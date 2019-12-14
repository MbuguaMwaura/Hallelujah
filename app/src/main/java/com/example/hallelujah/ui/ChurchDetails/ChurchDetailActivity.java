package com.example.hallelujah.ui.ChurchDetails;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hallelujah.R;
import com.example.hallelujah.adapters.ChurchDetailAdapters.ChurchFragmentAdapter;
import com.example.hallelujah.adapters.ChurchDetailAdapters.ChurchPagerAdapter;
import com.example.hallelujah.models.Church;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChurchDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private ChurchFragmentAdapter adapter;
    private ArrayList<Church> churches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_church_detail);
        ButterKnife.bind(this);

        churches =  Parcels.unwrap(getIntent().getParcelableExtra("church"));
        int startingPosition = getIntent().getIntExtra("position",0);

        adapter = new ChurchFragmentAdapter(getSupportFragmentManager(),churches);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(startingPosition);


    }
}
