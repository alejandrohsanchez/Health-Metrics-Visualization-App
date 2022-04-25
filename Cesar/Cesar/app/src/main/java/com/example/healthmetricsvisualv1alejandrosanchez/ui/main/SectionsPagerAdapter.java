package com.example.healthmetricsvisualv1alejandrosanchez.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//import com.example.healthmetricsvisualv1alejandrosanchez.CalendarViewHolder;
import com.example.healthmetricsvisualv1alejandrosanchez.Fragment1;
import com.example.healthmetricsvisualv1alejandrosanchez.Fragment2;
import com.example.healthmetricsvisualv1alejandrosanchez.Fragment3;
import com.example.healthmetricsvisualv1alejandrosanchez.Fragment4;
import com.example.healthmetricsvisualv1alejandrosanchez.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new Fragment3();
                break;
            case 3:
                fragment = new Fragment4();
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Number of pages to be shown
        return 4;
    }
}