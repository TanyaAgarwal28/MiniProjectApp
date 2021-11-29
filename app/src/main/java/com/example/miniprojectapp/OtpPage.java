package com.example.miniprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class OtpPage extends AppCompatActivity {
    CountryCodePicker cp;
    EditText phoneNumber;
    Button sendOtp;
    FirebaseAuth f1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);
        phoneNumber=(EditText)findViewById(R.id.editText8);
        sendOtp=(Button)findViewById(R.id.button6);
        cp=(CountryCodePicker)findViewById(R.id.ccp);
        cp.registerCarrierNumberEditText(phoneNumber);
        f1=FirebaseAuth.getInstance();
        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o=new Intent(OtpPage.this,Verification.class);
                o.putExtra("mobile",cp.getFullNumberWithPlus().trim());
                startActivity(o);
            }
        });
    }
}