package com.chennupatibalu.filedrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET,passwordET;
    private Button signinButton,signupButton;

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
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this,SignUpActivity.class)));

        signinButton.setOnClickListener(view -> startSignIn());
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
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

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
                    Toast.makeText(getApplicationContext(),"Sign in Success",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}