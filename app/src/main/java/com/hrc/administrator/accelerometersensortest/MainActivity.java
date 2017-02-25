package com.hrc.administrator.accelerometersensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SensorManager manager;
    //endDate表示事件结束的时间
    private Date startDate,endDate;
    //多次摇晃的限定次数
    private int number=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor=manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        startDate=Calendar.getInstance().getTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(manager!=null){
            manager.unregisterListener(listener);
        }
    }

    private SensorEventListener listener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float xValue=Math.abs(event.values[0]);
            float yValue=Math.abs(event.values[1]);
            float zValue=Math.abs(event.values[2]);
            endDate=Calendar.getInstance().getTime();
            if(xValue>15||yValue>15||zValue>15){
                if(number>5){
                    ToastTool.SetMessage(MainActivity.this,"太激烈啦，让我缓口气");
                    number=0;
                }else {
                    ToastTool.SetMessage(MainActivity.this,"摇一摇");
                    if(twoDateDistance(startDate,endDate)<60*1000){
                        startDate=endDate;
                        number++;
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private long twoDateDistance(Date sDate,Date eDate){
        long number=Math.abs(sDate.getTime()-eDate.getTime());
        return number;
    }
}
