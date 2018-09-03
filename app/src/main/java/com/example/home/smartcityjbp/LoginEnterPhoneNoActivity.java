package com.example.home.smartcityjbp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginEnterPhoneNoActivity extends AppCompatActivity {

    private EditText phoneNoET;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    private boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_enter_phone_no);

        getSupportActionBar().hide();

        phoneNoET = findViewById(R.id.enter_phone_et);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (!sharedPreferences.getBoolean("is_logged_in", false)) {
            findViewById(R.id.confirm_phone).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String phoneNo = phoneNoET.getText().toString().trim();

                    if (phoneNo.isEmpty() || phoneNo.length() < 10) {
                        phoneNoET.setError("Enter a valid mobile");
                        phoneNoET.requestFocus();
                        return;
                    }

                    Intent intent = new Intent(LoginEnterPhoneNoActivity.this, LoginVerifyPhoneNoActivity.class);
                    intent.putExtra("phone_number", phoneNo);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(LoginEnterPhoneNoActivity.this, MainActivity.class);
            startActivity(intent);
        }
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
