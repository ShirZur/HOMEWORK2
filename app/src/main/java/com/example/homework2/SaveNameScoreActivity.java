package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import im.delight.android.location.SimpleLocation;

public class SaveNameScoreActivity extends AppCompatActivity {

    private TextView end_score1_LBL;
    private Button save_name_BTN;
    private EditText name_editText;
    FusedLocationProviderClient client;
    private double lat = 0.0;
    private double lon = 0.0;
    private SimpleLocation simpleLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_name_score);
       // simpleLocation = new SimpleLocation(this);
        //requestLocationPermissions(simpleLocation);
        end_score1_LBL = findViewById(R.id.end_score1_LBL);
        save_name_BTN = findViewById(R.id.save_name_BTN);
        name_editText = findViewById(R.id.name_editText);
        simpleLocation = new SimpleLocation(this.getApplicationContext());
        findLocation(simpleLocation);



        Intent intent = getIntent();
        int score = intent.getIntExtra("theScore", 0);
        end_score1_LBL.setText("" + score);


        save_name_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_editText.getText().toString();
                if (!name.isEmpty()) {
                    MyDB myDB;
                    String json = MSPv3.getInstance(getApplicationContext()).getStringSP("records", "");
                    Log.d("abcd", json);
                    myDB = new Gson().fromJson(json, MyDB.class);
                    if (myDB == null) {
                        myDB = new MyDB();
                    }

                    Record rec = new Record().setName(name).setScore(score).setLat(lat).setLon(lon);

                    if (myDB.getRecords().size() < 10) {
                        myDB.getRecords().add(rec);
                    } else if (score > myDB.getRecords().get(0).getScore() && myDB.getRecords().size() >= 10) {
                        myDB.getRecords().remove(0);
                        myDB.getRecords().add(rec);
                    }

                    MSPv3.getInstance(getApplicationContext()).putStringSP("records", new Gson().toJson(myDB));

                    Intent intent = new Intent(SaveNameScoreActivity.this, StartScreenView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "YOU MUST PUT YOUR NAME!", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
    }


    private void requestLocationPermissions(SimpleLocation simpleLocation) {
      /*  client = LocationServices.getFusedLocationProviderClient(this);
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
           Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(location -> {
                Log.d("abcd", "the lat " + location.getLatitude());
              if (location != null) {
                 lat = location.getLatitude();
                   lon = location.getLongitude();
             }
            });
      } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }*/

     /*   ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                Log.d("permission", "fine location granted");
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                Log.d("permission", "coarse location granted");
                            } else {
                                Log.d("permission", "location permission denied");
                            }
                        }
                );

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });*/

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
//            findLocation(simpleLocation);
//        }
//        else{
//            findLocation(simpleLocation);
//
//        }

    }
    private void findLocation(SimpleLocation simpleLocation) {
        simpleLocation.beginUpdates();
        this.lat = simpleLocation.getLatitude();
        this.lon = simpleLocation.getLongitude();
        System.out.println("this is lat"+ " "+lat);
    }



  /* @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
           if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                   Task<Location> task = client.getLastLocation();
                    task.addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null){
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                           }
                     }
                  });

                  return;
             }

          }
        }
    }*/


}