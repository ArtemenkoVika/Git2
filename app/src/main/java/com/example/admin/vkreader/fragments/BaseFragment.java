package com.example.admin.vkreader.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vkreader.activity.MainActivity;
import com.example.admin.vkreader.async_task.LoadImageFromNetwork;
import com.example.admin.vkreader.data_base_helper.DataBaseOfFavorite;
import com.example.admin.vkreader.entity.ResultClass;
import com.example.admin.vkreader.patterns.Singleton;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class BaseFragment extends Fragment {
    protected HashMap<String, String> map;
    protected LoadImageFromNetwork ld;
    protected SimpleDateFormat sdf;
    protected ResultClass resultClass = ResultClass.getInstance();
    protected Singleton singleton;
    protected TextView textView;
    protected ImageView imageView;
    protected int position;

    public void click(HashMap<String, String> map){
        this.map = map;
        imageView.setVisibility(View.INVISIBLE);
        ld = new LoadImageFromNetwork(imageView);
        ld.execute(map.get("imageContent"));
        sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        textView.setText(map.get("textContent") + "\n\n" +
                sdf.format(Integer.parseInt(map.get("textDate"))));
    }

    public void clickOfDataBase() {
        SQLiteDatabase db = new DataBaseOfFavorite(getActivity()).getReadableDatabase();
        Cursor cursor = db.query(DataBaseOfFavorite.TABLE_NAME, new String[]{
                        DataBaseOfFavorite.TEXT, DataBaseOfFavorite.PICTURES},
                DataBaseOfFavorite._ID + "=" + singleton.getId().get(position),
                null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String text = cursor.getString(cursor.getColumnIndex
                (DataBaseOfFavorite.TEXT));
        byte[] bytes = cursor.getBlob(cursor.getColumnIndex
                (DataBaseOfFavorite.PICTURES));
        Bitmap bitmap = new MainActivity().getBitmapFromByteArray(bytes);
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.VISIBLE);
        textView.setText(text);
        db.close();
        cursor.close();
    }

    public void loadImages() {
        for (int i = 0; i < resultClass.getUrls().size(); i++) {
            System.out.println(resultClass.getUrls().size());
            LoadImageFromNetwork load = new LoadImageFromNetwork(imageView);
            load.execute(resultClass.getUrls().get(i));
            try {
                resultClass.getBitmaps().add(load.get());
            } catch (InterruptedException e) {
                System.out.println(e + "in BaseFragment");
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
                System.out.println(e + "in BaseFragment");
            }
        }
    }
}
