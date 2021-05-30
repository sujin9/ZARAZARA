package com.example.zarazara;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MissionPagerAdapter extends FragmentStatePagerAdapter {
    //List<Fragment> fragments = new ArrayList<>();

    private int tabCount;

    public MissionPagerAdapter(FragmentManager fm, int i){
        super(fm, i);
    //    fragments.add(new MissionFragment1());
    //    fragments.add(new MissionFragment2());
        this.tabCount = i;
    }

    @Override
    public Fragment getItem(int i){
        switch (i) {
            case 0:
                return new MissionFragment1();
            case 1:
                return new MissionFragment2();
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return tabCount;
    }
}
