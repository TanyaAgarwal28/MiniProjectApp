package com.example.miniprojectapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class BarCode extends AppCompatActivity {
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;
    private TextView barcodeText;
    private String barcodeData;
    private TextView retrieveTV;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference zonesRef,history;
    AlertDialog.Builder dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);


        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        surfaceView = findViewById(R.id.surfaceView);
        barcodeText = findViewById(R.id.textView3);
        retrieveTV = findViewById(R.id.textView15);
        dialog = new AlertDialog.Builder(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        initialiseDetectorsAndSources();



    }


    private void initialiseDetectorsAndSources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(BarCode.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(BarCode.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {


                    barcodeText.post(new Runnable() {

                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                barcodeText.removeCallbacks(null);
                                barcodeData = barcodes.valueAt(0).email.address;
                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                zonesRef = firebaseDatabase.getReference("users");
                                history = firebaseDatabase.getReference("history");
                                zonesRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(int i=1;i<=5;i++){
                                            String str="Medicine"+i;
                                            if (Objects.equals(dataSnapshot.child(str).child("barcodeNumber").getValue(String.class), barcodeData)) {
                                                dialog.setMessage("Your medicine is authorized");
                                                history.child("medicine1")
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                Map<String, Object> postValues = new HashMap<String,Object>();
                                                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                                    postValues.put(snapshot.getKey(),snapshot.getValue());
                                                                                                }
                                                                                                postValues.put("barcodeNumber", barcodeData);
                                                                                                postValues.put("manufacturingDate", "2020-1-1");
                                                                                                postValues.put("expiryDate", "2021-1-1");
                                                                                                postValues.put("price", "20");
                                                                                                postValues.put("name", "tanya");
                                                                                                history.child("medicine1").updateChildren(postValues);
                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                                                                                        }
                                                        );
                                                break;
                                            } else {
                                                dialog.setMessage("Your medicine is not authorized");
                                                history.child("medicine1")
                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                Map<String, Object> postValues = new HashMap<String,Object>();
                                                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                                    postValues.put(snapshot.getKey(),snapshot.getValue());
                                                                                                }
                                                                                                postValues.put("barcodeNumber", barcodeData);
                                                                                                postValues.put("manufacturingDate", "2020-1-1");
                                                                                                postValues.put("expiryDate", "2021-1-1");
                                                                                                postValues.put("price", "20");
                                                                                                postValues.put("name", "tanya");
                                                                                                history.child("medicine1").updateChildren(postValues);
                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {}
                                                                                        }
                                                        );
                                            }
                                        }
                                        dialog.setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int which) {
                                                        Intent i = new Intent(BarCode.this, BarCodeScanner.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                });
                                        AlertDialog alertDialog = dialog.create();
                                        alertDialog.show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        retrieveTV.setText(databaseError.toException().toString());
                                    }
                                });

                            } else {

                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeText.setText(barcodeData);
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                                zonesRef = firebaseDatabase.getReference("users");
                                zonesRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(int i=1;i<=5;i++){
                                            String str="Medicine"+i;
                                            if (Objects.equals(dataSnapshot.child(str).child("barcodeNumber").getValue(String.class), barcodeData)) {
                                                dialog.setMessage("Your medicine is authorized");
                                                break;
                                            } else {
                                                dialog.setMessage("Your medicine is not authorized");
                                            }
                                        }
                                        dialog.setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int which) {
                                                        Intent i = new Intent(BarCode.this, BarCodeScanner.class);
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                });
                                        AlertDialog alertDialog = dialog.create();
                                        alertDialog.show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        retrieveTV.setText(databaseError.toException().toString());
                                    }
                                });

                            }
                        }
                    });

                }
            }
        });

    }



    @Override
    protected void onPause() {
        super.onPause();
        getSupportActionBar().hide();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().hide();
        initialiseDetectorsAndSources();
    }

    }