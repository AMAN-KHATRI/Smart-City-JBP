package com.example.home.smartcityjbp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.home.smartcityjbp.features.FeedInNeedMainActivity;
import com.example.home.smartcityjbp.features.HelpYouMainActivity;
import com.example.home.smartcityjbp.features.SmartReceiptMainActivity;

public class MainActivity extends AppCompatActivity {

    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.smart_receipt_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SmartReceiptMainActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.help_you_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HelpYouMainActivity.class);
                startActivity(i);
            }
        });

        findViewById(R.id.feed_in_need_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FeedInNeedMainActivity.class);
                startActivity(i);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        LoginEnterPhoneNoActivity.editor = LoginEnterPhoneNoActivity.sharedPreferences.edit();
        LoginEnterPhoneNoActivity.editor.putBoolean("is_logged_in", false);
        LoginEnterPhoneNoActivity.editor.apply();

        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(MainActivity.this, LoginEnterPhoneNoActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }
}
