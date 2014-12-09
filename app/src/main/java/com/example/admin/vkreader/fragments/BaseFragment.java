package com.example.admin.vkreader.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vkreader.async_task.LoadImageFromNetwork;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class BaseFragment extends Fragment {
    protected HashMap<String, String> map;
    protected LoadImageFromNetwork ld;
    protected SimpleDateFormat sdf;

    public void click(TextView textView, ImageView imageView, HashMap<String, String> map){
        this.map = map;
        imageView.setVisibility(View.INVISIBLE);
        ld = new LoadImageFromNetwork(imageView);
        ld.execute(map.get("imageContent"));
        sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        textView.setText(map.get("textContent") + "\n\n" +
                sdf.format(Integer.parseInt(map.get("textDate"))));
    }

    public void rotation(TextView textView, ImageView imageView){
        try {
            textView.setText(map.get("textContent") + "\n\n" +
                    sdf.format(Integer.parseInt(map.get("textDate"))));
            imageView.setImageBitmap(ld.image);
        } catch (NullPointerException e) {
        }
    }
}
