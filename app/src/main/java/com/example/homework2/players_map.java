package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class players_map extends AppCompatActivity {

    private PlayersFragment playersFragment;
    private MapFragment mapFragment;
    private Button back_BTN;

    CallBack_listMap callBack_listMap = new CallBack_listMap() {
        @Override
        public void setLocationOfPlayer(double lat, double lon, String namePlayer) {
            mapFragment.setLocationOfPlayer(lat,lon,namePlayer);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_map);

        playersFragment = new PlayersFragment();
        mapFragment = new MapFragment();
        playersFragment.setCallBack_list(callBack_listMap);
        back_BTN = findViewById(R.id.back_BTN);



        getSupportFragmentManager().beginTransaction().add(R.id.FRAME_list_player, playersFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.FRAME_map, mapFragment).commit();



        back_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(players_map.this, StartScreenView.class);
                intent.putExtra("theFirst",false);
                startActivity(intent);

            }
        }
        );
    }
}