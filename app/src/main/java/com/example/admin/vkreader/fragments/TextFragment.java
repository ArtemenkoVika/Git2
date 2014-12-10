package com.example.admin.vkreader.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vkreader.R;
import com.example.admin.vkreader.service.UpdateService;

import java.util.ArrayList;
import java.util.HashMap;

public class TextFragment extends BaseFragment {
    public final Context context = getActivity();
    public final String ARG_POSITION = "param";
    public final String ARG_ARR = "paramArr";
    public Integer position;
    private static ArrayList list;
    private TextView textView;
    private ImageView imageView;
    private HashMap<String, String> map;
    private ArrayList arrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            savedInstanceState = getArguments();
            position = savedInstanceState.getInt(ARG_POSITION);
            arrayList = savedInstanceState.getStringArrayList(ARG_ARR);
            if (list != null) {
                arrayList = list;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.text);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (UpdateService.arrayUpdate != null) {
                for (int i = 0; i < UpdateService.arrayUpdate.size(); i++) {
                    arrayList.add(UpdateService.arrayUpdate.get(i));
                }
                list = arrayList;
            }
            map = (HashMap<String, String>) arrayList.get(position);
            click(textView, imageView, map);
        } catch (NullPointerException e) {
        }
    }
}
