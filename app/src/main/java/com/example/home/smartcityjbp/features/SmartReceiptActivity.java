package com.example.home.smartcityjbp.features;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.smartcityjbp.R;

public class SmartReceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_receipt);

        getSupportActionBar().setTitle("Smart Receipt");
    }
}
