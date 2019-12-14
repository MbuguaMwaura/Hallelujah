package com.example.hallelujah.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hallelujah.R;
import com.example.hallelujah.adapters.NotesPagerAdapter;
import com.example.hallelujah.models.Note;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private NotesPagerAdapter pagerAdapter;
    ArrayList<Note> mNotes  = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_detail);
        ButterKnife.bind(this);
        mNotes = Parcels.unwrap(getIntent().getParcelableExtra("note"));
        pagerAdapter = new NotesPagerAdapter(getSupportFragmentManager(),mNotes);
        int startingPosition = getIntent().getIntExtra("position",0);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(startingPosition,false);
    }
}
