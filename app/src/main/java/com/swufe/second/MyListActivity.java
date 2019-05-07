package com.swufe.second;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity implements Runnable{

    Handler handler;
    ListView listView;

    String TAG = "MyListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        listView = findViewById(R.id.myList);
        String data[] = {"one", "two"};

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        //开启子线程
        Thread thread = new Thread(this); //注意！必须加this
        thread.start(); // 调用run方法

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1, list2);
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    public void run(){
        //获取网络数据，放入list带回主线程
        List<String> retList = new ArrayList<>();

        Document doc = null;
        try {
            Thread.sleep(3000);
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Message msg = handler.obtainMessage(7);
        msg.obj = retList;
        handler.sendMessage(msg);
    }
}
