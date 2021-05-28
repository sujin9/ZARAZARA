package com.example.zarazara

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission)

        // 상단 보유 코인 표시
        val sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE)
        val coinText = findViewById<TextView>(R.id.userCoin)
        coinText.text = sharedPreferences.getInt("userCoin", 0).toString()
    }
}