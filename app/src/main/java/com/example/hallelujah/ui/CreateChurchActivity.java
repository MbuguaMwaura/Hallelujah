package com.example.hallelujah.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hallelujah.R;
import com.example.hallelujah.models.Church;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateChurchActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.createChurchBtn)
    Button createChurchBtn;
    @BindView(R.id.churchDescription)
    EditText churchDescription;
    @BindView(R.id.churchLocation) EditText churchLocation;
    @BindView(R.id.churchName) EditText churchName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_church);
        ButterKnife.bind(this);

        createChurchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == createChurchBtn){
            String churchTitle = churchName.getText().toString();
            String churchLocate = churchLocation.getText().toString();
            String churchDescribe = churchDescription.getText().toString();
            Church church = new Church(churchTitle,churchDescribe,churchLocate);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("churches");
            DatabaseReference pushRef = reference.push();
            String pushID = pushRef.getKey();
            String adminUID = uid;
            church.setAdminUid(adminUID);
            church.setPushID(pushID);
            pushRef.setValue(church);

            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("adminchurches").child(uid);
            DatabaseReference pushRef2 = reference2.push();
            String pushID2 = pushRef2.getKey();
            church.setAdminUid(adminUID);
            church.setPushID(pushID2);
            pushRef2.setValue(church);

        }
    }
}
