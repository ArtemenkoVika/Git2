package com.example.artemenkovika.myapplication;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.ActionBar;

public class Fragment1 extends Fragment implements OnClickListener {
    public TextView txt;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        actionBar = getActivity().getActionBar();
        txt = (TextView) getActivity().findViewById(R.id.textView);
        btn1 = (Button) getActivity().findViewById(R.id.button1);
        btn2 = (Button) getActivity().findViewById(R.id.button2);
        btn3 = (Button) getActivity().findViewById(R.id.button3);
        btn4 = (Button) getActivity().findViewById(R.id.bHide);
        btn5 = (Button) getActivity().findViewById(R.id.bShow);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                txt.setText("Нажата кнопка \"Кнопка 1\"");
                break;
            case R.id.button2:
                txt.setText("Нажата кнопка \"Кнопка 2\"");
                break;
            case R.id.button3:
                txt.setText("Нажата кнопка \"Кнопка 3\"");
                break;
            case R.id.bHide:
               actionBar.hide();
                break;
            case R.id.bShow:
                actionBar.show();
                break;
        }
    }
}
