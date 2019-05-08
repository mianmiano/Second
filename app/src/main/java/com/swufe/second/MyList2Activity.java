package com.swufe.second;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MyList2Activity extends ListActivity {

    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();

        MyAdapter myAdapter = new MyAdapter(this,R.layout.list_item,listItems);
        this.setListAdapter(myAdapter);
//        this.setListAdapter(listItemAdapter);
    }

    private void initListView() {
        listItems = new ArrayList<>();
        for(int i=0;i<10;i++){
            HashMap<String,String> map = new HashMap<>();
            map.put("ItemTitle","Rate= "+i);
            map.put("ItemDetail","detail"+i);
            listItems.add(map);
        }

        listItemAdapter = new SimpleAdapter(this,listItems,
                R.layout.list_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail});
    }
}
