package com.example.zarazara;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver {

    int gaugeFull;
    int gaugeHealth;
    int gaugeHappy;

    @Override
    public void onReceive(Context context, Intent intent) {

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
        editor.putInt("gaugeHappy", gaugeHappy);

    //    Log.e("CheckFullGauge in Receiver", String.valueOf(gaugeFull));
    //    Log.e("CheckHealthGauge in Receiver", String.valueOf(gaugeHealth));


        editor.apply();

    }
}
