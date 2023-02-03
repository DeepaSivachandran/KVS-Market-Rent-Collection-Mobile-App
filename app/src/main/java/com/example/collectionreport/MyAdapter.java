package com.example.collectionreport;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    TextView Selected;
    String City_name;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs,String City_name) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.City_name = City_name;

    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ActiveFragment activeFragment = new ActiveFragment(myContext,City_name);
                return activeFragment;
            case 1:
                InactiveFragment inactiveFragment = new InactiveFragment(myContext,City_name);
                return inactiveFragment;
            case 2:
                PendingFragment pendingFragment = new PendingFragment(myContext,City_name);
                return pendingFragment;
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