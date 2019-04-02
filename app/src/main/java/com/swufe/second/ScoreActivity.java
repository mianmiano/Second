package com.swufe.second;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity {

    TextView tvScoreA;
    TextView tvScoreB;
    String TAG = "ScoreActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        tvScoreA = findViewById(R.id.tvScoreA);
        tvScoreB = findViewById(R.id.tvScoreB);

    }

    public void btn1(View btn){
        if(btn.getId() == R.id.btnTeamA1){
            show(1);
        }else{
            show2(1);
        }
    }

    public void btn2(View btn){
        if(btn.getId() == R.id.btnTeamA2){
            show(2);
        }else{
            show2(2);
        }
    }

    public void btn3(View btn){
        if(btn.getId() == R.id.btnTeamA3){
            show(3);
        }else{
            show2(3);
        }
    }

    public void reset(View btn){
        tvScoreA.setText("0");
        tvScoreB.setText("0");
    }


    public void show(int i){
        String oldScore = (String) tvScoreA.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore)+i);
        Log.i(TAG, "show: score="+newScore);
        tvScoreA.setText(newScore);
        Log.i(TAG, "show: score2="+newScore);
    }

    public void show2(int i){
        String oldScore = (String) tvScoreB.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore)+i);
        Log.i(TAG, "show: score="+newScore);
        tvScoreB.setText(newScore);
        Log.i(TAG, "show: score2="+newScore);
    }




}
