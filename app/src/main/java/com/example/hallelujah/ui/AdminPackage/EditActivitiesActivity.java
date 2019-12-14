package com.example.hallelujah.ui.AdminPackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hallelujah.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivitiesActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.updateActivities)
    EditText activitiesUpdate;
    @BindView(R.id.updateActivitiesBtn)
    Button updateActivitiesBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activities);
        ButterKnife.bind(this);

        updateActivitiesBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == updateActivitiesBtn){
            
        }
    }
}
