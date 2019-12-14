package com.example.hallelujah.adapters.ChurchDetailAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hallelujah.R;
import com.example.hallelujah.models.Church;
import com.example.hallelujah.ui.ChurchDetails.ChurchDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChurchPagerAdapter extends RecyclerView.Adapter<ChurchPagerAdapter.ChurchViewHolder> {
    private ArrayList<Church> churches;
    private Context context;

    public ChurchPagerAdapter(ArrayList<Church> churches, Context context) {
        this.churches = churches;
        this.context = context;
    }

    @NonNull
    @Override
    public ChurchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_church_item,viewGroup,false);
       ChurchPagerAdapter.ChurchViewHolder churchViewHolder =  new ChurchPagerAdapter.ChurchViewHolder(view);
       return  churchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChurchViewHolder churchViewHolder, int i) {
        churchViewHolder.bindChurch(churches.get(i));
    }

    @Override
    public int getItemCount() {
        return churches.size();
    }

    public class ChurchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.savedChurchLocation)
        TextView churchLocation;
        @BindView(R.id.savedChurchName) TextView churchName;
        private Context mContext;

        public ChurchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindChurch(Church church) {
            churchLocation.setText(church.getLocation());
            churchName.setText(church.getName());

        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(context, ChurchDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("church", Parcels.wrap(churches));
            mContext.startActivity(intent);
        }
    }
}
