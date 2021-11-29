package com.example.miniprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.media.MediaCas;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class BarCodeScanner extends AppCompatActivity {

    Button scanner;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_scanner2);
        scanner = (Button) findViewById(R.id.button6);
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BarCodeScanner.this, BarCode.class);
                startActivity(i);
                finish();
            }
        });

    }





    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.Home:
                Intent y = new Intent(BarCodeScanner.this, BarCodeScanner.class);
                startActivity(y);
                finish();
                return true;
            case R.id.History:
                Intent x = new Intent(BarCodeScanner.this, History1.class);
                startActivity(x);
                finish();
                return true;
            case R.id.Logout:
                /*SessionManager<TwitterSession> sessionManager = TwitterCore.getInstance().getSessionManager();
                if (sessionManager.getActiveSession() != null){
                    sessionManager.clearActiveSession();
                    mAuth.signOut();
                }

                mAuth.signOut();
                updateUI();*/

                FirebaseAuth.getInstance().signOut();
                Intent i1 = new Intent(BarCodeScanner.this, MainActivity.class);
                startActivity(i1);
                finish();

                /*AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn) {
                    LoginManager.getInstance().logOut();
                    Intent i = new Intent(BarCodeScanner.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}