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

import com.cs1699.budjet.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddIncomeExpenseActivity extends AppCompatActivity {

    private final Context mContext = this;
    private EditText inputValue, inputDescription;
    private RadioGroup input_or_expense;
    private RadioButton radio_choice;
    private Button btnSubmit;
    private FirebaseAuth auth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnSubmit = (Button) findViewById(R.id.submit_button);
        input_or_expense = (RadioGroup) findViewById(R.id.radioSex);
        inputValue = (EditText) findViewById(R.id.income_expense_val);
        inputDescription = (EditText) findViewById(R.id.income_expense_description);
        auth = FirebaseAuth.getInstance();

        // This block of code is triggered when the "Register" button is clicked
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioID = input_or_expense.getCheckedRadioButtonId();
                radio_choice = (RadioButton) findViewById(radioID);
                if(radio_choice.getText() == "Income"){
                    Toast.makeText(mContext, "Adding Income", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext, "Adding Expense", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
