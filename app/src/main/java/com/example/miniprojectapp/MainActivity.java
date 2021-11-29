package com.example.miniprojectapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.EventListener;

import javax.sql.StatementEventListener;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;


public class MainActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    Button signup;
    EditText username,password;
    FirebaseAuth firebaseAuth;
    SignInButton signInWithGmail;
    GoogleSignInClient googleSignInClient;
    ImageButton signInWithEmail;
    CallbackManager callbackManager;
    LoginButton loginButton;
    Button signinWithOtp;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    //private TwitterLoginButton mTwitterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signinWithOtp=findViewById(R.id.button3);
        signInWithEmail=(ImageButton)findViewById(R.id.imageButton);
        signup=(Button)findViewById(R.id.button5);
        signInWithGmail=(SignInButton)findViewById(R.id.signin);
        username=(EditText) findViewById(R.id.editText1);
        password=(EditText)findViewById(R.id.editText2);
        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("834778149632-4kdd50ccirah1m6ghk7ejjn5uj4egm83.apps.googleusercontent.com").requestEmail().build();
        googleSignInClient= GoogleSignIn.getClient(MainActivity.this,googleSignInOptions);
        firebaseAuth=FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        //mTwitterBtn = findViewById(R.id.twitter_login_button);
        //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");




        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task<AuthResult> pendingResultTask = firebaseAuth.getPendingAuthResult();
                if (pendingResultTask != null) {
                    // There's something already here! Finish the sign-in for your user.
                    pendingResultTask
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            // User is signed in.
                                            // IdP data available in
                                            // authResult.getAdditionalUserInfo().getProfile().
                                            // The OAuth access token can also be retrieved:
                                            // authResult.getCredential().getAccessToken().
                                            // The OAuth secret can be retrieved by calling:
                                            // authResult.getCredential().getSecret().
                                            Intent i=new Intent(MainActivity.this,BarCodeScanner.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle failure.
                                            Toast.makeText(MainActivity.this, "FailurePendingMethod", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                } else {
                    // There's no pending result so you need to start the sign-in flow.
                    // See below.
                }

                firebaseAuth
                        .startActivityForSignInWithProvider( MainActivity.this, provider.build()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        // User is signed in.
                                        // IdP data available in
                                        // authResult.getAdditionalUserInfo().getProfile().
                                        // The OAuth access token can also be retrieved:
                                        // authResult.getCredential().getAccessToken().
                                        // The OAuth secret can be retrieved by calling:
                                        // authResult.getCredential().getSecret().
                                        Intent i=new Intent(MainActivity.this,BarCodeScanner.class);
                                        startActivity(i);
                                        finish();
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle failure.
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

            }
        });*/

        signinWithOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,OtpPage.class);
                startActivity(i);
                finish();
            }
        });








        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MainActivity.this,BarCodeScanner.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k=new Intent(MainActivity.this,SignUp.class);
                startActivity(k);
                finish();
            }
        });
        signInWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=username.getText().toString();
                String s2=password.getText().toString();
                if(s1.isEmpty()){
                    username.setError("Kindly enter the username");
                    return;
                }
                else{
                    if(s2.isEmpty()){
                        password.setError("Kindly enter the password");
                        return;
                    }
                    /*if(!s2.matches("^\\w+[.]\\w+[@]\\w+[.]\\w+")){
                        password.setError("Kindly enter valid password");
                        return;
                    }*/
                }
                firebaseAuth.createUserWithEmailAndPassword(s1,s2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Successfully Sign in", Toast.LENGTH_SHORT).show();
                            Intent k=new Intent(MainActivity.this,BarCodeScanner.class);
                            startActivity(k);
                            finish();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "No user exist.Kindly sign up first", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
        signInWithGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l=googleSignInClient.getSignInIntent();
                startActivityForResult(l,100);

            }
        });
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            Intent m=new Intent(MainActivity.this,BarCodeScanner.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(m);
            finish();
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount>googleSignInAccountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
            if(googleSignInAccountTask.isSuccessful()){
                Toast.makeText(MainActivity.this, "Successfully sign in", Toast.LENGTH_SHORT).show();
                Intent p= new Intent(MainActivity.this,BarCodeScanner.class);
                startActivity(p);
                finish();
                try{
                    GoogleSignInAccount googleSignInAccount= googleSignInAccountTask.getResult(ApiException.class);
                    if(googleSignInAccount!=null){
                        AuthCredential credential= GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this, "Firebase Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent s= new Intent(MainActivity.this,BarCodeScanner.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(s);
                                finish();
                            }
                        });
                    }
                } catch(ApiException e){
                    Toast.makeText(this, "Sign in unsuccessfull", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }
    }
