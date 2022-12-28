package com.example.homework2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyDB {

    private ArrayList<Record> records = new ArrayList<>();

    public MyDB() {    }

    public ArrayList<Record> getRecords() {
        Collections.sort(records, (record1, record2) -> record2.getScore() - record1.getScore());

        if(records.size() > 10){
            records.remove(0);
        }


        return records;
    }

    public MyDB setRecords(ArrayList<Record> records){
        this.records = records;
        return this;
    }
}
