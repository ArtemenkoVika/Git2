package com.example.admin.vika.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.vika.R;

public class TextFragment extends Fragment {
    public Integer position;
    public static String[] content;
    public static String ARG_POSITION = "param";
    TextView textView;

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
        return inflater.inflate(R.layout.fragment_text_fragment, container, false);
    }

    public static void setContent(String[] cont) {
        content = cont;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            textView = (TextView) getActivity().findViewById(R.id.textF);
            textView.setText(content[position]);
        }
    }
}
