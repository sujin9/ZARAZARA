package com.example.zarazara;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.amitshekhar.DebugDB;

import java.text.SimpleDateFormat;
import java.util.Date;


// 참고 사이트
// 만보계 구현
//--> https://stickode.tistory.com/82
// TYPE_STEP_COUNTER 사용
//--> https://call203.tistory.com/29

// SQLite 사용(db로 걸음 수 관리)
//--> https://popcorn16.tistory.com/76


public class WalkActivity extends AppCompatActivity implements SensorEventListener{

    // 변수
    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    TextView totalStepCountView;
    TextView todayView; //

    // 현재 걸음 수
    int mCurrentSteps = 0;
    // listener 등록 후의 걸음 수
    int mSteps = 0;
    // 누적 걸음수
    int mTotalSteps = 0;

    // SQLite 데이터베이스 생성용
    DBHelper helper;
    SQLiteDatabase db;
    Long now;
    Date date;
    String today;
    int save = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);

        DebugDB.getAddressLog();

        // SQLite database 및 테이블 생성(if not exist)
        helper = new DBHelper(WalkActivity.this, "stepStore.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        //현재 시간(날짜 구하기 위해)
        now = System.currentTimeMillis();
        date = new Date(now);
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        today = sdt.format(date);

        if(helper.checkTable() < 0)
            helper.insert("0",0);
        else
        {
            // 새로운 날짜에만 instance 생성
            save = helper.getStep(today);
            if (save == 0)
                helper.insert(today, 0);
        }


        stepCountView = findViewById(R.id.stepCountView);
        totalStepCountView = findViewById(R.id.totalStepCountView);
        todayView = findViewById(R.id.day); //

        todayView.setText(today); //

        // 활동 퍼미션 체크 (권한 체크)
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        // 걸음 센서 연결
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        //
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

    }


    public void onStart() {
        super.onStart();
        if(stepCountSensor != null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

/*
    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
*/


    // 실제 센서의 작동과 관련된 함수
    // 동작을 감지하면 이벤트를 발생하여 onSensorChanged 에 값을 전달
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            // 초기값 설정(TYPE_STEP_COUNTER 는 스스로 초기화 x)
            if(mSteps < 1)
            {
                mSteps = (int) event.values[0];
                save = helper.getStep(today);
            }
            // 센서 이벤트가 발생할때 마다 걸음수 증가
            // 현재 값 = (리셋 안 된 값 + 현재 값) - 리셋 안 된 값 + db에 저장된 그날의 걸음수 불러와서
            mCurrentSteps = (int)event.values[0] - mSteps + save;
            helper.updateDailyStep(today, mCurrentSteps);
            stepCountView.setText(String.valueOf(mCurrentSteps));
            // 누적값
            mTotalSteps = helper.getTotalStep();
            totalStepCountView.setText(String.valueOf(mTotalSteps));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}

