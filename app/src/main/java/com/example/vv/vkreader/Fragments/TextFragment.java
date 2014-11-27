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
import java.util.HashMap;

public class TextFragment extends BaseFragment {
    public Integer position;
    public String ARG_POSITION = "param";
    private HashMap<String, String> map;
    private LoadImageFromNetwork ld;
    private TextView textView;
    private ImageView imageView;
    private GsonClass gs;

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
        View v = inflater.inflate(R.layout.fragment_text, container, false);
        if (getArguments() != null) {
            gs = new GsonClass();
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            imageView = (ImageView) getActivity().findViewById(R.id.imageT);
            textView = (TextView) getActivity().findViewById(R.id.textF);
            map = (HashMap<String, String>) gs.getArr().get(position);
            imageView.setVisibility(View.INVISIBLE);
            ld = new LoadImageFromNetwork(imageView, getActivity());
            ld.execute(map.get("imageContent"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            textView.setText(map.get("textContent") + "\n\n" +
                    sdf.format(Integer.parseInt(map.get("textDate"))));
        }
    }
}
