package com.chennupatibalu.filedrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputLayout emailET,phoneET;
    private MaterialButton sendPasswordButton;
    private ProgressBar pb;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        emailET = findViewById(R.id.forgotEmailET);
        phoneET = findViewById(R.id.forgotPhoneET);
        pb = findViewById(R.id.forgotPasswordProgressBar);
        sendPasswordButton = findViewById(R.id.forgotPasswordButton);
    }

    @Override
    public void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(authStateListener);
    }

    public void forgotPassword(View view) {

        pb.setVisibility(View.VISIBLE);
        SystemClock.sleep(2000);
        String email = emailET.getEditText().getText().toString();
        String phone = phoneET.getEditText().getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(
                                ForgotPasswordActivity.this,
                                "Password reset instructions sent to your mail",
                                Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        finish();
                    }
                    else
                    {
                        pb.setVisibility(View.INVISIBLE);
                        Toast.makeText(ForgotPasswordActivity.this, "Error sending reset password link", Toast.LENGTH_SHORT).show();
                    }
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