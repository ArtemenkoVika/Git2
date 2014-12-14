package com.example.admin.vkreader.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vkreader.R;
import com.example.admin.vkreader.patterns.Singleton;

import java.util.HashMap;

public class DetailsFragment extends BaseFragment {
    public final String ARG_POSITION = "param";
    public Integer position;
    private TextView textView;
    private ImageView imageView;
    private HashMap<String, String> map;
    private Singleton singleton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            savedInstanceState = getArguments();
            position = savedInstanceState.getInt(ARG_POSITION);
            singleton = Singleton.getInstance();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.text);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            map = (HashMap<String, String>) singleton.getArrayList().get(position);
            System.out.println(singleton.getArrayList().size());
            click(textView, imageView, map);
        } catch (NullPointerException e) {
        }
    }
}
