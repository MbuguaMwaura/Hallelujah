package com.example.hallelujah.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hallelujah.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.editBtn)
    ImageView editBtn;
    @BindView(R.id.userNameProfile)
    TextView userNameProfile;
    @BindView(R.id.church) TextView churchRole;
    @BindView(R.id.adminBtn)
    Button adminBtn;



    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;
    private ValueEventListener valueEventListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("profile").child(uid);
        valueEventListener = database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String name = dataSnapshot1.child("userName").getValue().toString();
                    boolean admin = Boolean.parseBoolean(dataSnapshot1.child("admin").getValue().toString());
                    boolean member = Boolean.parseBoolean(dataSnapshot1.child("member").getValue().toString());

                    userNameProfile.setText(name);

                    if (admin){
                        churchRole.setText("Church Administrator");
                        adminBtn.setVisibility(adminBtn.VISIBLE);
                    }
                    if (member){
                        churchRole.setText("Church Member");
                        adminBtn.setVisibility(adminBtn.INVISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        adminBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == editBtn){
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            startActivity(intent);
        }
        if (v == adminBtn){
            Toast.makeText(getContext(),"clicked",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getContext(), AdminPageActivity.class);
            startActivity(intent);
        }
    }

}
