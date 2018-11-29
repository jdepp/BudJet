package com.cs1699.budjet;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cs1699.budjet.models.Budget;
import com.cs1699.budjet.models.Category;
import com.cs1699.budjet.models.Income;
import com.cs1699.budjet.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class GraphicalActivity extends AppCompatActivity {

    PieChartView pieChartView = findViewById(R.id.chart);

    List<SliceValue> pieData = new ArrayList<>();

    private final Context mContext = this;
    private static String currentUser;



}
