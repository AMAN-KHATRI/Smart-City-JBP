package com.example.home.smartcityjbp.features;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.smartcityjbp.R;

public class FeedInNeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_in_need);

        getSupportActionBar().setTitle("Feed In Need");
    }
}
