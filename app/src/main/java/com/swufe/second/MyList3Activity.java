package com.swufe.second;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MyList3Activity extends AppCompatActivity {

    List<String> data = new ArrayList<>();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list3);

        ListView listView = findViewById(R.id.myList3);
        for(int i=0;i<10;i++){
            data.add("item"+i);
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        //当列表无数据时，用tv来填充界面
        listView.setEmptyView(findViewById(R.id.tvNoData));

        //设置监听(也可以接口AdapterView.OnItemClickListener）
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //adapterView是listview,i是列表对应位置
                //adapter.remove仅适用于ArrayAdapter
                adapter.remove(adapterView.getItemAtPosition(i));
//                //刷新页面，通知页面已被改变
//                adapter.notifyDataSetChanged();
            }
        });
    }
}
