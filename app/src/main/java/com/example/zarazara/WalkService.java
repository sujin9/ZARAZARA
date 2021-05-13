package com.example.zarazara;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static java.sql.DriverManager.println;

public class WalkService extends Service {

    public WalkService() {
    }

    @Override public void onCreate() { super.onCreate(); }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        if("startForeground".equals(intent.getAction()))
            startForegroundService();   // 포그라운드 생성

        // START_STICKY: 서비스가 죽어도 시스템에서 재생성
        // START_NOT_STICKY: 서비스가 죽어도 시스템에서 재생성하지 않음
        return START_NOT_STICKY;
    }

    // 포그라운드 서비스
    @RequiresApi(api = Build.VERSION_CODES.O)

    private void startForegroundService() {
        // 채널 설정
        String channel_id = "WalkChannelId";            // 채널 아이디
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("ZARAZARA");        // 앱 이름
        builder.setContentText("걸음 수 측정 중");    // 설명

        Intent notificationIntent = new Intent(this, WalkActivity.class); // 실제 수행하는 인텐트
        // pendingIntent: 클릭 시 동작하는 인텐트
        // PendingIntent 로 지정해서 사용할 notification 을 지정해놓으면 해당 notification 이 수행
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        // 클릭 시 실행될 pendingIntent 세팅
        builder.setContentIntent(pendingIntent);

        // 오레오 이상의 버전에서는 채널 등록해서 notification 사용
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel(channel_id, "걸음 수 측정", NotificationManager.IMPORTANCE_DEFAULT));
            builder.setChannelId(channel_id);
        }

        Notification notification = builder.build();
        startForeground(1, notification); // id 0이면 안 됨
    }

}
