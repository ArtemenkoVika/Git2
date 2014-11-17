package com.example.vv.vkreader.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vv.vkreader.JavaClasses.GsonClass;
import com.example.vv.vkreader.R;

import java.text.SimpleDateFormat;

public class TextFragment  extends android.support.v4.app.Fragment {
    public Integer position;
    public static String ARG_POSITION = "param";
    private static String[] imageContent;
    private static String[] textContent;
    private static Integer[] textDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle param = getArguments();
            position = param.getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            imageContent = new GsonClass().getImageContent();
            textContent = new GsonClass().getTextContent();
            textDate = new GsonClass().getTextDate();
        }
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageT);
            TextView textView = (TextView) getActivity().findViewById(R.id.textF);
            imageView.setVisibility(View.INVISIBLE);
            try {
                new MyListFragment.LoadImageFromNetwork(imageView).execute(imageContent[position]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            textView.setText(textContent[position] + "\n\n" + sdf.format(textDate[position]));
        }
    }
}
