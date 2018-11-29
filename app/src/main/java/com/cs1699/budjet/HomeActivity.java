package com.cs1699.budjet;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs1699.budjet.models.Category;
import com.cs1699.budjet.models.Expense;
import com.cs1699.budjet.models.Income;
import com.cs1699.budjet.models.MoneyChange;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;

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
        final TextView showIncomesTextView = (TextView)findViewById(R.id.show_incomes_textview);
        final TextView showExpensesTextView = (TextView)findViewById(R.id.show_expenses_textview);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = database.child("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    users.add(user);
                    if(user.getEmail().equals(currentUserEmail)) {
                        // We now have access to the currently logged in User and can access its properties
                        String nameText = "User: " + user.getName();
                        loggedInUserTextview.setText(nameText);

                        String incomesString = "Incomes:";
                        HashMap<String, Income> incomes = user.getIncomes();  // The user's list properties are store as HashMaps in Firebase
                        for (Map.Entry<String, Income> income : incomes.entrySet()) {  // Iterate through the hashmap of incomes and append each income to a string to be displayed
                            incomesString = incomesString + "\nValue: " + income.getValue().getValue() +  "   Description: " + income.getValue().getDescription();
                        }
                        showIncomesTextView.setText(incomesString);  // Show this string of incomes

                        String expensesString = "\nExpenses:";
                        HashMap<String, Expense> expenses = user.getExpenses();  // Show expenses as well.
                        for (Map.Entry<String, Expense> expense : expenses.entrySet()) {  // This is how we're going to iterate thru the user's hashpmap properties
                            expensesString = expensesString + "\nValue: " + expense.getValue().getValue() + "   Description: " + expense.getValue().getDescription();
                        }
                        showExpensesTextView.setText(expensesString);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Working on the Expandable Layout in the home screen that shows the categories and their lists of incomes/expenses
//        final ExpandableLayout layout = (ExpandableLayout) findViewById(R.id.expandable_layout);
//        layout.setRenderer(new ExpandableLayout.Renderer<Category,MoneyChange>() {
//
//            @Override
//            public void renderParent(View view, String course, boolean isExpanded, int parentPosition) {
//                ((TextView)view.findViewById(R.id.tv_parent_name)).setText(course.getName());
//                view.findViewById(R.id.arrow).setBackgroundResource(isExpanded?R.drawable.ic_arrow_up: R.drawable.ic_arrow_down);
//            }
//
//            @Override
//            public void renderChild(View view, String string, int parentPosition, int childPosition) {
//                ((TextView)view.findViewById(R.id.tv_child_name)).setText(string);
//            }
//        });
//
//        layout.setExpandListener(new ExpandCollapseListener.ExpandListener<String>() {
//            @Override
//            public void onExpanded(int i, final Course courseExpanded, View layout) {
//                final Course c = courseExpanded;
//                TextView parentText = (TextView)layout.findViewById(R.id.tv_parent_name);
//                parentText.setTypeface(null, Typeface.BOLD);
//                Button editClassButton = (Button)layout.findViewById(R.id.editClassButton);
//                Button deleteClassButton = (Button)layout.findViewById(R.id.deleteClassButton);
//                editClassButton.setVisibility(View.VISIBLE);
//                deleteClassButton.setVisibility(View.VISIBLE);
//                editClassButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        createAddClassPopup(true, c.getName(), Integer.toString(c.getCredits()), c.getGrade());
//                    }
//                });
//                deleteClassButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setTitle("Delete this course?");
//                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dba.deleteClass(courseExpanded);
//                                displayToast(courseExpanded.getName() + " deleted");
//                                dialog.dismiss();
//                                refreshFragment();
//                            }
//                        });
//                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                displayToast(courseExpanded.getName() + " not deleted");
//                            }
//                        });
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                    }
//                });
//            }
//        });
//
//        layout.setCollapseListener(new ExpandCollapseListener.CollapseListener<Course>() {
//            @Override
//            public void onCollapsed(int i, Course courseCollapsed, View layout) {
//                TextView parentText = (TextView)layout.findViewById(R.id.tv_parent_name);
//                parentText.setTypeface(null, Typeface.NORMAL);
//                Button editClassButton = (Button)layout.findViewById(R.id.editClassButton);
//                editClassButton.setVisibility(View.INVISIBLE);
//                Button deleteClassButton = (Button)layout.findViewById(R.id.deleteClassButton);
//                deleteClassButton.setVisibility(View.INVISIBLE);
//            }
//        });
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

