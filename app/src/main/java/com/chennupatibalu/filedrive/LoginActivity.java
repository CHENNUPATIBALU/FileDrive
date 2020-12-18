package com.chennupatibalu.filedrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailET,passwordET;
    private ProgressBar pb;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Firebase firebaseRef;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        String firebaseUrl = "https://file-drive-a4f2f.firebaseio.com/";
        firebaseRef = new Firebase(firebaseUrl);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        Button signinButton = findViewById(R.id.signinButton);
        Button signupButton = findViewById(R.id.signUpButton);
        pb = findViewById(R.id.signInProgressBar);
        pb.setVisibility(View.INVISIBLE);

        signinButton.setOnClickListener(view -> {
            pb.setVisibility(View.VISIBLE);
            startSignIn();
        });
        signupButton.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
        });

        authStateListener = firebaseAuth -> {
            if(firebaseAuth.getCurrentUser()!=null)
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    public void startSignIn()
    {
        String email = emailET.getEditText().getText().toString();
        String password = passwordET.getEditText().getText().toString();

        SystemClock.sleep(2000);

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Fields are Empty", Toast.LENGTH_LONG).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(!task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Sign in failed",Toast.LENGTH_LONG).show();
                }
                else
                {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Sign in Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            });

        }
    }
}