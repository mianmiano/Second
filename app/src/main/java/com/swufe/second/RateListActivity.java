package com.swufe.second;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable {
    private String TAG = "RateListActivity";
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //因为ListActivity已经包含了布局，所以不需要通过页面填充它
        //setContentView(R.layout.activity_rate_list);

        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        Log.i ("List","lastRateDateStr=" +logDate);

        List<String> list1 = new ArrayList<>();
        for(int i=0;i<100;i++){
            list1.add("item"+i);
        }
        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);
        setListAdapter(adapter);

        //开启子线程
        Thread thread = new Thread(this); //注意！必须加this
        thread.start(); // 调用run方法

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 7){
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    public void run(){
        //获取网络数据，放入list带回主线程
        List<String> retList = new ArrayList<>();

        //判断日期
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        Log.i("run", "curDateStr:"+curDateStr+" logDate:"+logDate);

        if(curDateStr.equals(logDate)){
            //日期相等，数据库获取数据
            Log.i("run", "日期相等，数据库获取数据");
            RateManager manager = new RateManager(this);
            for(RateItem item : manager.listAll()){
                retList.add(item.getCurName()+"==>"+item.getCurRate());
            }
        }else{
            //日期不等，网络获取数据
            Log.i("run", "日期不等，网络获取数据");
            Document doc = null;
            //存入数据库
            List<RateItem> rateList = new ArrayList<>();
            try {
                Thread.sleep(300);
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
                    retList.add(str+"==>"+val);

                    RateItem item = new RateItem(str,val);
                    rateList.add(item);
                }

                RateManager manager = new RateManager(this);
                manager.deleteAll();
                Log.i("db"," 删除所有记录");
                manager.addAll(rateList);
                Log.i("db"," 添加新记录集");

                //修改日期，写入sp
                SharedPreferences sp = getSharedPreferences("myrate",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(DATE_SP_KEY,curDateStr);
                editor.commit();
                Log.i("run", "更新日期结束："+curDateStr);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);
    }
}
