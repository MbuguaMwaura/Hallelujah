package com.example.hallelujah.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hallelujah.R;
import com.example.hallelujah.models.UserProfile;
import com.example.hallelujah.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userEmail) EditText userEmail;
    @BindView(R.id.userPassWord) EditText userPassword;
    @BindView(R.id.userPassWordConfirm) EditText userPasswordConfirm;
    @BindView(R.id.signUpBtn)
    Button signUpBtn;
    @BindView(R.id.userNumber) EditText userNumber;
    @BindView(R.id.loginTextView)
    TextView loginTextView;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog progressDialog;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        auth = FirebaseAuth.getInstance();
        createAuthStateListener();
        createAuthProgressDialog();

        loginTextView.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
    }

    private void createAuthProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Authenticating with Firebase");
        progressDialog.setCancelable(false);
    }


    private void createAuthStateListener() {
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    public void onClick(View view){
        if (view == loginTextView){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if(view == signUpBtn){
            createNewUser();
        }
    }

    private void createNewUser() {
        mName = userName.getText().toString().trim();
        final String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        String passwordConfirm = userPasswordConfirm.getText().toString().trim();
        final String number = userNumber.getText().toString().trim();

        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(mName);
        boolean validPassword = isValidPassword(password, passwordConfirm);
        if (!validEmail || !validName || !validPassword) return;
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){

                    createFirebaseUserProfile(task.getResult().getUser());
                    FirebaseUser user = auth.getCurrentUser();
                    String email = user.getEmail();
                    String uid = user.getUid();


                    DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("profile").child(uid);

                    String name ="User";
                    boolean admin = false;
                    boolean member = false;
                    UserProfile  userProfile = new UserProfile(name,admin,member);

                    reference1.child(uid).setValue(userProfile);
                    HashMap<Object,String> hashMap = new HashMap<>();
                    hashMap.put("email",email);
                    hashMap.put("uid",uid);
                    hashMap.put("name", "" );
                    hashMap.put("image","");
                    hashMap.put("number",number);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference  = database.getReference("Users");
                    reference.child(uid).setValue(hashMap);

                }else {
                    Toast.makeText(CreateAccountActivity.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void createFirebaseUserProfile(final FirebaseUser user){
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .build();
        user.updateProfile(addProfileName);

    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            userEmail.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

    private boolean isValidName(String name) {
        if (name.equals("")) {
            userName.setError("Please enter your name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            userPassword.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            userPassword.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
