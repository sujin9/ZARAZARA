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

    int gaugeFull;
    int gaugeHealth;

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        gaugeFull = sharedPreferences.getInt("gaugeFull", 0);
        gaugeHealth = sharedPreferences.getInt("gaugeHealth", 0);

        // 포만감, 건강 게이지 시간에 따라 감소되도록
        gaugeFull -= 10;
        if(gaugeFull < 0)   gaugeFull = 0;
        gaugeHealth -= 10;
        if(gaugeHealth < 0) gaugeHealth = 0;

        editor.putInt("gaugeFull", gaugeFull);
        editor.putInt("gaugeHealth", gaugeHealth);

        Log.e("CheckFullGauge in Receiver", String.valueOf(gaugeFull));
        Log.e("CheckHealthGauge in Receiver", String.valueOf(gaugeHealth));

        editor.apply();

    }
}
