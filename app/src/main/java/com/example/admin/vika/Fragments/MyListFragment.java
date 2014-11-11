package com.example.admin.vika.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.vika.Adapters.CustomAdapter;
import com.example.admin.vika.R;

public class MyListFragment extends ListFragment {
    private TextView textView;
    private Fragment fragment2;
    private static String[] content;

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

    public static void setContent(String[] cont) {
        content = cont;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setListAdapter(new CustomAdapter(getActivity()));
        return inflater.inflate(R.layout.fragment_my_list_fragment, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);
        someEventListener.someEvent(position);
        fragment2 = (TextFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.details_frag);
        if (fragment2 != null) {
            textView = (TextView) getActivity().findViewById(R.id.textF);
            textView.setText(content[position]);
        }
    }
}