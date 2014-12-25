package com.example.admin.vkreader.async_task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public class LoadImageFromNetwork extends AsyncTask<String, Void, Bitmap> {
    public Bitmap image;
    private ImageView imageBitmap;

    public LoadImageFromNetwork(ImageView imageBitmap) {
        if (imageBitmap!= null) this.imageBitmap = imageBitmap;
    }

    protected Bitmap doInBackground(String... url) {
        try {
            InputStream init = new java.net.URL(url[0]).openStream();
            image = BitmapFactory.decodeStream(init);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap param) {
        if (imageBitmap!= null){
            imageBitmap.setImageBitmap(param);
            imageBitmap.setVisibility(View.VISIBLE);
        }

    }
}