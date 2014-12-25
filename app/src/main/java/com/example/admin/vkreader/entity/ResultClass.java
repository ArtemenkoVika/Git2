package com.example.admin.vkreader.entity;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ResultClass {
    private static ArrayList<String> title;
    private static ArrayList<String> text;
    private static ArrayList<String> urls;
    private static ArrayList<Bitmap> bitmaps;

    private static final ResultClass instance = new ResultClass();

    public static final ResultClass getInstance() {
        return instance;
    }

    private ResultClass() {
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }

    public ArrayList<String> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<String> urls) {
        this.urls = urls;
    }

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }
}
