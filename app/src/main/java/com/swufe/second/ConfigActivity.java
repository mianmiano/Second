package com.swufe.second;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {

    EditText etDollar;
    EditText etEuro;
    EditText etWon;

    String TAG = "ConfigActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent intent = getIntent();
        float dollar = intent.getFloatExtra("dollarRate",0.0f);
        float euro = intent.getFloatExtra("euroRate",0.0f);
        float won = intent.getFloatExtra("wonRate",0.0f);

        etDollar = findViewById(R.id.etDollarRate);
        etEuro = findViewById(R.id.etEuroRate);
        etWon = findViewById(R.id.etWonRate);

        etDollar.setText(String.valueOf(dollar));
        etEuro.setText(String.valueOf(euro));
        etWon.setText(String.valueOf(won));
    }

    public void save(View btn){
        float dollar2 = Float.parseFloat(etDollar.getText().toString());
        float euro2 = Float.parseFloat(etEuro.getText().toString());
        float won2 = Float.parseFloat(etWon.getText().toString());

        Log.i(TAG, "save: dollar2="+dollar2);
        Log.i(TAG, "save: euro2="+euro2);
        Log.i(TAG, "save: won2="+won2);


        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putFloat("dollarNew",dollar2);
        bundle.putFloat("euroNew",euro2);
        bundle.putFloat("wonNew",won2);
        intent.putExtras(bundle);
        //返回数据
        setResult(2,intent);

        //返回调用界面（结束当前页面）
        finish();
    }
}
