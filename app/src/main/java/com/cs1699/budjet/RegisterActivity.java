package com.cs1699.budjet;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs1699.budjet.models.Budget;
import com.cs1699.budjet.models.Category;
import com.cs1699.budjet.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private final Context mContext = this;
    private EditText inputEmail, inputPassword, confirmPassword, inputName, securityA;
    private Spinner securityQ;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSignUp = (Button) findViewById(R.id.register_button);
        inputEmail = (EditText) findViewById(R.id.register_email_edittext);
        inputPassword = (EditText) findViewById(R.id.register_password_edittext);
        confirmPassword = (EditText) findViewById(R.id.confirm_password_edittext);
        inputName = (EditText) findViewById(R.id.register_name_edittext);
        securityQ = (Spinner) findViewById(R.id.secQSpinner);
        securityA = (EditText) findViewById(R.id.sec_answer);
        auth = FirebaseAuth.getInstance();

        // This block of code is triggered when the "Register" button is clicked
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String name = inputName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String cPassword = confirmPassword.getText().toString().trim();
                final String secQ = securityQ.getSelectedItem().toString().trim();
                final String secA = securityA.getText().toString().trim();

                Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                Matcher matcher = pattern.matcher(email);

                if (!password.equals(cPassword))
                {
                    Toast.makeText(mContext, "Your passwords do not match", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(mContext, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!matcher.matches()) {
                    Toast.makeText(mContext, "Not a valid email", Toast.LENGTH_SHORT).show();
                    inputPassword.getText().clear();
                    return;
                }

                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(mContext, "Enter Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(mContext, "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(mContext, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    inputPassword.getText().clear();
                    confirmPassword.getText().clear();
                    return;
                }

                // Creates user in Firebase's Authentication and Database
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "Welcome to BudJet " + name, Toast.LENGTH_SHORT).show();
                                if (!task.isSuccessful()) {
                                    try {
                                        throw task.getException();
                                    }
                                    catch(FirebaseAuthUserCollisionException e) {
                                        Toast.makeText(mContext, "User with this email already exists", Toast.LENGTH_SHORT).show();
                                    }
                                    catch(Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Authentication failed, caused by: " + task.getException(), Toast.LENGTH_LONG).show();
                                    }
                                    clearInputs();
                                }
                                else {
                                    User newUser = new User(name, email);  // create the new User and store in the database



                                    newUser.setSecQ(secQ);
                                    newUser.setSecA(secA);
                                    String[] splitEmail = email.split("@");
                                    String localEmailName = splitEmail[0];
                                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");  // gets the reference to the "users" portion of the database
                                    usersRef.child(localEmailName).setValue(newUser);  // generates unique ID for the user and saves to database

                                    // Create 3 default categories for the user
                                    Category rent_c = new Category("Rent", "For Housing Costs", 2000);
                                    Category food_c = new Category("Food", "For Food and Drink", 500);
                                    Category entertainment_c = new Category("Entertainment", "For any miscellaneous spending", 500);

                                    // Create 3 default (correlating) budgets
                                    Budget rent_b = new Budget(rent_c, 2000);
                                    Budget food_b = new Budget(food_c, 500);
                                    Budget entertainment_b = new Budget(entertainment_c, 500);

                                    usersRef.child(localEmailName).child("categories").push().setValue(rent_c);
                                    usersRef.child(localEmailName).child("categories").push().setValue(food_c);
                                    usersRef.child(localEmailName).child("categories").push().setValue(entertainment_c);

                                    usersRef.child(localEmailName).child("budgets").push().setValue(rent_b);
                                    usersRef.child(localEmailName).child("budgets").push().setValue(food_b);
                                    usersRef.child(localEmailName).child("budgets").push().setValue(entertainment_b);

                                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    public void clearInputs() {
        inputEmail.getText().clear();
        inputPassword.getText().clear();
    }

}
