package com.example.vv.vkreader.JavaClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GsonClass {
    private String textContent;
    private String imageContent;
    private String textDate;
    private HashMap<String, String> map = new HashMap<String, String>();
    private List arr = new ArrayList();

    public GsonClass(String textContent, String textDate, String imageContent) {
        this.textContent = textContent;
        this.textDate = textDate;
        this.imageContent = imageContent;
    }

    public GsonClass(){
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public List getArr() {
        return arr;
    }

    public void setArr(List arr) {
        this.arr = arr;
    }

    public String getTextContent() {
        return textContent;
    }

    public String getImageContent() {
        return imageContent;
    }

    public String getTextDate() {
        return textDate;
    }
}
