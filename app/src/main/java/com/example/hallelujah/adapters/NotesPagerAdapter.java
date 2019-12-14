package com.example.hallelujah.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hallelujah.models.Note;
import com.example.hallelujah.ui.NotesDetailFragment;

import java.util.ArrayList;

public class NotesPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Note> mNotes;

    public NotesPagerAdapter(FragmentManager fm, ArrayList<Note> notes) {
        super(fm);
        this.mNotes = notes;
    }

    @Override
    public Fragment getItem(int i) {
        return NotesDetailFragment.newInstance(mNotes.get(i));
    }

    @Override
    public int getCount() {
        return mNotes.size();
    }
}
