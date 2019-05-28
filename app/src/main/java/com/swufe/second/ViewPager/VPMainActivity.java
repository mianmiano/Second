package com.swufe.second.ViewPager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.swufe.second.R;

public class VPMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpmain);

        ViewPager viewPager = findViewById(R.id.viewpager);
        MyPageAdapter myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPageAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
