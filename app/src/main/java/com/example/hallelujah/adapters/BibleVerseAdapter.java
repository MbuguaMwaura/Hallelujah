package com.example.hallelujah.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hallelujah.R;
import com.example.hallelujah.models.BibleVerse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BibleVerseAdapter extends RecyclerView.Adapter<BibleVerseAdapter.BibleVerseViewHolder> {
    private ArrayList<BibleVerse> mBibleVerse;
    private Context mContext;

    public BibleVerseAdapter(ArrayList<BibleVerse> mBibleVerse, Context mContext) {
        this.mBibleVerse = mBibleVerse;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BibleVerseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bible_verse_item,viewGroup,false);
        BibleVerseViewHolder verseViewHolder = new BibleVerseViewHolder(view);
        return verseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BibleVerseViewHolder bibleVerseViewHolder, int i) {
        bibleVerseViewHolder.bindVerse(mBibleVerse.get(i));
    }

    @Override
    public int getItemCount() {
        return mBibleVerse.size();
    }

    public class BibleVerseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bibleBookItem)
        TextView bibleBookItem;
        @BindView(R.id.bibleChapterItem) TextView bibleChapterItem;
        @BindView(R.id.bibleVerseFromItem) TextView bibleVerseFromItem;
        @BindView(R.id.bibleVerseToItem) TextView bibleVerseToItem;
        @BindView(R.id.verseItem) TextView verseItem;
        private Context mContext;

        public BibleVerseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

        }

        public void bindVerse(BibleVerse bibleVerse) {
            bibleBookItem.setText(bibleVerse.getBook());
            bibleChapterItem.setText("Chapter "+bibleVerse.getChapter()+": ");
            bibleVerseFromItem.setText(" Verse "+ bibleVerse.getVerseFrom()   +" - " );
            bibleVerseToItem.setText(bibleVerse.getVerseTo());
            verseItem.setText(bibleVerse.getOutput());
        }
    }
}
