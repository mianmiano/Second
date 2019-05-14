package com.swufe.second;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyListActivity extends ListActivity implements Runnable,AdapterView.OnItemClickListener{

    Handler handler;
    ListView listView;
    private List<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;

    private String TAG = "MyListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_list);

        listView = findViewById(R.id.myList);
        String data[] = {"one", "two"};

        this.setListAdapter(listItemAdapter);

        //开启子线程
        Thread thread = new Thread(this); //注意！必须加this
        thread.start(); // 调用run方法

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    listItems = (List<HashMap<String,String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(MyListActivity.this,listItems,
                            R.layout.list_item,
                            new String[]{"name","rate"},
                            new int[]{R.id.itemTitle,R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };

        //列表绑定监听器
        //由于这个activity是继承的listactivity，所以需要通过getListView()获取listview对象
        getListView().setOnItemClickListener(this);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                Log.i(TAG, "onItemLongClick: 长按");
//                //删除操作:删除数据再通知adapter
//                listItems.remove(i);//注意listItems前后是否是同一个（handler返回数据之前是存在list2中
//                listItemAdapter.notifyDataSetChanged();

                //构造对话框进行确认操作
                AlertDialog.Builder builder = new AlertDialog.Builder(MyListActivity.this);
                Log.i(TAG, "onItemLongClick: builder="+builder);
                builder.setTitle("提示").setMessage("请确认是否删除数据")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.i(TAG, "onClick: 对话框事件处理");
                                listItems.remove(position);
                                listItemAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("否",null);
                builder.create().show();

                //返回false表明短按事件依然生效，true则是短按事件被屏蔽
                return true;
            }
        });
    }

    public void run(){
        //获取网络数据，放入list带回主线程
        List<HashMap<String,String>> retList = new ArrayList<HashMap<String,String>>();

        Document doc = null;
        try {
            //jsoup直接从网络地址获取
            doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj").get();//获得connect对象，get方法获得对应doc
            Log.i(TAG, "run: "+doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table = tables.get(1);
            //获取td中的数据
            Elements tds = table.getElementsByTag("td");

            //创建list存放数据
            for(int i=0;i<tds.size();i+=8){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+5);
                String str = td1.text();
                String val = td2.text();
                Log.i(TAG, "run: "+str+"==>"+val);
                HashMap<String,String> map = new HashMap<>();
                map.put("name",str);
                map.put("rate",val);
                retList.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        // view是listview的item
        //有两种方式获得列表数据：1.通过getListView().getItemIdAtPosition(position)获得map-通过get方法
//        HashMap<String,String> map = (HashMap<String,String>)getListView().getItemAtPosition(position);
//        map.get("");
        //2.通过view.findViewById()获得控件，从而获得对应数据
//        TextView detail = view.findViewById(R.id.itemDetail);
//        detail.getText();

        //获取货币及汇率
        TextView title = view.findViewById(R.id.itemTitle);
        TextView detail = view.findViewById(R.id.itemDetail);
        String name = title.getText().toString();
        String rate = detail.getText().toString();
        Log.i(TAG, "onItemClick: name="+name);
        Log.i(TAG, "onItemClick: rate="+rate);

//        //打开新的页面，传入参数
//        Intent rateCal = new Intent(this,RateCalActivity.class);
//        rateCal.putExtra("name",name);
//        rateCal.putExtra("rate",Float.parseFloat(rate));
//        startActivity(rateCal);

        customDialogClick(name,Float.parseFloat(rate));

    }

    private void customDialogClick(String name, final float rate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyListActivity.this);
        Log.i(TAG, "onItemLongClick: builder="+builder);
        AlertDialog dialog = builder.create();
        View dialogView = View.inflate(this,R.layout.activity_rate_cal,null);
        //设置对话框布局
        dialog.setView(dialogView);
        dialog.setTitle("汇率计算");
        dialog.show();
        TextView title = dialogView.findViewById(R.id.title2);
        EditText etInput = dialogView.findViewById(R.id.etInput);
        final TextView tvShowMoney = dialogView.findViewById(R.id.tvShowMoney2);

        Log.i(TAG, "customDialogClick: title="+title);
        Log.i(TAG, "customDialogClick: name = "+name);
        title.setText(name);
        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0){
                    float val = Float.parseFloat(s.toString());
                    tvShowMoney.setText(val + "RMB==>" + (100/rate*val));
                }else{
                    tvShowMoney.setText("");
                }
            }
        });
    }
}
