package com.example.homework2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.SensorManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import im.delight.android.location.SimpleLocation;

public class StartScreenView extends AppCompatActivity {

    private MaterialButton button_mode_BTN;
    private MaterialButton sensor_mode_BTN;
    private MaterialButton top_ten_BTN;
    private ShapeableImageView sound_BTN;
    private SwitchMaterial fast_SWITCH;
    private SwitchMaterial slow_SWITCH;
    private boolean isFast = false;
    private boolean isSlow = false;
    private boolean isSound = true;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen_view);
        toast = Toast.makeText(getApplicationContext(), "YOU MUST CHOOSE ONE OF THE OPTIONS - SLOW OR FAST !", Toast.LENGTH_LONG);
        findViews();
        requestLocationPermissions();
        initViews();
    }


    private void initViews() {
        button_mode_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFast && !isSlow){
                    toast.show();
                }
               else if(isFast && isSlow){
                    toast.show();
                }
                else {
                    Log.d("abcd", " " + isSlow + " " + isFast);
                    Intent gameIntent = new Intent(StartScreenView.this,MainActivity.class);
                    gameIntent.putExtra("theChoose",0);
                    gameIntent.putExtra("fast",isFast);
                    gameIntent.putExtra("slow",isSlow);
                    gameIntent.putExtra("theSound",isSound);
                    startActivity(gameIntent);
                }
            }
        });

        sensor_mode_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameIntent = new Intent(StartScreenView.this,MainActivity.class);
                gameIntent.putExtra("theChoose",1);
                gameIntent.putExtra("theSound",isSound);
                startActivity(gameIntent);
            }
        });

        sound_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sound_BTN.getTag() != null && sound_BTN.getTag().toString().equals("sound")){
                    sound_BTN.setTag("mute");
                    sound_BTN.setImageResource(R.drawable.mute);
                    isSound = false;
                }
                else{
                    sound_BTN.setTag("sound");
                    sound_BTN.setImageResource(R.drawable.sound);
                    isSound = true;


                }

            }
        });

        top_ten_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameIntent = new Intent(StartScreenView.this,players_map.class);
                startActivity(gameIntent);

            }
        });

        fast_SWITCH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean fast) {
                isFast = fast;
            }
        });
        slow_SWITCH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean slow) {
                isSlow = slow;
            }
        });

    }


    private void findViews() {
        slow_SWITCH = findViewById(R.id.slow_SWITCH);
        fast_SWITCH = findViewById(R.id.fast_SWITCH);
        button_mode_BTN = findViewById(R.id.button_mode_BTN);
        sensor_mode_BTN = findViewById(R.id.sensor_mode_BTN);
        top_ten_BTN = findViewById(R.id.top_ten_BTN);
        sound_BTN = findViewById(R.id.sound_BTN);

    }

    private void requestLocationPermissions() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
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
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        fast_SWITCH.setChecked(false);
        slow_SWITCH.setChecked(false);
    }



}