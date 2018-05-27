package com.tiannacano.carpaymentcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.SeekBar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Button;
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    private GridLayout results;
    private TextView carPriceAmountTextView;
    private TextView downPaymentAmountTextView;
    private TextView twentyFourResult;
    private TextView interestRate;
    private TextView thirtySixResult;
    private TextView fortyEightResult;
    private TextView sixtyResult;
    private TextView headResult;
    private double carLoanAmount = 0.0;
    private double downPaymentAmount = 0.0;
    private double interestRatePercent = 0.10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        interestRate = (TextView) findViewById(R.id.interestRateValueTextView);
        carPriceAmountTextView = (TextView) findViewById(R.id.carPriceTextView);
        downPaymentAmountTextView = (TextView) findViewById(R.id.downPaymentTextView);
        twentyFourResult = (TextView) findViewById(R.id.twentyFourResult);
        thirtySixResult = (TextView) findViewById(R.id.thirtySixResult);
        fortyEightResult = (TextView) findViewById(R.id.fortyEightResult);
        sixtyResult = (TextView) findViewById(R.id.sixtyResult);
        results = (GridLayout) findViewById(R.id.results);
        headResult = (TextView) findViewById(R.id.ResultTextView);

        interestRate.setText(percentFormat.format(interestRatePercent));

        // 5/26/18 TC 3L: set amountEditText's TextWatcher
        EditText carLoanEdit =
                (EditText) findViewById(R.id.carPriceEditText);
        carLoanEdit.addTextChangedListener(carPriceEditTextWatcher);

        // 5/26/18 TC 3L: set amountEditText's TextWatcher
        EditText downPaymentEdit =
                (EditText) findViewById(R.id.downPaymentEditText);
        downPaymentEdit.addTextChangedListener(downPaymentEditTextWatcher);

        // set percentSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar =
                (SeekBar) findViewById(R.id.interestRateSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

        Button button = (Button) findViewById(R.id.monthlyPaymentsButton);
        button.setOnClickListener(calculateButtonListener);
    }

    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    interestRatePercent = progress/100.0;
                    interestRate.setText(percentFormat.format(interestRatePercent));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

    // 5/26/18 TC 3L: listener object for the EditText's text-changed events
    private final TextWatcher carPriceEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get bill amount and display currency formatted value
                carLoanAmount = Double.parseDouble(s.toString()) / 100.0;
                carPriceAmountTextView.setText(currencyFormat.format(carLoanAmount));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                carPriceAmountTextView.setText("");
                carLoanAmount = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };

    // 5/26/18 TC 3L: listener object for the EditText's text-changed events
    private final TextWatcher downPaymentEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get bill amount and display currency formatted value
                downPaymentAmount = Double.parseDouble(s.toString()) / 100.0;
                downPaymentAmountTextView.setText(currencyFormat.format(downPaymentAmount));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                downPaymentAmountTextView.setText("");
                downPaymentAmount = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };

    // called when a guess Button is touched
    private View.OnClickListener calculateButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            interestRate.setText(percentFormat.format(interestRatePercent));

            /*if(!(carPriceAmountTextView.getText().toString()).isEmpty()){
                carLoanAmount = Double.parseDouble(carPriceAmountTextView.getText().toString());
            }

            if(!(downPaymentAmountTextView.getText().toString()).isEmpty()){
                downPaymentAmount = Double.parseDouble(downPaymentAmountTextView.getText().toString());
            }*/

            double twentyFour = (((interestRatePercent / 12) * (carLoanAmount - downPaymentAmount))) / (1 - Math.pow((1 + (interestRatePercent / 12)), -24));
            double thirtySix = (((interestRatePercent / 12) * (carLoanAmount - downPaymentAmount))) / (1 - Math.pow((1 + (interestRatePercent / 12)), -36));
            double fortyEight = (((interestRatePercent / 12) * (carLoanAmount - downPaymentAmount))) / (1 - Math.pow((1 + (interestRatePercent / 12)), -48));
            double sixty = (((interestRatePercent / 12) * (carLoanAmount - downPaymentAmount))) / (1 - Math.pow((1 + (interestRatePercent / 12)), -60));

            DecimalFormat decimalFormat = new DecimalFormat("#0.00");

            //double twentyFour = (mainLoan/24);
            twentyFourResult.setText("$" + toString().valueOf(decimalFormat.format(twentyFour)));
            //double thirtySix =  (mainLoan/36);
            thirtySixResult.setText("$" + toString().valueOf(decimalFormat.format(thirtySix)));
            //double fortyEight = (mainLoan/48);
            fortyEightResult.setText("$" + toString().valueOf(decimalFormat.format(fortyEight)));
            //double sixty = (mainLoan/60);
            sixtyResult.setText("$" + toString().valueOf(decimalFormat.format(sixty)));

            headResult.setVisibility(View.VISIBLE);
            results.setVisibility(View.VISIBLE);

        }
    };
}

