package com.example.admin.vkreader.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.example.admin.vkreader.async_task.LoadImageFromNetwork;
import com.example.admin.vkreader.entity.DataBaseOfFavoriteEntity;
import com.example.admin.vkreader.java_classes.DataBase;
import com.example.admin.vkreader.patterns.Singleton;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseActivity extends FragmentActivity implements DialogInterface.OnClickListener {
    protected Singleton singleton = Singleton.getInstance();
    protected int position;
    protected DataBaseOfFavoriteEntity dataEntity;
    protected DataBase dataBase = new DataBase();

    public void saveArticles(MenuItem menuSave) {
        menuSave.setEnabled(false);
        HashMap<String, String> map = (HashMap<String, String>) singleton.getArrayList().get(position);
        LoadImageFromNetwork ld = new LoadImageFromNetwork(null);
        ld.execute(map.get("imageContent"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String text = map.get("textContent") + "\n\n" +
                sdf.format(Integer.parseInt(map.get("textDate")));
        Pattern pat = Pattern.compile("\n");
        Matcher mat = pat.matcher(map.get("textContent"));
        mat.find();
        int k = mat.start();
        String substring = map.get("textContent").substring(0, k);
        Bitmap photo = null;
        try {
            photo = ld.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        byte[] byteArray = getByteArrayFromBitmap(photo);
        dataEntity = new DataBaseOfFavoriteEntity(substring, text, byteArray);
        dataBase.addArticles(this, dataEntity);
    }

    protected void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("OK", this);
        builder.create().show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        onBackPressed();
    }

    public byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public Bitmap getBitmapFromByteArray(byte[] bitmap) {
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }
}
