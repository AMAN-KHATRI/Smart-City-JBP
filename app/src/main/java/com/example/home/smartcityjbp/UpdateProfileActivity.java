package com.example.home.smartcityjbp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfileActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferencesForPhoneNumber;
    Spinner genderSpinner;
    EditText phoneNumberEt;
    int mGender = 0;    //0 is unknown, 1 is male and 2 is female
    final int IMAGE_REQUEST_CODE = 111;
    CircleImageView profileIv;
    FloatingActionButton saveFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        setupSpinner();

        setupPhoneNumber();

        setupProfileImage();

        saveFab = findViewById(R.id.save_fab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateProfileActivity.this, "Save successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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

    private void setupProfileImage() {
        profileIv = findViewById(R.id.profile_image);

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Bitmap image = null;
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();

            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            profileIv.setImageBitmap(image);
        }
    }
}
