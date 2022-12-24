package com.example.homework2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyDB {

    private ArrayList<Record> records = new ArrayList<>();

    public MyDB() {    }

    public ArrayList<Record> getRecords() {
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record record1, Record record2) {
                return record1.getScore() - record2.getScore();
            }
        });

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
