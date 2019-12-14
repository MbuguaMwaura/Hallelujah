package com.example.hallelujah.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.example.hallelujah.R;
import com.example.hallelujah.models.Note;
import com.example.hallelujah.util.ItemTouchHelperViewHolder;

public class FirebaseNotesViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    View mView;
    Context mContext;
    public CardView mValueCardView;
    public TextView mSavedItem;

    public FirebaseNotesViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindNote(Note note){
        mValueCardView = (CardView) mView.findViewById(R.id.card1);
        mSavedItem = (TextView) mView.findViewById(R.id.savedItem);
        TextView noteTextView = (TextView) mView.findViewById(R.id.savedItem);
        noteTextView.setText(note.getNote());
    }

    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.7f)
                .scaleY(0.7f)
                .setDuration(300);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }
}
