package com.example.hallelujah.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hallelujah.R;
import com.example.hallelujah.adapters.FirebaseChurchViewHolder;
import com.example.hallelujah.models.Church;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedChurchActivity extends AppCompatActivity {
    @BindView(R.id.recyclerViewChurches)
    RecyclerView recyclerViewChurch;
    private DatabaseReference reference;

    private FirebaseRecyclerAdapter<Church, FirebaseChurchViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_church);
        ButterKnife.bind(this);
        reference = FirebaseDatabase.getInstance().getReference().child("churches");
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        FirebaseRecyclerOptions<Church> options = new FirebaseRecyclerOptions.Builder<Church>()
                .setQuery(reference,Church.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<Church, FirebaseChurchViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseChurchViewHolder holder, int position, @NonNull Church model) {
                holder.bindChurch(model);
            }

            @NonNull
            @Override
            public FirebaseChurchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_church_item, viewGroup,false);
                return new FirebaseChurchViewHolder(view);
            }
        };
        recyclerViewChurch.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChurch.setAdapter(adapter);

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
