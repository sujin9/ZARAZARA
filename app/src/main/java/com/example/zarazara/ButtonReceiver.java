package com.example.zarazara;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ButtonReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 종료 확인 메시지 출력
        Toast.makeText(context, "걸음 수 측정 종료", Toast.LENGTH_SHORT).show();
        // STOP intent 들어왔을 시 서비스 재실행으로 stop 액션명령 보내기
        if(intent.getAction().equals("STOP"))
        {
            context.stopService(intent);
            Intent stopIntent = new Intent(context, WalkService.class);
            stopIntent.setAction("STOP_ACTION");
            context.startService(stopIntent);
        }
    }
}
