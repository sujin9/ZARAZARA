package com.example.zarazara

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    // 초기 설정
    var userCoin: Int = 1000       // 유저가 보유한 코인    --> 매달 12시 지나면 추가
    var name: String = "모찌"      // 캐릭터 이름
    var gaugeFull: Int = 80       // 포만감
    var gaugeHealth: Int = 80     // 건강도
    var gaugeHappy: Int = 80      // 행복도
    var totalExp = 0            // 총 경험치
    var mealExp = 0             // 식사 경험치
    var exerciseExp = 0         // 운동 경험치
    var hobbyExp = 0            // 취미 경험치
    var version: Int = 1         // 캐릭터 단계
    var kind: Int = 1            // 캐릭터 종류

    // 데일리 미션 달성 여부
    var dailyMission1:Boolean = false
    var dailyMission2:Boolean = false
    var dailyMission3:Boolean = false
    var dailyMission4:Boolean = false
    var dailyMission5:Boolean = false

    //누적미션
    var totalStep:Int = 5000
    var accMission:Int = 0
    var walkText:String = "5000보 걷기"

    // 텍스트뷰, 프로그레스바
    lateinit var coinText: TextView
    lateinit var fullProgressBar: ProgressBar
    lateinit var happyProgressBar: ProgressBar
    lateinit var exerciseProgressBar: ProgressBar
    lateinit var totalExpProgreeBar: ProgressBar
    lateinit var mealExpProgressBar: ProgressBar
    lateinit var exerciseExpProgressBar: ProgressBar
    lateinit var hobbyExpProgressBar: ProgressBar

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var mozzi: ImageButton
    lateinit var upgradeButton: Button

    // 토스트 관련
    lateinit var toast: Toast
    lateinit var toastLayout: View
    lateinit var toastText: TextView

    // 날짜 체크용
    var nowDate: String = ""       // 접속한 현재 날짜
    var now: Long = 0              // 현재 시점 불러옴
    lateinit var date: Date
    var sdt = SimpleDateFormat("yyyy-MM-dd")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 선언
        // 텍스트뷰
        var nameText = findViewById<TextView>(R.id.characterName)   // 캐릭터 이름
        coinText = findViewById<TextView>(R.id.userCoin)        // 보유한 코인
        // 버튼
        var homeButton1 = findViewById<ImageButton>(R.id.homeBtn1) // 키우기 버튼
        var homeButton2 = findViewById<ImageButton>(R.id.homeBtn2) // 걸음수 버튼
        var homeButton3 = findViewById<ImageButton>(R.id.homeBtn3) // 미션 버튼
        upgradeButton = findViewById<Button>(R.id.upgradeBtn)   // 진화 버튼
        mozzi = findViewById<ImageButton>(R.id.mozzi)   //모찌
        // intent
        var raiseIntent = Intent(this, RaiseActivity::class.java)
        var walkIntent = Intent(this, WalkActivity::class.java)
        var missionIntent = Intent(this, MissionActivity::class.java)
        // 모찌 상태 프로그레스바
        fullProgressBar = findViewById<ProgressBar>(R.id.full_progressbar)
        happyProgressBar = findViewById<ProgressBar>(R.id.happy_progressbar)
        exerciseProgressBar = findViewById<ProgressBar>(R.id.exercise_progressbar)
        // 상단 경험치 프로그레스바
        totalExpProgreeBar = findViewById<ProgressBar>(R.id.expProgressBar)
        mealExpProgressBar = findViewById<ProgressBar>(R.id.p_meal_progressbar)
        exerciseExpProgressBar = findViewById<ProgressBar>(R.id.p_exercise_progressbar)
        hobbyExpProgressBar = findViewById<ProgressBar>(R.id.p_hobby_progressbar)
        // 토스트 관련
        var customToastLayout = findViewById<LinearLayout>(R.id.customToastLayout)
        toastLayout = layoutInflater.inflate(R.layout.custom_toast, customToastLayout)
        toastText = toastLayout.findViewById<TextView>(R.id.customToastText)
        toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT

        //
        // 첫 실행 여부 확인
        sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
        val checkFirstAccess = sharedPreferences.getBoolean("checkFirstAccess", false)
        editor = sharedPreferences.edit()

        now = java.lang.System.currentTimeMillis()
        date = java.util.Date(now)
        nowDate = sdt.format(date)

        if (!checkFirstAccess) {     // 앱 최초 실행
            editor.putBoolean("checkFirstAccess", true)
            editor.apply()

            // 첫 접속 날짜 저장
            editor.putString("currentDate", nowDate)
            // 초기 설정 저장
            editor.putInt("version", version)
            editor.putInt("kind", kind)
            editor.putInt("userCoin", userCoin)
            editor.putInt("gaugeFull", gaugeFull)
            editor.putInt("gaugeHealth", gaugeHealth)
            editor.putInt("gaugeHappy", gaugeHappy)
            editor.putInt("totalExp", totalExp)
            editor.putInt("mealExp", mealExp)
            editor.putInt("exerciseExp", exerciseExp)
            editor.putInt("hobbyExp", hobbyExp)
            // 데일리 미션 달성여부 저장
            editor.putBoolean("dailyMission1", false)
            editor.putBoolean("dailyMission2", false)
            editor.putBoolean("dailyMission3", false)
            editor.putBoolean("dailyMission4", false)
            editor.putBoolean("dailyMission5", false)

            //누적미션 달성여부 저장
            editor.putInt("accMission", 0)
            editor.putInt("totalStep", 5000)
            editor.putString("walkText", "5000보 걷기")

            editor.apply()

            // 튜토리얼 실행
            //val tutorialIntent = Intent(this, TutorialActivity::class.java)
            //startActivity(tutorialIntent)
            //finish()
        } else {
            // 접속 날짜 변경 비교 --> 코인 획득
            // Log.d("CheckDateChanged", sharedPreferences.getString("currentDate", "Default").toString()+" & "+nowDate))
            if (checkDateChanged(
                            sharedPreferences.getString("currentDate", "Default").toString(), nowDate)) {
                getYesterdayCoin()
            } else {
                // 접속 같은 날짜
                // nothing to do !
            }
        }

        //요소들 출력
        showCharacter(sharedPreferences.getInt("version", 1), sharedPreferences.getInt("kind",1))
        coinText.text = sharedPreferences.getInt("userCoin", 0).toString() + "C"

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

        // 모찌 상태 출력
        showMozziInfoBubble()
        // 모찌 사망 여부
        passAway()

        /*
       // 게이지 Timerㅇ
       val timerTask: TimerTask = object : TimerTask() {
           override fun run() {
               Log.e("태스크 카운터:", count.toString())
               count++
           }
       }
       // 타이머
       val timer = Timer()
       // timerTask를 딜레이 0초에 3초마다 실행
       timer.schedule(timerTask, 0, 3000)
        */
        
        // 현재시각 체크 시작
        startAlarmReceiver(this)

        // 프로그레스바 설정
        setMozziProgress()
        setExpProgress()
    }

    // back 버튼 누르고 돌아왔을 때 화면 갱신
    // 앱 실행중일 때 - 날짜 바뀌고 코인 받는 부분 추가 해야하나..?
    override fun onResume() {
        super.onResume()
        coinText.text = sharedPreferences.getInt("userCoin", 0).toString() + "C"
        setMozziProgress()
        setExpProgress()
        passAway()
    }


    // 날짜가 바뀐 경우 - 전날 코인 가져와 추가
    fun getYesterdayCoin() {
        // 접속일 기준, 어제 걸음수 불러오기
        var yesterday = sdt.format(java.util.Date(now - 24 * 60 * 60 * 1000))
        var dbHelper = DBHelper(this, "stepStore.db", null, 1)
        var db = dbHelper.writableDatabase
        dbHelper.onCreate(db)

        var newCoin: Int = dbHelper.getStep(yesterday) / 100

        // 코인 변환
        // Log.d("CheckCoin", sharedPreferences.getInt("userCoin", 0).toString()+"+++"+newCoin)
        userCoin = sharedPreferences.getInt("userCoin", 0) + newCoin

        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toastText.text = newCoin.toString() + " 코인이 적립되었어요!"
        toast.view = toastLayout
        toast.show()

        editor.putInt("userCoin", userCoin)
        editor.putString("currentDate", nowDate)
        editor.apply()
    }

    // 상단 경험치 프로그레스바 설정
    fun setExpProgress() {
        mealExp = sharedPreferences.getInt("mealExp", 0)
        hobbyExp = sharedPreferences.getInt("hobbyExp", 0)
        exerciseExp = sharedPreferences.getInt("exerciseExp", 0)
        totalExp = sharedPreferences.getInt("totalExp", 0);
        Log.d("CheckExp", "meal " + mealExp + "| exercise " + exerciseExp + "| hobby " + hobbyExp + "| total " + totalExp)
        mealExpProgressBar.setProgress(mealExp)
        exerciseExpProgressBar.setProgress(exerciseExp)
        hobbyExpProgressBar.setProgress(hobbyExp)
        totalExpProgreeBar.setProgress(totalExp)
    }

    // 모찌 상태 프로그레스바 설정
    fun setMozziProgress() {
        gaugeFull = sharedPreferences.getInt("gaugeFull", 0)
        gaugeHealth = sharedPreferences.getInt("gaugeHealth", 0)
        gaugeHappy = sharedPreferences.getInt("gaugeHappy", 0)
        Log.d("CheckGauge", "full " + gaugeFull + "| health " + gaugeHealth + "| happy " + gaugeHappy)

        fullProgressBar.setProgress(gaugeFull)
        exerciseProgressBar.setProgress(gaugeHealth)
        happyProgressBar.setProgress(gaugeHappy)
    }


    // 모찌 상태 말풍선 출력
    fun showMozziInfoBubble() {

        //모찌말풍선이랑 경험치바 말풍선이랑 겹치지 않게, 1 = 말풍선 띄워짐, 0 = 말풍선 숨김
        var mozzi_num = 0
        var progressBar_num = 0
        //클릭시 말풍선 띄우고 다시 클릭시 사라짐
        var mozzispeech = findViewById<LinearLayout>(R.id.mozzi_speech)    //모찌말풍선
        var progressspeech = findViewById<LinearLayout>(R.id.progressBar_speech)    //경험치바말풍선

        mozzi.setOnClickListener {
            if (mozzispeech.visibility == View.INVISIBLE) {
                if (progressBar_num == 0) {
                    mozzispeech.visibility = View.VISIBLE
                    mozzi_num = 1
                } 
              else {
                    progressspeech.visibility = View.INVISIBLE
                    progressBar_num = 0
                    mozzispeech.visibility = View.VISIBLE
                    mozzi_num = 1
                }
                setMozziProgress()
            } 
          else {
                mozzispeech.visibility = View.INVISIBLE
                mozzi_num = 0
            }
        }

        totalExpProgreeBar.setOnClickListener {
            if (progressspeech.visibility == View.INVISIBLE) {
                if (mozzi_num == 0) {
                    progressspeech.visibility = View.VISIBLE
                    progressBar_num = 1
                } else {
                    mozzispeech.visibility = View.INVISIBLE
                    mozzi_num = 0
                    progressspeech.visibility = View.VISIBLE
                    progressBar_num = 1
                }
                upgradeButton.setOnClickListener {
                    upgrade()
                }
            } else {
                progressspeech.visibility = View.INVISIBLE
                progressBar_num = 0
            }
        }

    }

    // 날짜 비교 체크 함수
    fun checkDateChanged(date1: String, date2: String): Boolean {
        if (date1 == date2) {
            return false        // 같은 날짜
        } else {
            // Toast.makeText(this, "날짜 바뀜!", Toast.LENGTH_LONG).show()
            return true        // 다른 날짜
        }
    }

    // notification 채널(서비스 - notification)
    // Foreground Service + notification 으로 수행
    fun onStartForegroundService(view: View?) {
        val intent = Intent(this, WalkService::class.java)
        intent.action = "startForeground"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {     // 버전이 오레오 이상이라면
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    // 게이지 수치 반복 감소용
    fun startAlarmReceiver(context: Context) {

        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        var alarmIntent = Intent(context, AlarmReceiver::class.java)
        var alarmPendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)

        // 2시간마다 수치 감소하도록
        // 2시간 : 2*60*60*1000
        // 1분 : 60*1000     (변경 확인 위해)
        alarmManager?.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 2 * 60 * 60 * 1000,
                2 * 60 * 60 * 1000,
                alarmPendingIntent);

    }

    // 캐릭터 진화 기능
    fun upgrade() {
        // Toast.makeText(this, "진화한당", Toast.LENGTH_SHORT).show()
        totalExp = sharedPreferences.getInt("totalExp", 0);
        version = sharedPreferences.getInt("version", 1)
        version += 1
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.view = toastLayout
        if (totalExp >= 300 && version <= 3) {
            // 진화
            toastText.text = "캐릭터가 진화해요 !"
            toast.show()

            totalExpProgreeBar.setProgress(0)
            mealExpProgressBar.setProgress(0)
            exerciseExpProgressBar.setProgress(0)
            hobbyExpProgressBar.setProgress(0)

            editor.putInt("version", version)
            editor.apply()

            showCharacter(sharedPreferences.getInt("version", 1), sharedPreferences.getInt("kind", 1))
            resetSetting(0, 50)

        } else if (version > 3) {
            toastText.text = "이미 캐릭터가 최고 레벨이에요 !"
            toast.show()
            version = 3
        } else if (totalExp < 300) {
            toastText.text = "아직 진화할 수 없어요 !"
            toast.show()
        }
    }

    // 캐릭터 사망 기능
    fun passAway() {
        gaugeFull = sharedPreferences.getInt("gaugeFull", 0)
        gaugeHealth = sharedPreferences.getInt("gaugeHealth", 0)
        // gaugeHappy = sharedPreferences.getInt("gaugeHappy", 0)

        // 포만감, 운동량 둘 중 하나라도 0이 되면 사망
        if (gaugeFull <= 0 || gaugeHealth <= 0) {
            // 사망
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.view = toastLayout
            toastText.text = "모찌가 사망하고 환생하여\n처음 단계로 돌아왔어요"
            toast.show()

            var random = Random()
            kind = (random.nextInt(3)+1)    // 1~3
            editor.putInt("version", 1)
            editor.putInt("kind", kind)
            editor.apply()

            showCharacter(sharedPreferences.getInt("version", 1), kind)
            resetSetting(0, 70)
        }
    }

    // 단계별 캐릭터 출력 함수
    fun showCharacter(version: Int, kind: Int) {
        if (kind == 1) {
            if (version == 1) {
                mozzi.setImageResource(R.drawable.character_bear_11)
            //    editor.putInt("version", 1)
            } else if (version == 2) {
                mozzi.setImageResource(R.drawable.character_bear_21)
            //    editor.putInt("version", 2)
            } else if (version == 3) {
                mozzi.setImageResource(R.drawable.character_bear_31)
            //    editor.putInt("version", 3)
            } else { }
        }
        else if (kind == 2) {
            if (version == 1) {
                mozzi.setImageResource(R.drawable.character_cat_11)
            //    editor.putInt("version", 1)
            } else if (version == 2) {
                mozzi.setImageResource(R.drawable.character_cat_21)
            //    editor.putInt("version", 2)
            } else if (version == 3) {
                mozzi.setImageResource(R.drawable.character_cat_31)
            //    editor.putInt("version", 3)
            } else { }
        }
        else if (kind == 3) {
            if (version == 1) {
                mozzi.setImageResource(R.drawable.character_dog_11)
            //    editor.putInt("version", 1)
            } else if (version == 2) {
                mozzi.setImageResource(R.drawable.character_dog_21)
            //    editor.putInt("version", 2)
            } else if (version == 3) {
                mozzi.setImageResource(R.drawable.character_dog_31)
            //    editor.putInt("version", 3)
            } else { }
        }
        editor.apply()
    }

    // 캐릭터 경험치 초기화
    fun resetSetting(expSet: Int, gaugeSet: Int) {
        editor.putInt("totalExp", expSet)
        editor.putInt("mealExp", expSet)
        editor.putInt("exerciseExp", expSet)
        editor.putInt("hobbyExp", expSet)

        editor.putInt("gaugeFull", gaugeSet)
        editor.putInt("gaugeHealth", gaugeSet)
        editor.putInt("gaugeHappy", gaugeSet)

        editor.apply()

    }

}