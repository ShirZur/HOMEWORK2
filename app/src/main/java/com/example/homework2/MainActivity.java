package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ShapeableImageView IMG_left;
    private ShapeableImageView IMG_right;
    private ShapeableImageView[] game_IMG_hearts;
    private TextView LBL_score;
    private final int NUM_OF_LANES = 5;
    private final int NUM_OF_ROWS = 8;
    private LinearLayout panel_lanes;
    private Game game;
    private Timer timer;
    private MediaPlayer mediaPlayer;
    private final int DELAY = 500;
    private int delay = DELAY;
    private  final int FAST_DELAY = 300;
    private final int SLOW_DELAY = 700;
    private SensorManager sensorManager;
    private Sensor sensor;
    private int choose;
    private Boolean isSound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast toast = Toast.makeText(this, "Boom!", Toast.LENGTH_LONG);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        int score = 0;
        Context context = this;
        game = new Game(context, 3, this,  2, toast, vibrator);
        timer = new Timer();
        mediaPlayer = MediaPlayer.create(context,R.raw.game_over);
        findViews();
        sensorOrButton();
    }

    private SensorEventListener accSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            if (x > 7 && x <= 9) {
                game.moveTheSpaceOfBird(0);
            }
            if (x >= 2 && x <= 4) {
                game.moveTheSpaceOfBird(1);
            }
            if (x >= -1 && x <= 1) {
                game.moveTheSpaceOfBird(2);
            }
            if (x <= -2 && x >= -4) {
                game.moveTheSpaceOfBird(3);
            }
            if (x < -7 && x >= -9) {
                game.moveTheSpaceOfBird(4);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void startGame(int delay) {
        game.setPanel_lanes(panel_lanes);
        game.insertImageView();
        updateTimerUI(delay);

        IMG_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curPos = game.getCurPos();
                if (game.getArrOfBirds().get(NUM_OF_LANES-1).getVisibility() != View.VISIBLE) {
                    game.getArrOfBirds().get(curPos + 1).setVisibility(View.VISIBLE);
                    game.getArrOfBirds().get(curPos).setVisibility(View.INVISIBLE);
                    game.setCurPos(curPos + 1);
                }
            }
        });
        IMG_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int curPos = game.getCurPos();
                if (game.getArrOfBirds().get(0).getVisibility() != View.VISIBLE) {
                    game.getArrOfBirds().get(curPos - 1).setVisibility(View.VISIBLE);
                    game.getArrOfBirds().get(curPos).setVisibility(View.INVISIBLE);
                    game.setCurPos(curPos - 1);
                }
            }
        });
    }

    private void sensorOrButton() {
        Intent intent = getIntent();
        choose = intent.getIntExtra("theChoose",0);
        isSound = intent.getBooleanExtra("theSound",true);
        Boolean isFast = intent.getBooleanExtra("fast",false);
        Boolean isSlow = intent.getBooleanExtra("slow",false);
        if(isFast)
            delay = FAST_DELAY;
        if(isSlow)
            delay = SLOW_DELAY;

        if(choose == 0 ){
            startGame(delay);
        }

        if(choose == 1){
            IMG_right.setVisibility(View.INVISIBLE);
            IMG_left.setVisibility(View.INVISIBLE);
            initSensor();
            startGame(delay);
        }
    }

    private void findViews() {
        IMG_left = findViewById(R.id.IMG_left);
        IMG_right = findViewById(R.id.IMG_right);
        panel_lanes = findViewById(R.id.panel_lanes);
        LBL_score = findViewById(R.id.LBL_score);
        game_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };
    }

    private void updateTimerUI(int delay) {
        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                game.update(isSound);
                                updateUI();
                            }
                        });
                    }
                }
                , delay, delay);
    }

    public void refreshUILifes() {
        if (game.getWrong() != 0 && !(game.getWrong() > game.getLife()))
            game_IMG_hearts[game_IMG_hearts.length - game.getWrong()].setVisibility(View.INVISIBLE);

        if(game.getWrong() == game.getLife()){
            Intent intent = new Intent(MainActivity.this,SaveNameScoreActivity.class);
            intent.putExtra("theScore", game.getScore());
            if(isSound){
                mediaPlayer.start();
            }
            startActivity(intent);
        }
        LBL_score.setText("" + game.getScore());
    }

    public void updateUI() {
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_LANES; j++) {
                if(game.getIsVisible()[i][j]){
                    game.getMatOfEagle()[i][j].setImageResource(R.drawable.eagle2);
                    game.getMatOfEagle()[i][j].setTag("eagle");
                    game.getMatOfEagle()[i][j].setVisibility(View.VISIBLE);
                }

                if(game.getIsVisibleStar()[i][j]){
                    game.getMatOfEagle()[i][j].setImageResource(R.drawable.caterpillar);
                    game.getMatOfEagle()[i][j].setTag("caterpillar");
                    game.getMatOfEagle()[i][j].setVisibility(View.VISIBLE);
                }

                if(!game.getIsVisible()[i][j] && game.getMatOfEagle()[i][j].getTag() != null) {
                    if(game.getMatOfEagle()[i][j].getTag().toString().equals("eagle")){
                        game.getMatOfEagle()[i][j].setVisibility(View.INVISIBLE);
                    }
                }

                if(!game.getIsVisibleStar()[i][j] && game.getMatOfEagle()[i][j].getTag() != null) {
                    if(game.getMatOfEagle()[i][j].getTag().toString().equals("caterpillar")){
                        game.getMatOfEagle()[i][j].setVisibility(View.INVISIBLE);
                    }
                }





            }
        }
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public boolean isSensorExists(int sensorType) {
        return (sensorManager.getDefaultSensor(sensorType) != null);
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
        if(choose == 1)
            sensorManager.unregisterListener(accSensorEventListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateTimerUI(delay);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(choose == 1)
            sensorManager.registerListener(accSensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}