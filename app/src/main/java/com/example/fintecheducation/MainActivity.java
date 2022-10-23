package com.example.fintecheducation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button button;
    EditText stockInput;
    UserStock stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.search_button);
        button.setOnClickListener(new View.OnClickListener(){


            public void onClick(View v){

                try {
                    stock = new UserStock(stockInput.getText().toString());
                } catch (IOException e) {
                    stock = null;
                }

                if(stock != null){
                    goToStockDisplay();
                }
                else{
                    //stock cannot be found

                }

            }


        });

    }

    public void goToStockDisplay(){
        Intent intent = new Intent(this, StockInformationDisplay.class);
        startActivity(intent);
    }

    public UserStock getUserStock(){
        return this.stock;
    }
}