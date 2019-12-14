package com.example.hallelujah.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hallelujah.R;
import com.example.hallelujah.models.Church;
import com.example.hallelujah.ui.ChurchDetails.ChurchDetailActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FirebaseChurchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseChurchViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindChurch(Church church){
        TextView savedChurchName = (TextView) mView.findViewById(R.id.savedChurchName);
        TextView savedChurchLocation = (TextView) mView.findViewById(R.id.savedChurchLocation);

        savedChurchLocation.setText(church.getLocation());
        savedChurchName.setText(church.getName());
    }

    @Override
    public void onClick(View v) {
        final ArrayList<Church> churches = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("churches");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    churches.add(snapshot.getValue(Church.class));
                }
//
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, ChurchDetailActivity.class);
                intent.putExtra("position",itemPosition+"");
                intent.putExtra("church", Parcels.wrap(churches));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
