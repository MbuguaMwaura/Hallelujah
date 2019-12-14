package com.example.hallelujah.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.hallelujah.R;
import com.example.hallelujah.models.Note;
import com.example.hallelujah.ui.NotesDetailActivity;
import com.example.hallelujah.ui.SavedNotesActivity;
import com.example.hallelujah.util.ItemTouchHelperAdapter;
import com.example.hallelujah.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

public class FirebaseNotesListAdapter extends FirebaseRecyclerAdapter<Note, FirebaseNotesViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference mRef;
    private OnStartDragListener mOnStartDragListener;
    private Context mContext;
    private ChildEventListener mChildEventListener;
    private ArrayList<Note> mNotes = new ArrayList<>();


    public FirebaseNotesListAdapter(@NonNull FirebaseRecyclerOptions<Note> options, Query ref, OnStartDragListener onStartDragListener, Context context ) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener = onStartDragListener;
        mContext = context;

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mNotes.add(dataSnapshot.getValue(Note.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }


    @Override
    protected void onBindViewHolder(@NonNull final FirebaseNotesViewHolder holder, int position, @NonNull Note model) {
        holder.bindNote(model);
        holder.mSavedItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NotesDetailActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                intent.putExtra("note", Parcels.wrap(mNotes));
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FirebaseNotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_note, viewGroup,false);
        return new FirebaseNotesViewHolder(view);

    }
    private void setIndexInFirebase() {
        for (Note note : mNotes) {
            int index = mNotes.indexOf(note);
            DatabaseReference ref = getRef(index);
            note.setIndex(Integer.toString(index));
            ref.setValue(note);
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mNotes,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        setIndexInFirebase();
        return false;
    }


    @Override
    public void onItemDismiss(int position) {
        mNotes.remove(position);
        getRef(position).removeValue();
    }


    @Override
    public void stopListening() {
        super.stopListening(); mRef.removeEventListener(mChildEventListener);
    }

}
