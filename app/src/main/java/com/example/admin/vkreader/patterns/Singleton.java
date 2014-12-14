package com.example.admin.vkreader.patterns;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin.vkreader.adapters.CustomAdapter;

import java.util.ArrayList;

public class Singleton implements Parcelable {
    private static final Singleton instance = new Singleton();
    public int count = 0;
    private CustomAdapter arrayAdapter;
    private ArrayList arrayList;

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

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeValue(arrayAdapter);
        parcel.writeList(arrayList);
    }
}
