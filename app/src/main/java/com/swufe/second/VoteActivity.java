package com.swufe.second;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class VoteActivity extends AppCompatActivity {

    private static String TAG = "VoteActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
    }
    
    public void onClickVote(View btn){
        if(btn.getId()==R.id.btn_vote1){
            new VoteTask().execute("赞成");
        }else if(btn.getId()==R.id.btn_vote2){
            new VoteTask().execute("赞成");
        }else{
            new VoteTask().execute("赞成");
        }
    }
    
    private class VoteTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            for(String p:strings){
                Log.i(TAG, "doInBackground: ");
            }
            String ret = doVote(strings[0]);
            return ret;
        }
    }

    private String doVote(String voteStr) {
        String retStr = "";
        try{
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("r=").append(URLEncoder.encode(voteStr,"utf-8"));

            byte[] data = stringBuffer.toString().getBytes();
            String urlPath = "http://";
//            Url url = new URL(urlPath);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return retStr;
    }
}
