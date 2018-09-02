package com.example.home.smartcityjbp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        Intent i = new Intent(MainActivity.this, LoginEnterPhoneNoActivity.class);
        startActivity(i);
    }
}
