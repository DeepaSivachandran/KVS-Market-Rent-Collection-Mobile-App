package com.example.collectionreport;

import android.content.Context;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Main_Adapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    TextView  Selected;

    public Main_Adapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;

    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Daily_Fragment daily_fragment = new Daily_Fragment();
                return  daily_fragment;
            case 1:
                Monthly_Fragment monthly_fragment = new Monthly_Fragment();
                return monthly_fragment;
            default:
                return null;
        }

    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}