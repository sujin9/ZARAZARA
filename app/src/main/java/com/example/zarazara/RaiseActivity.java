package com.example.zarazara;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class RaiseActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise);

        // 상단 보유 코인 표시, 전체 경험치 표시
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        String userCoin = Integer.toString(sharedPreferences.getInt("userCoin", 0));
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.expProgressBar);
        progressBar.setProgress(sharedPreferences.getInt("totalExp",0));
        TextView coinText = (TextView)findViewById(R.id.userCoin);
        coinText.setText(userCoin+"C");

        tabLayout = findViewById(R.id.raiseTab);
        viewPager = findViewById(R.id.raiseViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("식사"));
        tabLayout.addTab(tabLayout.newTab().setText("운동"));
        tabLayout.addTab(tabLayout.newTab().setText("취미"));

        viewPager.setAdapter(new RaisePagerAdapter(getSupportFragmentManager(), 3));

        // pager 가 변경될 때 발생하는 이벤트
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // tab이 선택될 때 page 변경
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        
    }
}
