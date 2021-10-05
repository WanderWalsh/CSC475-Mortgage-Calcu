package com.example.mortgagecal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    //set up
    private double mortgage = 0.0;
    private double rate = 0.0;
    private double payment = 0.0;
    private int years = 15;
    private int months = 0;
    private double total = 0.0;
    private double loan = 0;
    private String buffer1 = "";
    private String buffer2 = "";
    private TextView yearsTextView;
    private TextView mortgageTextView;
    private EditText totalEditText;
    private EditText paymentEditText;
    private EditText rateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Start app

        yearsTextView = (TextView) findViewById(R.id.yearsTextView);
        mortgageTextView = (TextView) findViewById(R.id.mortgageTextView);
        totalEditText = (EditText) findViewById(R.id.totalEditText);
        paymentEditText = (EditText) findViewById(R.id.paymentEditText);
        rateEditText = (EditText) findViewById(R.id.rateEditText);
        mortgageTextView.setText(currencyFormat.format(0));

        EditText totalEditText =
                (EditText) findViewById(R.id.totalEditText);
        totalEditText.addTextChangedListener(totalEditTextWatcher);

        EditText rateEditText =
                (EditText) findViewById(R.id.rateEditText);
        rateEditText.addTextChangedListener(rateEditTextWatcher);

        EditText paymentEditText =
                (EditText) findViewById(R.id.paymentEditText);
        paymentEditText.addTextChangedListener(paymentEditTextWatcher);

        SeekBar yearsSeekBar =
                (SeekBar) findViewById(R.id.yearsSeekBar);
        yearsSeekBar.setOnSeekBarChangeListener(yearsSeekBarListener);
    }

    private void calculate() {

        if (total - payment <= 0 ){
            loan = 0.0;
        }
        else{
            loan = total - payment;
        }
        mortgage = loan *( rate * Math.pow((1 + rate), months));
        mortgage /= (Math.pow((1 + rate), months) - 1);
        mortgageTextView.setText(currencyFormat.format(mortgage));
    }

    private final OnSeekBarChangeListener yearsSeekBarListener =
        new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                years = progress;
                if(years > 21){
                    months = 30 * 12;
                }
                else if (years > 11){
                    months = 20 * 12;
                }
                else{
                    months = 10 * 12;
                }
                calculate();
                yearsTextView.setText(String.valueOf(years));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        };

    private final TextWatcher totalEditTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                if(!s.toString().equals(buffer1)){
                    totalEditText.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[$,.]", "");
                    total = Double.parseDouble( cleanString);
                    String formatted = currencyFormat.format(total/100);
                    total /= 100;
                    buffer1 = formatted;
                    totalEditText.setText(formatted);
                    totalEditText.setSelection(formatted.length());
                    totalEditText.addTextChangedListener(this);
                }
            } catch (NumberFormatException e) {
                total = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };

    private final TextWatcher paymentEditTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                if(!s.toString().equals(buffer2)){
                    paymentEditText.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[$,.]", "");
                    payment = Double.parseDouble( cleanString);
                    String formatted = currencyFormat.format(payment/100);
                    payment /= 100;
                    buffer2 = formatted;
                    paymentEditText.setText(formatted);
                    paymentEditText.setSelection(formatted.length());
                    paymentEditText.addTextChangedListener(this);
                }
            }
            catch (NumberFormatException e) {
                payment = 0.0;
            }
        }
        @Override
        public void afterTextChanged(Editable s) { }
    };

    private final TextWatcher rateEditTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                  rate = Double.parseDouble(s.toString())/ 100.0;
                  rate/= 12;
            } catch (NumberFormatException e) {
                rate = 0.0;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

}