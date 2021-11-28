package com.example.miniprojectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


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
                Intent y = new Intent(History.this, BarCodeScanner.class);
                startActivity(y);
                finish();
                return true;
            case R.id.History:
                Intent x = new Intent(History.this, History.class);
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
                Intent i1 = new Intent(History.this, MainActivity.class);
                startActivity(i1);
                finish();

                /*AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn) {
                    LoginManager.getInstance().logOut();
                    Intent i = new Intent(History.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}