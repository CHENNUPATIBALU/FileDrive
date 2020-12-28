package com.chennupatibalu.filedrive;

/*
 *
 *  Copyright (c) 2020 Chennupati Balu.
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout emailET,passwordET,nameET,phoneET;
    private TextView signInTv;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private Firebase firebaseRef;
    private ProgressBar pb;
    private String firebaseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseUrl = "https://file-drive-a4f2f.firebaseio.com/";
        mAuth = FirebaseAuth.getInstance();
        firebaseRef = new Firebase(firebaseUrl);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        nameET = findViewById(R.id.personName);
        phoneET = findViewById(R.id.phoneNumber);
        signInTv = findViewById(R.id.alreadyHaveAccountTv);
        signUpButton = findViewById(R.id.signupButton);
        pb = findViewById(R.id.signUpProgressBar);

        signUpButton.setOnClickListener(view -> {
            pb.setVisibility(View.VISIBLE);
            startSignUp();
        });
        signInTv.setOnClickListener(view -> {
            finish();
        });
    }

    public void startSignUp()
    {
        String name = nameET.getEditText().getText().toString();
        long phone = Long.parseLong(phoneET.getEditText().getText().toString());
        String email = emailET.getEditText().getText().toString();
        String password = passwordET.getEditText().getText().toString();

        //Set data to the Firebase
        Firebase firebaseChild = firebaseRef.child("Users").child(name);
        firebaseChild.child("Name").setValue(name);
        firebaseChild.child("Email").setValue(email);
        firebaseChild.child("Mobile Number").setValue(phone);
        SystemClock.sleep(2000);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sign Up failed", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Sign Up Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_LONG).show();
                    pb.setVisibility(View.GONE);
                    finish();
                }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}