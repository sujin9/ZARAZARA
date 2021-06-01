package com.example.zarazara;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MissionPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public MissionPagerAdapter(FragmentManager fm, int tabCount){
        super(fm, tabCount);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i){
        switch (i){
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
