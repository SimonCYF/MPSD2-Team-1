package com.example.mpsd2.news;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewsAdapter extends FragmentPagerAdapter {

    int tabCount;

    public NewsAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new HomeNewsFragmentActivity();
            case 1:
                return new CovidNewsFragmentActivity();
            case 2:
                return new EducationNewsFragmentActivity();
            case 3:
                return new EntertainmentNewsFragmentActivity();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}