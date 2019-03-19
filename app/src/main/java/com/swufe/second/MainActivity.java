package com.swufe.second;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnBirthday;
    ImageView imageView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnBirthday = findViewById(R.id.btnBirthday);
        imageView1 = findViewById(R.id.image1);
        //监听button事件
        btnBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView1.setImageDrawable(getResources().getDrawable(R.drawable.birthday2));


            }
        });
    }
}
