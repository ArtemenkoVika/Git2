package com.example.vv.vkreader.Fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vv.vkreader.Adapters.CustomAdapter;
import com.example.vv.vkreader.JavaClasses.GsonClass;
import com.example.vv.vkreader.R;

import java.io.InputStream;
import java.text.SimpleDateFormat;

public class MyListFragment extends ListFragment {
    private TextView textView;
    private ImageView imageView;
    private Fragment fragment2;
    private static String[] imageContent;
    private static String[] textContent;
    private static Integer[] textDate;
    private LoadImageFromNetwork ld;
    public onSomeEventListener someEventListener;

    public interface onSomeEventListener {
        public void someEvent(Integer i);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setListAdapter(new CustomAdapter(getActivity()));
        imageContent = new GsonClass().getImageContent();
        textContent = new GsonClass().getTextContent();
        textDate = new GsonClass().getTextDate();
        imageView = (ImageView) getActivity().findViewById(R.id.imageT);
        textView = (TextView) getActivity().findViewById(R.id.textF);
        return inflater.inflate(R.layout.fragment_my_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);
        someEventListener.someEvent(position);
        fragment2 = (TextFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.details_frag);
        if (fragment2 != null) {
            imageView.setVisibility(View.INVISIBLE);
            try {
                ld = new LoadImageFromNetwork(imageView);
                ld.execute(imageContent[position]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            textView.setText(textContent[position] + "\n\n" + sdf.format(textDate[position]));
        }
    }

    public static class LoadImageFromNetwork extends AsyncTask<String, Void, Bitmap> {
        ImageView imageBitmap;

        public LoadImageFromNetwork(ImageView imageBitmap) {
            this.imageBitmap = imageBitmap;
        }

        protected Bitmap doInBackground(String... url) {
            Bitmap imageT = null;
            try {
                InputStream init = new java.net.URL(url[0]).openStream();
                imageT = BitmapFactory.decodeStream(init);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return imageT;
        }

        protected void onPostExecute(Bitmap param) {
            imageBitmap.setImageBitmap(param);
            imageBitmap.setVisibility(View.VISIBLE);
        }
    }
}