package com.swufe.second;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class RateCalActivity extends AppCompatActivity {

    float rate = 0.0f;

    private String TAG = "RateCalActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_cal);

        Intent intent = getIntent();
        String title = intent.getStringExtra("name");
        rate = intent.getFloatExtra("rate",0.0f);
        Log.i(TAG, "onCreate: title="+title);
        Log.i(TAG, "onCreate: rate="+rate);

        ((TextView)findViewById(R.id.title2)).setText(title);
        EditText input = findViewById(R.id.etInput);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TextView show = findViewById(R.id.tvShowMoney2);
                if(s.length()>0){
                    float val = Float.parseFloat(s.toString());
                    show.setText(val + "RMB==>" + (100/rate*val));
                }else{
                    show.setText("");
                }
            }
        });

    }
}
