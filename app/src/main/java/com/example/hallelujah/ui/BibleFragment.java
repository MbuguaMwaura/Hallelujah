package com.example.hallelujah.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hallelujah.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BibleFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.bibleBook)
    EditText bibleBook;
    @BindView(R.id.bibleChapter) EditText bibleChapter;
    @BindView(R.id.bibleVerseFrom) EditText bibleVerseFrom;
    @BindView(R.id.bibleVerseTo) EditText bibleVerseTo;
    @BindView(R.id.verseBtn)
    Button verseBtn;

    public BibleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_bible, container, false);
        ButterKnife.bind(this,view);


        verseBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == verseBtn){
            String book = bibleBook.getText().toString();
            String chapter = bibleChapter.getText().toString();
            String verseTo = bibleVerseTo.getText().toString();
            String verseFrom  = bibleVerseFrom.getText().toString();
            Intent intent = new Intent(getContext(),BibleVerseActivity.class);
            intent.putExtra("book", book);
            intent.putExtra("chapter", chapter);
            intent.putExtra("verseTo", verseTo);
            intent.putExtra("verseFrom", verseFrom);
            startActivity(intent);
        }
    }
}
