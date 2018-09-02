package com.example.home.smartcityjbp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginEnterPhoneNoActivity extends AppCompatActivity {

    private EditText phoneNoET;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_enter_phone_no);

        getSupportActionBar().hide();

        phoneNoET = findViewById(R.id.enter_phone_et);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean val = sharedPreferences.getBoolean("is_logged_in", false);

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
}
