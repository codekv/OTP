package com.example.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendotpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendotp);


         final EditText inputmobile = findViewById(R.id.inputmobile);

       final Button buttongetotp = findViewById(R.id.buttongetotp);

        final ProgressBar progressBar= findViewById(R.id.progressbar);



        buttongetotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputmobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(SendotpActivity.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                buttongetotp.setVisibility(View.VISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+ inputmobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        SendotpActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull  PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                buttongetotp.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull  FirebaseException e) {


                                progressBar.setVisibility(View.GONE);
                                buttongetotp.setVisibility(View.VISIBLE);
                                Toast.makeText(SendotpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull  String verificationId, @NonNull  PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                               progressBar.setVisibility(View.GONE);
                               buttongetotp.setVisibility(View.VISIBLE);

                                Intent intent = new Intent(getApplicationContext(),VerifyOtpActivity.class);
                                intent.putExtra("mobile",inputmobile.getText().toString());
                                intent.putExtra("verificationId",verificationId);
                                startActivity(intent);
                            }
                        }
                );

            }
        });
    }
}