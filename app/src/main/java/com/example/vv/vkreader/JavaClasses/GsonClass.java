package com.example.vv.vkreader.JavaClasses;

public class GsonClass {
    private static String[] textContent;
    private static String[] imageContent;
    private static Integer[] textDate;

    public GsonClass(String[] textContent, Integer[] textDate, String[] imageContent) {
        this.textContent = textContent;
        this.textDate = textDate;
        this.imageContent = imageContent;
    }

    public GsonClass() {
    }

    public String[] getTextContent() {
        return textContent;
    }

    public void setTextContent(String[] textView) {
        this.textContent = textView;
    }

    public Integer[] getTextDate() {
        return textDate;
    }

    public void setTextDate(Integer[] textDate) {
        this.textDate = textDate;
    }

    public String[] getImageContent() {
        return imageContent;
    }

    public void setImageContent(String[] imageView) {
        this.imageContent = imageView;
    }
}
