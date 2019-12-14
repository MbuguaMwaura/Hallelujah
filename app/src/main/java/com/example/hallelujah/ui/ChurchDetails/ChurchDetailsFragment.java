package com.example.hallelujah.ui.ChurchDetails;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hallelujah.R;
import com.example.hallelujah.models.Church;
import com.example.hallelujah.models.UserProfile;
import com.example.hallelujah.ui.AdminPackage.EditActivitiesActivity;
import com.example.hallelujah.Mpesa.TitheActivity;
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
public class ChurchDetailsFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.churchDetailsActivitiesTitle)
    TextView textViewActivitesTitle;
    @BindView(R.id.churchDetailsImage)
    ImageView imageViewChurchDetails;
    @BindView(R.id.churchDetailsName) TextView textViewChurchDetailsName;
    @BindView(R.id.churchDetailsActivities) TextView textViewActivities;
    @BindView(R.id.titheBtn)
    Button buttonTithe;
    @BindView(R.id.activityBtn) Button editActivities;
    private Church church;
    private ValueEventListener valueEventListener;
    private UserProfile userProfile;

    public static ChurchDetailsFragment newInstance(Church church){
        ChurchDetailsFragment churchDetailsFragment = new ChurchDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("church", Parcels.wrap(church));
        churchDetailsFragment.setArguments(args);
        return  churchDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        church = Parcels.unwrap(getArguments().getParcelable("church"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_church_details, container, false);
        ButterKnife.bind(this,view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();
         DatabaseReference database = FirebaseDatabase.getInstance().getReference("churches");
        valueEventListener = database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String adminid =dataSnapshot1.child("adminUid").getValue().toString();

                    boolean admin = adminid.matches(uid);

                    if (admin){
                        editActivities.setVisibility(editActivities.VISIBLE);
                    }
                    if(!admin){
                        editActivities.setVisibility(editActivities.INVISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        textViewChurchDetailsName.setText(church.getName());
        textViewActivitesTitle.setText("Activities");
        textViewActivities.setText(church.getActivities());
        buttonTithe.setOnClickListener(this);
        editActivities.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(final View v) {
        if(v == editActivities){
            Intent intent = new Intent(getContext(), EditActivitiesActivity.class);
            startActivity(intent);
        }
        if (v == buttonTithe){
            Intent intent =new Intent(getContext(), TitheActivity.class);
            startActivity(intent);
        }
    }
}
