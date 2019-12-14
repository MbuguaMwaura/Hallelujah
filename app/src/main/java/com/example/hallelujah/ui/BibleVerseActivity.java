package com.example.hallelujah.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.hallelujah.R;
import com.example.hallelujah.adapters.BibleVerseAdapter;
import com.example.hallelujah.models.BibleVerse;
import com.example.hallelujah.services.BibleService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BibleVerseActivity extends AppCompatActivity {
    ArrayList<BibleVerse> mBibleVerse;
    @BindView(R.id.recyclerViewVerse)
    RecyclerView recyclerViewVerse;
    private BibleVerseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible_verse);
        ButterKnife.bind(this);

        getVerse();

    }

    private void getVerse() {
        Intent intent = getIntent();
        String book = intent.getStringExtra("book");
        String chapter = intent.getStringExtra("chapter");
        String verseFrom = intent.getStringExtra("verseFrom");
        String verseTo = intent.getStringExtra("verseTo");

        Toast.makeText(BibleVerseActivity.this,book +" "+ chapter +" "+ verseFrom +" "+ verseTo ,Toast.LENGTH_LONG).show();

        final BibleService bibleService = new BibleService();
        bibleService.findVerse(book, chapter, verseFrom, verseTo, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mBibleVerse = bibleService.processResults(response);
                BibleVerseActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new BibleVerseAdapter(mBibleVerse,getApplicationContext());
                        recyclerViewVerse.setAdapter(adapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BibleVerseActivity.this);
                        recyclerViewVerse.setLayoutManager(layoutManager);
                        recyclerViewVerse.setHasFixedSize(true);

                    }
                });
            }
        });
    }

}
