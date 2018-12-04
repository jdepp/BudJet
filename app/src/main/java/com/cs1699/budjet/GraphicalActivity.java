package com.cs1699.budjet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs1699.budjet.models.Budget;
import com.cs1699.budjet.models.Expense;
import com.cs1699.budjet.models.Income;
import com.cs1699.budjet.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class GraphicalActivity extends AppCompatActivity {

    PieChartView pieChartView;



    private final Context mContext = this;
    private static String currentUser;
    double income_sum = 0;
    double expense_sum = 0;
    double budget_sum = 0;
    double total, remainder;
    String show_graph;
    Spinner graph_c;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graphical);
        pieChartView = findViewById(R.id.chart);
        final List<SliceValue> pieData = new ArrayList<>();

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        final String currentUserEmail = mFirebaseUser.getEmail();
        String[] emailTokenized = currentUserEmail.split("@");
        currentUser = emailTokenized[0];

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = database.child("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    users.add(user);
                    if (user.getEmail().equals(currentUserEmail)) {
                        // We now have access to the currently logged in User and can access its properties
                        HashMap<String, Income> incomes = user.getIncomes();  // The user's list properties are store as HashMaps in Firebase
                        for (Map.Entry<String, Income> income : incomes.entrySet()) {  // Iterate through the hashmap of incomes and append each income to a string to be displayed
                            income_sum += income.getValue().getValue();
                        }
                        HashMap<String, Expense> expenses = user.getExpenses();  // Show expenses as well.
                        for (Map.Entry<String, Expense> expense : expenses.entrySet()) {  // This is how we're going to iterate thru the user's hashpmap properties
                            expense_sum += expense.getValue().getValue();
                        }

                        HashMap<String, Budget> budgets = user.getBudgets();
                        for (Map.Entry<String, Budget> budget : budgets.entrySet()){
                            budget_sum += budget.getValue().getValue();
                        }
                        total = expense_sum - income_sum;
                        remainder = budget_sum - total;
                        pieData.add(new SliceValue((float) total, Color.RED).setLabel("Total Amount Spent: $" + total));
                        pieData.add(new SliceValue((float) remainder, Color.GREEN).setLabel("Amount Left: $" + remainder));
                        PieChartData pieChartData = new PieChartData(pieData);
                        pieChartData.setHasLabels(true);
                        pieChartData.setHasCenterCircle(true).setCenterText1("Overall Budget: $" + budget_sum).setCenterText1FontSize(15).setCenterText1Color(Color.parseColor("#0097A7"));
                        pieChartView.setPieChartData(pieChartData);

                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void viewGraphClicked(View view) {
        graph_c = findViewById(R.id.graph_choice);
        show_graph = graph_c.getSelectedItem().toString();
        if(show_graph.equalsIgnoreCase("rent")){
            Intent myIntent = new Intent(this, RentGraphicalActivity.class);
            startActivity(myIntent);
        }
        else if(show_graph.equalsIgnoreCase("entertainment")){
            Intent myIntent = new Intent(this, EntertainmentGraphicalActivity.class);
            startActivity(myIntent);
        }
        else if(show_graph.equalsIgnoreCase("food")){
            Intent myIntent = new Intent(this, FoodGraphicalActivity.class);
            startActivity(myIntent);
        }

    }

    public void goHome(View view) {
        Intent myIntent = new Intent(this, HomeActivity.class);
        startActivity(myIntent);
    }
}
