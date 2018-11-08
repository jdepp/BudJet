package com.cs1699.budjet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private final Context mContext = this;
    private EditText username;
    private EditText passwordF;
    private Button registerButton;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.login_email_edittext);
        passwordF = (EditText) findViewById(R.id.login_password_edittext);
        loginButton = (Button) findViewById(R.id.login_button);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null) {
                    Toast.makeText(mContext, "Automatically logged in user " + firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startSignIn();
            }
        });

    }


    @Override
    protected void onStart(){
        super.onStart();

        mAuthListener.onAuthStateChanged(mAuth);
    }

    private void startSignIn(){

        String email = username.getText().toString().trim();
        String password = passwordF.getText().toString().trim();

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please Fill in Both Fields, or press 'Register'", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Please Fill in Both Fields, or press 'Register'", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password Combination", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    }
                }

            });

        }
    }

    public void onForgotPasswordClicked(View view) {
        Toast.makeText(mContext, "Forgot password clicked", Toast.LENGTH_SHORT).show();
    }

}
