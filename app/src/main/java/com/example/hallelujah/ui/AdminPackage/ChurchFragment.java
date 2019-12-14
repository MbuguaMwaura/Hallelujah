package com.example.hallelujah.ui.AdminPackage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hallelujah.R;
import com.example.hallelujah.adapters.AdminAdapters.FirebaseChurchAdminViewHolder;
import com.example.hallelujah.adapters.FirebaseChurchViewHolder;
import com.example.hallelujah.models.Church;
import com.example.hallelujah.ui.CreateChurchActivity;
import com.example.hallelujah.ui.SavedChurchActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChurchFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.cardChurch)
    CardView cardViewChurch;
//    @BindView(R.id.savedChurchesCard) CardView cardViewSavedChurches;
    @BindView(R.id.recyclerViewChurches)
    RecyclerView recyclerViewChurch;
    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<Church, FirebaseChurchAdminViewHolder> adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_church, container, false);
        ButterKnife.bind(this,view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("adminchurches").child(uid);
        setUpFirebaseAdapter();
//        cardViewSavedChurches.setOnClickListener(this);
        cardViewChurch.setOnClickListener(this);
        return view;
    }


    private void setUpFirebaseAdapter() {
        FirebaseRecyclerOptions<Church> options = new FirebaseRecyclerOptions.Builder<Church>()
                .setQuery(reference,Church.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Church, FirebaseChurchAdminViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseChurchAdminViewHolder holder, int position, @NonNull Church model) {
                holder.bindChurch(model);
            }

            @NonNull
            @Override
            public FirebaseChurchAdminViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_admin_church, viewGroup,false);
                return new FirebaseChurchAdminViewHolder(view);
            }
        };
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewChurch.setLayoutManager(horizontalLayoutManagaer);
        recyclerViewChurch.setAdapter(adapter);

    }



    @Override
    public void onClick(View v) {
        if (v == cardViewChurch){
            Intent intent = new Intent(getContext(), CreateChurchActivity.class);
            startActivity(intent);
        }
//        if (v == cardViewSavedChurches){
//            Intent intent = new Intent(getContext(), SavedChurchActivity.class);
//            startActivity(intent);
//        }
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null){
            adapter.stopListening();
        }
    }



}
