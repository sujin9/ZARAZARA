package com.example.zarazara;

import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RaiseActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise);

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
