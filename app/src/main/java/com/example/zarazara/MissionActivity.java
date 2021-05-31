package com.example.zarazara;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class MissionActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        String userCoin = Integer.toString(sharedPreferences.getInt("userCoin", 0));

        TextView coinText = (TextView)findViewById(R.id.userCoin);
        coinText.setText(userCoin);

        tabLayout = findViewById(R.id.missionTab);
        viewPager = findViewById(R.id.missionViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("일일미션"));
        tabLayout.addTab(tabLayout.newTab().setText("누적미션"));

        viewPager.setAdapter(new MissionPagerAdapter(getSupportFragmentManager(), 2));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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