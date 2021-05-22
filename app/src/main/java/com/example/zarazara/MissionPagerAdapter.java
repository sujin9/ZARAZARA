package com.example.zarazara;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MissionPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments = new ArrayList<>();

    public MissionPagerAdapter(FragmentManager fm, int i){
        super(fm);
        fragments.add(new MissionFragment1());
        fragments.add(new MissionFragment2());
    }

    @Override
    public Fragment getItem(int i){
        return fragments.get(i);
    }

    @Override
    public int getCount(){
        return fragments.size();
    }
}
