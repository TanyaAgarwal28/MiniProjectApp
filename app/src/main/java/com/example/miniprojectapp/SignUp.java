package com.example.miniprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText e1,e2,e3,e4;
    ImageButton b1;
    Button b2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        e1=findViewById(R.id.editText3);
        e2=findViewById(R.id.editText4);
        e3=findViewById(R.id.editText5);
        e4=findViewById(R.id.editText6);
        b1=findViewById(R.id.imageButton2);
        b2=findViewById(R.id.button4);
        firebaseDatabase=FirebaseDatabase.getInstance();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference=firebaseDatabase.getReference("UserRecord");
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                String s3=e3.getText().toString();
                String s4=e4.getText().toString();
                if(s4.length()!=10)
                {
                    Toast.makeText(SignUp.this, "Plz enter valid number", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Users users=new Users(s1,s2,s3,s4);
                    databaseReference.child(users.email).setValue(users);
                    Toast.makeText(SignUp.this, "SignUp successfull", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(SignUp.this,BarCodeScanner.class);
                    startActivity(i);
                    finish();

                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUp.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
