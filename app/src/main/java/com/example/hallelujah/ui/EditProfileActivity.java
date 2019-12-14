package com.example.hallelujah.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hallelujah.R;
import com.example.hallelujah.models.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.churchAdmin)
    CheckBox churchAdmin;
    @BindView(R.id.churchMember) CheckBox churchMember;
    @BindView(R.id.submitEditBtn)
    Button submitEditBtn;
    @BindView(R.id.editName)
    EditText editTextName;

    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        submitEditBtn.setOnClickListener(this);
        churchAdmin.setOnClickListener(this);
        churchMember.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == submitEditBtn){
          String name = editTextName.getText().toString();
          boolean admin = churchAdmin.isChecked();
          boolean member = churchMember.isChecked();
          userProfile = new UserProfile(name,admin,member);
          if (admin && member) {
              Toast.makeText(getApplicationContext(),"Please select only one role",Toast.LENGTH_LONG).show();
              return;
          }
            if ((!admin && !member)) {
                Toast.makeText(getApplicationContext(),"Please select a role",Toast.LENGTH_LONG).show();
                return;
            }
            if(name.isEmpty()){
               editTextName.setError("Please input your name");
               return;
            }
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile").child(uid);
            reference.child(uid).setValue(userProfile);
            Toast.makeText(getApplicationContext(),"Updating your details",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("role", admin);

        }

    }
}
