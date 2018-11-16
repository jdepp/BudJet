package com.cs1699.budjet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private final Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onAddIncomeExpenseClicked(View view) {
            //Toast.makeText(mContext, "Hello", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(this, AddIncomeExpenseActivity.class);
            startActivity(myIntent);
        }
}

