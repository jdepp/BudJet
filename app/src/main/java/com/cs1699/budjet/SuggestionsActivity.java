package com.cs1699.budjet;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cs1699.budjet.R;
import com.cs1699.budjet.models.Category;
import com.cs1699.budjet.models.Expense;
import com.cs1699.budjet.models.Income;
import com.cs1699.budjet.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

public class SuggestionsActivity extends AppCompatActivity {
    private final Context mContext = this;
    private static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

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
                // Working on the Expandable Layout in the home screen that shows the categories and their lists of incomes/expenses
                final ExpandableLayout layout = (ExpandableLayout) findViewById(R.id.expandable_layout);
                layout.setRenderer(new ExpandableLayout.Renderer<String,String>() {

                    @Override
                    public void renderParent(View view, String string, boolean isExpanded, int parentPosition) {
                        ((TextView)view.findViewById(R.id.category_textview)).setText(string);
                        view.findViewById(R.id.arrow).setBackgroundResource(isExpanded?R.drawable.ic_arrow_up: R.drawable.ic_arrow_down);
                    }

                    @Override
                    public void renderChild(View view, String string, int parentPosition, int childPosition) {
                        ((TextView)view.findViewById(R.id.income_expense_textview)).setText(string);
                    }
                });

                layout.setExpandListener(new ExpandCollapseListener.ExpandListener<String>() {
                    @Override
                    public void onExpanded(int i, final String string, View layout) {
                        TextView parentText = (TextView)layout.findViewById(R.id.category_textview);
                        parentText.setTypeface(null, Typeface.BOLD);
                    }
                });

                layout.setCollapseListener(new ExpandCollapseListener.CollapseListener<String>() {
                    @Override
                    public void onCollapsed(int i, String string, View layout) {
                        TextView parentText = (TextView)layout.findViewById(R.id.category_textview);
                        parentText.setTypeface(null, Typeface.NORMAL);
                    }
                });

                List<User> users = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    users.add(user);
                    if(user.getEmail().equals(currentUserEmail)) {
                        // We now have access to the currently logged in User and can access its properties
                        Calendar c = Calendar.getInstance();
                        int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                        HashMap<String, Income> incomes = user.getIncomes();  // The user's list properties are store as HashMaps in Firebase
                        HashMap<String, Expense> expenses = user.getExpenses();
                        HashMap<String, Category> categories = user.getCategories();
                        for (Map.Entry<String, Category> category : categories.entrySet()) {
                            double curr_total = 0;
                            double curr_b = category.getValue().getBudget().getValue();

                            Section<String, String> section = new Section<>();
                            section.parent = category.getValue().getName();
                            for(Map.Entry<String, Income> income : incomes.entrySet()) {
                                if(income.getValue().getCategory().getName().equals(category.getValue().getName())) {
                                    if(income.getValue().isRecurring()) {
                                        int i_month = Integer.parseInt(income.getValue().getDate().substring(4, 6))-1;
                                        int i_day = Integer.parseInt(income.getValue().getDate().substring(6, 8));
                                        int i_year = Integer.parseInt(income.getValue().getDate().substring(0, 4));
                                        Calendar cal = new GregorianCalendar(i_year, i_month, i_day);
                                        int i_days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                                        if(income.getValue().getRecurRate() == 1) { curr_total -= (income.getValue().getValue() * (i_days - i_day)); }
                                        else if(income.getValue().getRecurRate() == 2) { curr_total -= (income.getValue().getValue() * Math.floor((i_days-i_day)/7)); }
                                        else if(income.getValue().getRecurRate() == 3) { curr_total -= income.getValue().getValue(); }
                                    }
                                    else { curr_total -= income.getValue().getValue(); }
                                }
                            }
                            for(Map.Entry<String, Expense> expense : expenses.entrySet()) {
                                if(expense.getValue().getCategory().getName().equals(category.getValue().getName())) {
                                    if(expense.getValue().isRecurring()) {
                                        int e_month = Integer.parseInt(expense.getValue().getDate().substring(4, 5))-1;
                                        int e_day = Integer.parseInt(expense.getValue().getDate().substring(6, 7));
                                        int e_year = Integer.parseInt(expense.getValue().getDate().substring(0, 3));
                                        Calendar cal = new GregorianCalendar(e_year, e_month, e_day);
                                        int e_days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                                        if(expense.getValue().getRecurRate() == 1) {
                                            curr_total += expense.getValue().getValue() * (e_days-e_day);
                                        }
                                        else if(expense.getValue().getRecurRate() == 2) { curr_total += expense.getValue().getValue() * Math.floor((e_days-e_day)/7); }    // Weekly
                                        else if(expense.getValue().getRecurRate() == 3) { curr_total += expense.getValue().getValue(); }        // Monthly
                                    }
                                    else { curr_total += expense.getValue().getValue(); }                                                        // One-time
                                    //section.children.add("Expense " + recurring + ": $" + expense.getValue().getValue() + " - " + expense.getValue().getDescription());
                                }
                            }
                            double remainder = curr_b - curr_total;
                            DecimalFormat nf = new DecimalFormat("#.00");
                            section.children.add("You are $" + remainder + " away from your " + category.getValue().getName() + " budget!");

                            section.children.add("There are: " +Integer.toString(daysInMonth-dayOfMonth) + " days remaining this month");
                            section.children.add("You may spend " + nf.format(remainder/(daysInMonth/dayOfMonth)) + " per day for the rest of the month");
                            layout.addSection(section);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
