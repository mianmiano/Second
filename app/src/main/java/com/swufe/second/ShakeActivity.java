package com.swufe.second;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.print.PrinterId;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class ShakeActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Vibrator vibrator;
    private static String strs[] = {"石头","剪刀","布"};
    private static final int SENSOR_SHAKE = 10;

    TextView text;
    ImageView img;

    private static String TAG = "ShakeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        text = findViewById(R.id.tv_txt);
        img = findViewById(R.id.img_shake);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    protected void onResume() {
        super.onResume();
        if(sensorManager!=null){
            sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

    protected void onStop() {
        super.onStop();
        if(sensorManager!=null){
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //传感器信息改变时执行该方法
            float[] values = sensorEvent.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            Log.i(TAG, "onSensorChanged: x["+x+"] y["+y+"] z["+z+"]");
            int medumValue = 10;
            if(Math.abs(x)>medumValue || Math.abs(y)>medumValue || Math.abs(z)>medumValue){
                vibrator.vibrate(200);
                Message msg = new Message();
                msg.what = SENSOR_SHAKE;
                handler.sendMessage(msg);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 10:
                    Log.i(TAG, "handleMessage: 摇晃操作");
                    Random r = new Random();
                    int num = Math.abs(r.nextInt())%3;
                    text.setText(strs[num]);
//                    img.setImageResource(pics[num]);
                    break;
            }
        }
    };
}
