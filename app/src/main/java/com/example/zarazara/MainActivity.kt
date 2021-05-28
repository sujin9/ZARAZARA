package com.example.zarazara

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var userCoin:Int = 0       // 유저가 보유한 코인    --> 매달 12시 지나면 추가
    var name:String = "모찌"   // 유저 캐릭터 애칭      --> 최초 실행할 때 튜토리얼에서 입력받음

    // 날짜 체크용 시간
    var nowDate:String = ""       // 접속한 현재 날짜
    var now:Long = 0              // 현재 시점 불러옴
    lateinit var date:Date
    var sdt = SimpleDateFormat("yyyy-MM-dd")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 선언
        // 텍스트뷰
        var nameText = findViewById<TextView>(R.id.characterName)   // 캐릭터 이름
        var coinText = findViewById<TextView>(R.id.userCoin)        // 보유한 코인
        // 버튼
        var homeButton1 = findViewById<ImageButton>(R.id.homeBtn1) // 키우기 버튼
        var homeButton2 = findViewById<ImageButton>(R.id.homeBtn2) // 걸음수 버튼
        var homeButton3 = findViewById<ImageButton>(R.id.homeBtn3) // 미션 버튼
        // intent
        var raiseIntent = Intent(this, RaiseActivity::class.java)
        var walkIntent = Intent(this, WalkActivity::class.java)
        var missionIntent = Intent(this, MissionActivity::class.java)

        // 첫 실행 여부 확인
        val sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
        val checkFirstAccess = sharedPreferences.getBoolean("checkFirstAccess", false)
        val editor = sharedPreferences.edit()

        if(!checkFirstAccess) {     // 앱 최초 실행
            editor.putBoolean("checkFirstAccess", true)
            editor.apply()

            // 첫 접속 날짜 저장
            now = java.lang.System.currentTimeMillis()
            date = java.util.Date(now)
            nowDate = sdt.format(date)
            editor.putInt("userCoin", userCoin)
            editor.putString("currentDate", nowDate)
            editor.apply()

            // 튜토리얼 실행
            //val tutorialIntent = Intent(this, TutorialActivity::class.java)
            //startActivity(tutorialIntent)
            //finish()
        }
        else {
            // 접속 날짜 변경 비교 --> 코인 획득
            now = java.lang.System.currentTimeMillis()
            date = java.util.Date(now)
            nowDate = sdt.format(date)

            // Log.d("CheckDateChanged", sharedPreferences.getString("currentDate", "Default").toString()+" & "+nowDate))
            if(checkDateChanged(
                    sharedPreferences.getString("currentDate", "Default").toString(),nowDate)) {
                // 접속일 기준, 어제 걸음수 불러오기
                var yesterday = sdt.format(java.util.Date(now - 24 * 60 * 60 * 1000))
                var dbHelper = DBHelper(this, "stepStore.db", null, 1)
                var db = dbHelper.writableDatabase
                dbHelper.onCreate(db)

                var newCoin:Int = dbHelper.getStep(yesterday)/100

                // 코인 변환
                // Log.d("CheckCoin", sharedPreferences.getInt("userCoin", 0).toString()+"+++"+newCoin)
                userCoin = sharedPreferences.getInt("userCoin", 0) + newCoin

                Toast.makeText(this, newCoin.toString()+"코인이 적립되었습니다!", Toast.LENGTH_LONG).show()

                editor.putInt("userCoin", userCoin)
                editor.putString("currentDate", nowDate)
                editor.apply()
            }
            else {
                // 접속 같은 날짜
                // nothing to do !
            }

        }

        //
        coinText.text = sharedPreferences.getInt("userCoin", 0).toString()
    //    nameText.text = name

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

        showMozziInfoBubble()

    }


    // 모찌 상태 말풍선 출력
    fun showMozziInfoBubble() {

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

    // 날짜 비교 체크 함수
    fun checkDateChanged(date1: String, date2: String):Boolean {
        if (date1==date2) {
            return false        // 같은 날짜
        }
        else {
            // Toast.makeText(this, "날짜 바뀜!", Toast.LENGTH_LONG).show()
            return true        // 다른 날짜
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