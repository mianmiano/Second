package com.swufe.second;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private ArrayList<HashMap<String,String>> listItems;
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
                    List<HashMap<String,String>> list2 = (List<HashMap<String,String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(MyListActivity.this,list2,
                            R.layout.list_item,
                            new String[]{"name","rate"},
                            new int[]{R.id.itemTitle,R.id.itemDetail});
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };

        //列表绑定监听器
        getListView().setOnItemClickListener(this);
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

        //打开新的页面，传入参数
        Intent rateCal = new Intent(this,RateCalActivity.class);
        rateCal.putExtra("name",name);
        rateCal.putExtra("rate",Float.parseFloat(rate));
        startActivity(rateCal);

    }
}
