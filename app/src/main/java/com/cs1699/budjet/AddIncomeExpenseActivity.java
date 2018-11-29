package com.cs1699.budjet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs1699.budjet.models.Expense;
import com.cs1699.budjet.models.Income;
import com.cs1699.budjet.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddIncomeExpenseActivity extends AppCompatActivity {

    private final Context mContext = this;
    private EditText inputValue, inputDescription;
    private Boolean recurr_bool;
    private RadioGroup income_or_expense, recurring, recur_rate;
    private RadioButton income_expense_choice, recurring_choice, recur_rate_choice;
    private Button btnSubmit;
    private FirebaseAuth auth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income_expense);

        btnSubmit = (Button) findViewById(R.id.submit_button);
        income_or_expense = (RadioGroup) findViewById(R.id.radioSex);
        recurring = (RadioGroup) findViewById(R.id.radioSex2);
        recur_rate = (RadioGroup) findViewById(R.id.radioSex3) ;
        inputValue = (EditText) findViewById(R.id.income_expense_val);
        inputDescription = (EditText) findViewById(R.id.income_expense_description);
        auth = FirebaseAuth.getInstance();
        recurr_bool = true;
        recur_rate_choice = null;

        recurring.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int recurring_id = recurring.getCheckedRadioButtonId();
                recurring_choice = (RadioButton) findViewById(recurring_id);
                RadioButton daily = (RadioButton) findViewById(R.id.radioDaily);
                RadioButton weekly = (RadioButton) findViewById(R.id.radioWeekly);
                RadioButton monthly = (RadioButton) findViewById(R.id.radioMonthly);

                if( recurring_choice.getText().equals("Yes")) {
                    daily.setVisibility(View.VISIBLE);
                    weekly.setVisibility(View.VISIBLE);
                    monthly.setVisibility(View.VISIBLE);
                    recurr_bool = true;
                }
                else {
                    daily.setVisibility(View.INVISIBLE);
                    weekly.setVisibility(View.INVISIBLE);
                    monthly.setVisibility(View.INVISIBLE);
                    recurr_bool = false;
                }
            }
        });

        // This block of code is triggered when the "Register" button is clicked
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = inputValue.getText().toString().trim();
                String descrip = inputDescription.getText().toString().trim();

                if (TextUtils.isEmpty(value)) {
                    Toast.makeText(mContext, "Enter Value!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isDigitsOnly(value)){
                    Toast.makeText(mContext, "Invalid Value Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(descrip)) {
                    Toast.makeText(mContext, "Enter a description!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double dValue = Double.parseDouble(value);
                int recur_rate_id = recur_rate.getCheckedRadioButtonId();
                recur_rate_choice = (RadioButton) findViewById(recur_rate_id);
                int input_expense_id = income_or_expense.getCheckedRadioButtonId();
                income_expense_choice = (RadioButton) findViewById(input_expense_id);
                if(income_expense_choice.getText().equals("Income")){
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    Income i = new Income();
                    if(recur_rate_choice == null) {  // If the recur rate choice is null, it's not recurring
                        i = new Income(null, recurr_bool, -1, dValue, descrip, timeStamp);
                    }
                    else {
                        if(recur_rate_choice.getText().equals("Daily"))
                            i = new Income(null, recurr_bool, 1, dValue, descrip, timeStamp);
                        else if(recur_rate_choice.getText().equals("Weekly"))
                            i = new Income(null, recurr_bool, 2, dValue, descrip, timeStamp);
                        else if(recur_rate_choice.getText().equals("Monthly"))
                            i = new Income(null, recurr_bool, 1, dValue, descrip, timeStamp);
                    }
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference usersRef = database.getReference("users");
                    usersRef.child(HomeActivity.getCurrentUser()).child("incomes").push().setValue(i);
                    Toast.makeText(mContext, "Income added", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(mContext, HomeActivity.class);
                    startActivity(myIntent);
                }
                else {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    Expense e = new Expense();
                    if(recur_rate_choice == null) {  // If the recur rate choice is null, it's not recurring
                        e = new Expense(null, recurr_bool, -1, dValue, descrip, timeStamp);
                    }
                    else {
                        if(recur_rate_choice.getText().equals("Daily"))
                            e = new Expense(null, recurr_bool, 1, dValue, descrip, timeStamp);
                        else if(recur_rate_choice.getText().equals("Weekly"))
                            e = new Expense(null, recurr_bool, 2, dValue, descrip, timeStamp);
                        else if(recur_rate_choice.getText().equals("Monthly"))
                            e = new Expense(null, recurr_bool, 1, dValue, descrip, timeStamp);
                    }
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference usersRef = database.getReference("users");
                    usersRef.child(HomeActivity.getCurrentUser()).child("expenses").push().setValue(e);
                    Toast.makeText(mContext, "Expense added", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(mContext, HomeActivity.class);
                    startActivity(myIntent);
                }
            }
        });
    }
}
