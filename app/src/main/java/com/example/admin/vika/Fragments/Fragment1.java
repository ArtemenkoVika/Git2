package com.example.admin.vika.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.vika.Adapters.CustomAdapter;
import com.example.admin.vika.R;

public class Fragment1 extends ListFragment {
    private TextView textView;
    private TextView text;
    private ImageView imageView;
    private Fragment fragment2;

    public interface onSomeEventListener {
        public void someEvent(Integer i);
    }

    public onSomeEventListener someEventListener;

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
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        textView = (TextView) getActivity().findViewById(R.id.textF);
        text = (TextView) getActivity().findViewById(R.id.text);
        imageView = (ImageView) getActivity().findViewById(R.id.image);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);
        someEventListener.someEvent(position);
        fragment2 = (FragmentText) getActivity().getSupportFragmentManager().findFragmentById(R.id.details_frag);
        if (fragment2 != null) {
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