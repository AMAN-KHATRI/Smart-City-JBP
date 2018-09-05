package com.example.home.smartcityjbp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class UpdateProfileActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferencesForPhoneNumber;
    Spinner genderSpinner;
    EditText phoneNumberEt;
    int mGender = 0;    //0 is unknown, 1 is male and 2 is female

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        setupSpinner();

        setupPhoneNumber();
    }

    private void setupSpinner() {
        genderSpinner = findViewById(R.id.gender_spinner);

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options,
                android.R.layout.simple_dropdown_item_1line);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genderSpinner.setAdapter(genderSpinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);

                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Male"))
                        mGender = 1;
                    else if (selection.equals("Female"))
                        mGender = 2;
                    else
                        mGender = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mGender = 0;
            }
        });
    }

    void setupPhoneNumber() {
        phoneNumberEt = findViewById(R.id.phone_number_et);

        sharedPreferencesForPhoneNumber = PreferenceManager.getDefaultSharedPreferences(this);

        phoneNumberEt.setText(sharedPreferencesForPhoneNumber.getString("phone_number", "0000"));
    }
}
