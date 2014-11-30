package com.example.vv.vkreader.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vv.vkreader.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class TextFragment extends BaseFragment {
    public final String ARG_POSITION = "param";
    public final String ARG_ARR = "paramArr";
    public Integer position;
    private HashMap<String, String> map;
    private LoadImageFromNetwork ld;
    private TextView textView;
    private ImageView imageView;
    private SimpleDateFormat sdf;
    private ArrayList arrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            savedInstanceState = getArguments();
            position = savedInstanceState.getInt(ARG_POSITION);
            arrayList = savedInstanceState.getStringArrayList(ARG_ARR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        imageView = (ImageView) getActivity().findViewById(R.id.imageT);
        textView = (TextView) getActivity().findViewById(R.id.textF);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            imageView = (ImageView) getActivity().findViewById(R.id.imageT);
            textView = (TextView) getActivity().findViewById(R.id.textF);
            map = (HashMap<String, String>) arrayList.get(position);
            imageView.setVisibility(View.INVISIBLE);
            ld = new LoadImageFromNetwork(imageView, getActivity());
            ld.execute(map.get("imageContent"));
            sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            textView.setText(map.get("textContent") + "\n\n" +
                    sdf.format(Integer.parseInt(map.get("textDate"))));
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            textView.setText(map.get("textContent") + "\n\n" +
                    sdf.format(Integer.parseInt(map.get("textDate"))));
            imageView.setImageBitmap(imageT);
        } catch (NullPointerException e) {
        }
    }
}
