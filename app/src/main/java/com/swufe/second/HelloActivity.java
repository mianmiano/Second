package com.swufe.second;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class HelloActivity extends AppCompatActivity {

    private Button mBtnCalculate;
    private EditText mEtCelcius;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        initViews();
    }

    private void initViews() {
        mBtnCalculate = findViewById(R.id.btnCalculateTemp);
        mEtCelcius = findViewById(R.id.etCelcius);
        mTvResult = findViewById(R.id.tvFahrenheit);

        mBtnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获得摄氏度
                String str = mEtCelcius.getText().toString();
                //如何判断错误


                //转换为华氏度
                double temp = Double.parseDouble(str);
                double f = temp * 1.8 +32;
                DecimalFormat df = new DecimalFormat("#.00");
                mTvResult.setText("结果为："+String.valueOf(df.format(f)));
            }
        });
        

    }

}
