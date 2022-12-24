package com.example.homework2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView listView_player_score;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MyDB allRecores ;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<Integer> scores = new ArrayList<>();
    CallBack_listMap callBack;

    public void setCallBack_list(CallBack_listMap callBack){
        this.callBack = callBack;
    }



    public PlayersFragment() {
    }


    public static PlayersFragment newInstance(String param1, String param2) {
        PlayersFragment fragment = new PlayersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        allRecores = new Gson().fromJson(MSPv3.getInstance(getContext()).getStringSP("records",""),MyDB.class);
        if(allRecores != null){
            for (int i = 0; i < allRecores.getRecords().size(); i++) {
                names.add(allRecores.getRecords().get(i).getName());
                scores.add(allRecores.getRecords().get(i).getScore());

            }
        }


        View view = inflater.inflate(R.layout.fragment_players, container, false);
        listView_player_score = view.findViewById(R.id.listView_player_score);
        CustomAdapter customAdapter = new CustomAdapter(getContext().getApplicationContext(), names,scores);
        listView_player_score.setAdapter(customAdapter);

        listView_player_score.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                double lat = allRecores.getRecords().get(i).getLat();
                double lon = allRecores.getRecords().get(i).getLon();
                String name = allRecores.getRecords().get(i).getName();
                callBack.setLocationOfPlayer(lat,lon,name);
            }
        });


        return view;
    }
}