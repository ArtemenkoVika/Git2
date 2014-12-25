package com.example.admin.vkreader.patterns;

import com.example.admin.vkreader.adapters.CustomAdapter;

import java.util.ArrayList;

public class Singleton {
    private static final Singleton instance = new Singleton();
    public int count = 0;
    private CustomAdapter arrayAdapter;
    private ArrayList arrayList;
    private boolean isDateBase = false;
    private ArrayList id;

    private Singleton() {
    }

    public synchronized static final Singleton getInstance() {
        return instance;
    }

    public CustomAdapter getArrayAdapter() {
        return arrayAdapter;
    }

    public void setArrayAdapter(CustomAdapter arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }

    public boolean isDateBase() {
        return isDateBase;
    }

    public void setDateBase(boolean isDateBase) {
        this.isDateBase = isDateBase;
    }

    public ArrayList getId() {
        return id;
    }

    public void setId(ArrayList id) {
        this.id = id;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }
}
