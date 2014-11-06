package com.example.admin.vika.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vika.R;

public class FragmentText extends Fragment {
    public Integer position;
    public static String ARG_POSITION = "param";
    TextView textView;
    TextView text;
    ImageView imageView;

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
        return inflater.inflate(R.layout.fragment_fragment_text, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            text = (TextView) getActivity().findViewById(R.id.text);
            imageView = (ImageView) getActivity().findViewById(R.id.image);
            textView = (TextView) getActivity().findViewById(R.id.textF);
            switch (position) {
                case 0:
                    text.setText("Немецкий рекс");
                    imageView.setImageResource(R.drawable.rex);
                    textView.setText(getResources().getText(R.string.rex));
                    break;
                case 1:
                    text.setText("Гавана");
                    imageView.setImageResource(R.drawable.havana);
                    textView.setText(getResources().getText(R.string.havana));
                    break;
                case 2:
                    text.setText("Австралийская дымчатая кошка");
                    imageView.setImageResource(R.drawable.australian);
                    textView.setText(getResources().getText(R.string.avs));
                    break;
                case 3:
                    text.setText("Американская жесткошерстная кошка");
                    imageView.setImageResource(R.drawable.american);
                    textView.setText(getResources().getText(R.string.american));
                    break;
            }
        }
    }
}
