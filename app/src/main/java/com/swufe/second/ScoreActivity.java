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
    String TAG = "ScoreActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        tvScoreA = findViewById(R.id.tvScoreA);

    }

    public void onClick(View btn){
        if(btn.getId() == R.id.btnTeamA1){
            show(1);
        }
        if(btn.getId() == R.id.btnTeamA2){
            show(2);
        }
        if(btn.getId() == R.id.btnTeamA3){
            show(3);
        }
        if(btn.getId() == R.id.btnReset){
            tvScoreA.setText("0");
        }
    }

    public void show(int i){
        String oldScore = (String) tvScoreA.getText();
        String newScore = String.valueOf(Integer.parseInt(oldScore)+i);
        Log.i(TAG, "show: score="+newScore);
        tvScoreA.setText(newScore);
        Log.i(TAG, "show: score2="+newScore);
    }




}
