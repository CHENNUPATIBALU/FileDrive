package com.chennupatibalu.filedrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailET,passwordET;
    private Button signinButton,signupButton;
    private ProgressBar pb;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        signinButton = findViewById(R.id.signinButton);
        signupButton = findViewById(R.id.signUpButton);
        pb = findViewById(R.id.signInProgressBar);
        pb.setVisibility(View.INVISIBLE);

        signinButton.setOnClickListener(view -> {
            pb.setVisibility(View.VISIBLE);
            startSignIn();
        });
        signupButton.setOnClickListener(view ->{
            Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(i);
        });

        authStateListener = firebaseAuth -> {
            if(firebaseAuth.getCurrentUser()!=null)
                startActivity(new Intent(LoginActivity.this,DriveActivity.class));
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
                    finish();
                }
            });
        }
    }
}