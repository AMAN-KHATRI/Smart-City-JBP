package com.example.home.smartcityjbp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginVerifyPhoneNoActivity extends AppCompatActivity {

    private String mVerificationId;
    private EditText enterOtpET;
    private ProgressBar enterOtpPB;
    private FirebaseAuth mAuth;
    public static SharedPreferences sharedPreferencesForPhoneNumber;
    public static SharedPreferences.Editor sharedPreferencesEditorForPhoneNumber;
    String phoneNo;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();

                    if (code != null) {
                        enterOtpET.setText(code);
                        verifyVerificationCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(LoginVerifyPhoneNoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    mVerificationId = s;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verify_phone_no);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        enterOtpET = findViewById(R.id.enter_otp_et);
        enterOtpPB = findViewById(R.id.waiting_otp_pb);

        enterOtpPB.setVisibility(View.VISIBLE);

        //Getting mobile number from previous activity
        Intent i = getIntent();
        phoneNo = i.getStringExtra("phone_number");
        sendVerificationCode(phoneNo);

        findViewById(R.id.confirm_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = enterOtpET.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    enterOtpET.setError("Enter valid code");
                    enterOtpET.requestFocus();
                    return;
                }
                verifyVerificationCode(code);
            }
        });

        findViewById(R.id.enter_otp_et).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                enterOtpPB.setVisibility(View.GONE);
                return false;
            }
        });
    }

    private void sendVerificationCode(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNo,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private void verifyVerificationCode(String code) {
        LoginEnterPhoneNoActivity.editor = LoginEnterPhoneNoActivity.sharedPreferences.edit();
        LoginEnterPhoneNoActivity.editor.putBoolean("is_logged_in", true);
        LoginEnterPhoneNoActivity.editor.apply();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onSuccessfullLogin();

                            Intent intent = new Intent(LoginVerifyPhoneNoActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void onSuccessfullLogin() {
        //Toast to show login Successfull
        Toast.makeText(LoginVerifyPhoneNoActivity.this, "Logged in", Toast.LENGTH_SHORT).show();

        sharedPreferencesForPhoneNumber = PreferenceManager.getDefaultSharedPreferences(this);

        sharedPreferencesEditorForPhoneNumber = sharedPreferencesForPhoneNumber.edit();
        sharedPreferencesEditorForPhoneNumber.putString("phone_number", phoneNo);
        sharedPreferencesEditorForPhoneNumber.apply();
    }

}
