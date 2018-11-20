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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddIncomeExpenseActivity extends AppCompatActivity {

    private final Context mContext = this;
    private EditText inputValue, inputDescription;
    private Boolean recurr_bool;
    private RadioGroup income_or_expense, recurring;
    private RadioButton income_expense_choice, recurring_choice;
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
        inputValue = (EditText) findViewById(R.id.income_expense_val);
        inputDescription = (EditText) findViewById(R.id.income_expense_description);
        auth = FirebaseAuth.getInstance();

        // This block of code is triggered when the "Register" button is clicked
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int input_expense_id = income_or_expense.getCheckedRadioButtonId();
                int recurring_id = recurring.getCheckedRadioButtonId();
                income_expense_choice = (RadioButton) findViewById(input_expense_id);
                recurring_choice = (RadioButton) findViewById(recurring_id);
                if( recurring_choice.getText().equals("Yes")) { recurr_bool = true; }
                else { recurr_bool = false; }

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
                if(income_expense_choice.getText().equals("Income")){
                    Income i = new Income(null, recurr_bool, dValue, descrip, new Date());
                }
                else {
                    Expense e = new Expense(null, recurr_bool, dValue, descrip, new Date());
                }
            }
        });
    }
}
