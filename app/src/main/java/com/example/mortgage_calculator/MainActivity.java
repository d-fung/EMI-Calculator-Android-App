package com.example.mortgage_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // initialize variables needed for calculation
    int principal;
    double interest;
    int term;

    // initialize references for the inputs
    EditText principalInput;
    EditText interestInput;
    EditText termInput;

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        principalInput = (TextInputEditText) findViewById(R.id.principal);
        interestInput = (TextInputEditText) findViewById(R.id.interest);
        termInput = (TextInputEditText) findViewById(R.id.term);

        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // use a try catch block or else the runtime system will abort and close the application if error occurs
                try{
                    principal = Integer.parseInt(principalInput.getText().toString());
                    interest = Double.parseDouble(interestInput.getText().toString());
                    term = Integer.parseInt(termInput.getText().toString());


                    double emi = calculateMonthlyPayments(principal, interest, term);
                    double totalPayment = emi * term * 12;

                    emi = roundToTwoDecimalPlaces(emi);
                    totalPayment = roundToTwoDecimalPlaces(totalPayment);

                    // displays the results to the displays
                    ((TextView) findViewById(R.id.monthlyDisplay)).setText(String.valueOf(emi));
                    ((TextView) findViewById(R.id.totalDisplay)).setText(String.valueOf(totalPayment));

                    // catch exception in case of an error
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid input. Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Using intents, refer user to the TD website for more information
    public void openWebsite(View view){
        String websiteUrl = "https://apps.td.com/mortgage-payment-calculator/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
        startActivity(intent);
    }

    // function to calculate EMI given principle, interest, and the term length
    public static double calculateMonthlyPayments(int principal, double interest, int term){

        double monthlyInterest = interest / 100 / 12;
        int totalMonths = term * 12;

        double emi = principal * (monthlyInterest * Math.pow(1+monthlyInterest, totalMonths)) / (Math.pow(1 + monthlyInterest, totalMonths) - 1);
        return emi;

    }

    // helper function to round the results to two decimal places
    public static double roundToTwoDecimalPlaces(double number){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(number));
    }


}