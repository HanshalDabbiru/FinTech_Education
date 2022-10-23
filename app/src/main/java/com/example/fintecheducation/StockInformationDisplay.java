package com.example.fintecheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


public class StockInformationDisplay extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView dividend;
    TextView MA200;
    TextView MA150;
    TextView MA50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_information_display);
        name = (TextView)findViewById(R.id.StockName);
        price = (TextView)findViewById(R.id.StockPrice);
        dividend = (TextView)findViewById(R.id.StockDividend);
        MA200 = (TextView)findViewById(R.id.Stock200Moving);
        MA150 = (TextView)findViewById(R.id.Stock150Moving);
        MA50 = (TextView)findViewById(R.id.Stock50Moving);

        name.setText()

    }
}