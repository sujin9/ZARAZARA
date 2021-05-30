package com.example.zarazara;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ButtonReceiver extends BroadcastReceiver {

    LayoutInflater inflater;
    View toastLayout;
    TextView toastText;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 종료 확인 메시지 출력
        Toast toast = new Toast(context);
        inflater = LayoutInflater.from(context);
        toastLayout = inflater.inflate(R.layout.custom_toast, null);
        toastText = toastLayout.findViewById(R.id.customToastText);
        toastText.setText("걸음 수 측정을 종료해요");
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastLayout);
        toast.show();
        // Toast.makeText(context, "걸음 수 측정 종료", Toast.LENGTH_SHORT).show();

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
