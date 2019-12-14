package com.example.hallelujah.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hallelujah.R;
import com.example.hallelujah.models.Church;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseChurchListAdapter extends FirebaseRecyclerAdapter<Church, FirebaseChurchViewHolder> {
    private Context mContext;
    private ArrayList<Church> mChurches = new ArrayList<>();


    public FirebaseChurchListAdapter(@NonNull FirebaseRecyclerOptions<Church> options, ArrayList<Church> churches) {
        super(options);
        this.mChurches = churches;
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseChurchViewHolder holder, int position, @NonNull Church model) {
        holder.bindChurch(model);
    }

    @NonNull
    @Override
    public FirebaseChurchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_church_item,viewGroup,false);
        return new FirebaseChurchViewHolder(view);
    }
}
