package com.example.homework2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> names;
    ArrayList<Integer> scores;
    LayoutInflater inflater;


    public CustomAdapter (Context applicationContext, ArrayList<String> names, ArrayList<Integer> scores) {
        this.context = context;
        this.names = names;
        this.scores = scores;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d("LALA","" + i);
        view = inflater.inflate(R.layout.activity_listview, null);
        TextView name = (TextView)view.findViewById(R.id.player_textView);
        TextView score = (TextView) view.findViewById(R.id.score_textView);
        name.setText(names.get(i));
        score.setText("" + scores.get(i));
        return view;
    }
}
