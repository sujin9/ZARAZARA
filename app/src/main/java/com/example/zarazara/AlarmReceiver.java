package com.example.zarazara;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {

    Long now;
    Date date;
    String today;

    int gaugeFull;
    int gaugeHealth;
    int gaugeHappy;

    @Override
    public void onReceive(Context context, Intent intent) {
       /*
        // 동작할 거
        full = intent.getIntExtra("gaugeFull", 0);
        health = intent.getIntExtra("gaugeHealth", 0);

        Log.e("CheckFullGauge in BroadCast", String.valueOf(full));
        Log.e("CheckHealthGauge in BroadCast", String.valueOf(health));

        Intent mainIntent = new Intent(context, MainActivity.class);

        mainIntent.putExtra("receiverFull", full - 10);
        mainIntent.putExtra("receiverFull", health - 10);

        */

        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        gaugeFull = sharedPreferences.getInt("gaugeFull", 0);
        gaugeHealth = sharedPreferences.getInt("gaugeHealth", 0);
        gaugeHappy = sharedPreferences.getInt("gaugeHappy", 0);

        // 포만감, 운동량, 행복감 게이지 시간에 따라 감소되도록
        gaugeFull -= 15;
        if(gaugeFull < 0)   gaugeFull = 0;
        gaugeHealth -= 15;
        if(gaugeHealth < 0) gaugeHealth = 0;
        gaugeHappy -= 10;
        if(gaugeHappy < 0) gaugeHappy = 0;

        editor.putInt("gaugeFull", gaugeFull);
        editor.putInt("gaugeHealth", gaugeHealth);

        Log.e("CheckFullGauge in Receiver", String.valueOf(gaugeFull));
        Log.e("CheckHealthGauge in Receiver", String.valueOf(gaugeHealth));

        editor.apply();

        /*
        // 시험용
        //현재 시간(날짜 구하기 위해)
        now = System.currentTimeMillis();
        date = new Date(now);
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        today = sdt.format(date);
        Log.e("현재 시각", today);
         */
    }
}
