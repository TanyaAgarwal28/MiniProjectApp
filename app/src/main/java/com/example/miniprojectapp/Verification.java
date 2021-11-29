package com.example.miniprojectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {
    EditText otpText;
    Button signin,resend;
    FirebaseAuth firebaseAuth;
    String phone;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        phone=getIntent().getStringExtra("mobile").toString();
        otpText=(EditText)findViewById(R.id.editText9);
        signin=(Button)findViewById(R.id.button7);
        resend=(Button)findViewById(R.id.button8);
        firebaseAuth=FirebaseAuth.getInstance();
        genotp();
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p=new Intent(Verification.this,OtpPage.class);
                startActivity(p);
                finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otpText.getText().toString().isEmpty()){
                    Toast.makeText(Verification.this, "Kindly Enter the OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(otpText.getText().toString().length()!=6){
                        Toast.makeText(Verification.this, "Kindly enter the valid otp", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(otp,otpText.getText().toString());
                        signInWithPhoneAuthCredential(credential);
                    }
                }
            }
        });


    }
    private void genotp(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        otp=s;
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }


                    @Override
                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                        Toast.makeText(Verification.this, "Verification failed", Toast.LENGTH_SHORT).show();

                    }
                }
        );
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Verification.this, "SignIn successfull", Toast.LENGTH_SHORT).show();
                    Intent y=new Intent(Verification.this,BarCodeScanner.class);
                    startActivity(y);
                    finish();
                }
                else{
                    Toast.makeText(Verification.this, "SignIn unsuccessfull", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }
