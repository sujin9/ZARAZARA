package com.example.zarazara

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 선언
        // 버튼
        var homeButton1 = findViewById<ImageButton>(R.id.homeBtn1) // 키우기 버튼
        var homeButton2 = findViewById<ImageButton>(R.id.homeBtn2) // 걸음수 버튼
        var homeButton3 = findViewById<ImageButton>(R.id.homeBtn3) // 미션 버튼
        // intent
        var raiseIntent = Intent(this, RaiseActivity::class.java)
        var walkIntent = Intent(this, WalkActivity::class.java)
        var missionIntent = Intent(this, MissionActivity::class.java)

        // 하단 버튼 클릭할 때
        homeButton1.setOnClickListener {
            startActivity(raiseIntent)
        }


        homeButton2.setOnClickListener {
            startActivity(walkIntent)
        }


        homeButton3.setOnClickListener {
            startActivity(missionIntent)
        }

    }

    // 알림 채널(서비스 - notification)
    // Button2(걸음 수) 기능은 Foreground Service + notification 으로 수행
    fun onStartForegroundService(view: View?) {
        val intent = Intent(this, WalkService::class.java)
        intent.action = "startForeground"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {     // 버전이 오레오 이상이라면
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

}