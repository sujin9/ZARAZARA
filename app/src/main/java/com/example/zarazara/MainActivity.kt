package com.example.zarazara

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintSet.VISIBLE

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

        //모찌말풍선이랑 경험치바 말풍선이랑 겹치지 않게, 1 = 말풍선 띄워짐, 0 = 말풍선 숨김
        var mozzi_num = 0
        var progressBar_num = 0
        //클릭시 말풍선 띄우고 다시 클릭시 사라짐
        var mozzi = findViewById<ImageButton>(R.id.mozzi)   //모찌
        var mozzispeech = findViewById<LinearLayout>(R.id.mozzi_speech)    //모찌말풍선
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)   //경험치바
        var progressspeech = findViewById<LinearLayout>(R.id.progressBar_speech)    //경험치바말풍선

        mozzi.setOnClickListener {
            if(mozzispeech.visibility == View.INVISIBLE){
                if(progressBar_num == 0){
                    mozzispeech.visibility = View.VISIBLE
                    mozzi_num = 1
                }
                else{
                    progressspeech.visibility = View.INVISIBLE
                    progressBar_num = 0
                    mozzispeech.visibility = View.VISIBLE
                    mozzi_num = 1
                }
            }
            else{
                mozzispeech.visibility = View.INVISIBLE
                mozzi_num = 0
            }
        }

        progressBar.setOnClickListener {
            if (progressspeech.visibility == View.INVISIBLE) {
                if(mozzi_num == 0){
                    progressspeech.visibility = View.VISIBLE
                    progressBar_num = 1
                }
                else{
                    mozzispeech.visibility = View.INVISIBLE
                    mozzi_num = 0
                    progressspeech.visibility = View.VISIBLE
                    progressBar_num = 1
                }
            }
            else {
                progressspeech.visibility = View.INVISIBLE
                progressBar_num = 0
            }
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