package com.example.zarazara;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    Long now;
    Date date;
    String today;
    @Override
    public void onReceive(Context context, Intent intent) {
        // 동작할 거

        // 시험용
        //현재 시간(날짜 구하기 위해)
        now = System.currentTimeMillis();
        date = new Date(now);
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        today = sdt.format(date);
        Log.e("현재 시각", today);
    }
}
