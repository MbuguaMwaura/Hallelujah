package com.example.hallelujah.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hallelujah.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.loginuserEmail)
    EditText loginEmail;
    @BindView(R.id.loginuserPassWord) EditText loginPassword;
    @BindView(R.id.loginBtn)
    Button loginBtn;
    @BindView(R.id.signUpTextView)
    TextView signUpTextView;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(this);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };

        createProgressDialog();


        signUpTextView.setOnClickListener(this);


    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging in...");
        progressDialog.setMessage("Authentication with Firebase");
        progressDialog.setCancelable(false);
    }

    public void onClick(View view){
        if (view == signUpTextView){
            Intent intent = new Intent(this, CreateAccountActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if (view == loginBtn){
            loginUser();
        }
    }

    private void loginUser() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        if (email.equals("")) {
            loginEmail.setError("Please enter your email");
            return;
        }
        if (password.equals("")) {
            loginPassword.setError("Password cannot be blank");
            return;
        }
        progressDialog.show();

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                FirebaseUser user  = auth.getCurrentUser();
                String email = user.getEmail();
                String uid = user.getUid();
                HashMap<Object,String> hashMap = new HashMap<>();
                hashMap.put("email",email);
                hashMap.put("uid",uid);
                hashMap.put("name", "" );
                hashMap.put("image","");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference  = database.getReference("Users");
                reference.child(uid).setValue(hashMap);

                if (!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
