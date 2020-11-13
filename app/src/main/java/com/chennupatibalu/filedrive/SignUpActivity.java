package com.chennupatibalu.filedrive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailET,passwordET,nameET,phoneET;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        nameET = findViewById(R.id.personName);
        phoneET = findViewById(R.id.phoneNumber);
        signUpButton = findViewById(R.id.signupButton);

        signUpButton.setOnClickListener(view -> startSignUp());
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }
    public void startSignUp()
    {
        String name = nameET.getText().toString();
        long phone = Long.parseLong(phoneET.getText().toString());
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(!task.isSuccessful())
            {
                Toast.makeText(getApplicationContext(),"Sign Up failed",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Sign Up Success",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                Toast.makeText(getApplicationContext(),"Login with your credentials",Toast.LENGTH_LONG).show();
            }
        });
    }
}