package com.example.zarazara;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends FragmentActivity {
    private ViewPager2 tutorialViewPager;
    private FragmentStateAdapter tutorialPagerAdapter;
    private List<Integer> tutorialLayouts;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_tutorial);

        tutorialViewPager = findViewById(R.id.tutorial_viewpager);
      //  tutorialViewPager.setPageTransformer(new Zoom~~); 애니메이션 관련 설정
        tutorialLayouts = new ArrayList<>();
        tutorialLayouts.add(R.layout.tutorial1);
        tutorialLayouts.add(R.layout.tutorial2);
        tutorialLayouts.add(R.layout.tutorial3);
        tutorialLayouts.add(R.layout.tutorial4);

        tutorialPagerAdapter = new TutorialAdapter(this, tutorialLayouts);
        tutorialViewPager.setAdapter(tutorialPagerAdapter);

    }

}
