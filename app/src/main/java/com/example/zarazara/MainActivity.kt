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

    var userCoin:Int = 0    // 유저가 보유한 코인    --> 매달 12시 추가
    var name:String = "모찌"   // 유저 캐릭터 애칭      --> 최초 실행할 때 튜토리얼에서 입력받음


    // 날짜 체크용 시간
    //var currentDate:String = ""   // 가장 최근 접속했던 날짜
    var nowDate:String = ""       // 접속한 현재 날짜
    var now:Long = 0              // 현재 시점 불러옴
    lateinit var date:Date
    var sdt = SimpleDateFormat("yyyy-MM-dd")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 첫 실행 여부 확인
        val sharedPreferences = getSharedPreferences("checkFirstAccess", MODE_PRIVATE)
        val checkFirstAccess = sharedPreferences.getBoolean("checkFirstAccess", false)
        val spCurrentDate = getSharedPreferences("saveCurrentDate", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        if(!checkFirstAccess) {
            editor.putBoolean("checkFirstAccess", true)
            editor.apply()

            // 첫 접속 날짜 저장
            now = java.lang.System.currentTimeMillis()
            date = java.util.Date(now)
            //currentDate = sdt.format(date)
            nowDate = sdt.format(date)
            editor.putString("currentDate", nowDate)
            editor.apply()

            // 튜토리얼 실행
            //val tutorialIntent = Intent(this, TutorialActivity::class.java)
            //startActivity(tutorialIntent)
            //finish()
        }
        else {
            // 날짜 변경 비교 --> 코인 획득
            now = java.lang.System.currentTimeMillis()
            date = java.util.Date(now)
            nowDate = sdt.format(date)

            Log.d("CheckDate",sharedPreferences.getString("currentDate", "Default")+"***"+nowDate)

            if(checkDateChanged(sharedPreferences.getString("currentDate", "Default").toString(), nowDate)) {
                // 다른 날짜
                var yesterday = sdt.format(java.util.Date(now-24*60*60*1000))
                //var newCoin: Int = (application as WalkActivity).helper.getStep(yesterday)
                //Log.d("CheckCoin", newCoin.toString())
                //Toast.makeText(this, "새로운 코인 "+newCoin+"을 얻었습니다", Toast.LENGTH_SHORT).show()

                editor.putString("currentDate", nowDate)
                editor.apply()
            }
            else {
                // 같은 날짜

            }

        }

        // 선언
        // 텍스트뷰
        var nameText = findViewById<TextView>(R.id.characterName)   // 캐릭터 이름
        var coinText = findViewById<TextView>(R.id.userCoin)        // 보유한 코인
        // 버튼
        var homeButton1 = findViewById<ImageButton>(R.id.homeBtn1)  // 키우기 버튼
        var homeButton2 = findViewById<ImageButton>(R.id.homeBtn2)  // 걸음수 버튼
        var homeButton3 = findViewById<ImageButton>(R.id.homeBtn3)  // 미션 버튼
        // intent
        var raiseIntent = Intent(this, RaiseActivity::class.java)
        var walkIntent = Intent(this, WalkActivity::class.java)
        var missionIntent = Intent(this, MissionActivity::class.java)

        //
        coinText.text = userCoin.toString()
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
    fun checkDateChanged(date1:String, date2:String):Boolean {
        if (date1==date2) {
            return false        // 같은 날짜
        }
        else {
            Toast.makeText(this, "날짜 바뀜!", Toast.LENGTH_LONG).show()
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