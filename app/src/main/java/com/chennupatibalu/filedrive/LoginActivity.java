package com.chennupatibalu.filedrive;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailET,passwordET;
    private ProgressBar pb;
    private TextView forgotPasswordTv;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        forgotPasswordTv = findViewById(R.id.forgotPasswordTextView);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        Button signinButton = findViewById(R.id.signinButton);
        Button signupButton = findViewById(R.id.signUpButton);
        pb = findViewById(R.id.signInProgressBar);
        pb.setVisibility(View.INVISIBLE);

        signinButton.setOnClickListener(view -> {
            pb.setVisibility(View.VISIBLE);
            startSignIn();
            finish();
        });
        signupButton.setOnClickListener(view ->{
            startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            finish();
        });

        authStateListener = firebaseAuth -> {
            if(firebaseAuth.getCurrentUser()!=null)
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
        };
        forgotPasswordTv.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            finish();
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    private void startSignIn()
    {
        String email = emailET.getEditText().getText().toString();
        String password = passwordET.getEditText().getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Fields are Empty", Toast.LENGTH_LONG).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(!task.isSuccessful())
                {
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Sign in failed. Check your internet connection",Toast.LENGTH_LONG).show();
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}