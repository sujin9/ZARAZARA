package com.example.zarazara;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ButtonReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "걸음 수 측정 종료", Toast.LENGTH_SHORT).show();
        if(intent.getAction().equals("STOP"))
        {
            context.stopService(intent);
            Intent stopIntent = new Intent(context, WalkService.class);
            stopIntent.setAction("STOP_ACTION");
            context.startService(stopIntent);
        }
    }
}
