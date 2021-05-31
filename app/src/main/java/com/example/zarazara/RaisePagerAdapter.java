package com.example.zarazara;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class RaisePagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public RaisePagerAdapter(FragmentManager fm, int tabCount){
        super(fm, tabCount);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return new RaiseFragment1();
            case 1:
                return new RaiseFragment2();
            case 2:
                return new RaiseFragment3();
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return tabCount;
    }
}
