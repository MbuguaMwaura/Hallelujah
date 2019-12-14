package com.example.hallelujah.adapters.ChurchDetailAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hallelujah.models.Church;
import com.example.hallelujah.ui.ChurchDetails.ChurchDetailsFragment;

import java.util.ArrayList;

public class ChurchFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Church> churches;

    public ChurchFragmentAdapter(FragmentManager fm, ArrayList<Church> churches) {
        super(fm);
        this.churches = churches;
    }

    @Override
    public Fragment getItem(int i) {
        return ChurchDetailsFragment.newInstance(churches.get(i));
    }

    @Override
    public int getCount() {
        return churches.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return churches.get(position).getName();
    }
}
