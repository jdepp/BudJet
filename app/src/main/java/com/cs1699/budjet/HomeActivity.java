package com.cs1699.budjet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cs1699.budjet.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private final Context mContext = this;
    private static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        final String currentUserEmail = mFirebaseUser.getEmail();
        String[] emailTokenized = currentUserEmail.split("@");
        currentUser = emailTokenized[0];

        final TextView loggedInUserTextview = (TextView)findViewById(R.id.home_loggedin_user_textview);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = database.child("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String email = child.child("email").getValue().toString();
                    String name = child.child("name").getValue().toString();
                    if(email.equals(currentUserEmail)) {
                        String nameText = loggedInUserTextview.getText() + " " + name;
                        loggedInUserTextview.setText(nameText);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onAddIncomeExpenseClicked(View view) {
            //Toast.makeText(mContext, "Hello", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(this, AddIncomeExpenseActivity.class);
            startActivity(myIntent);
    }

    public static String getCurrentUser() {
        return HomeActivity.currentUser;
    }
}

