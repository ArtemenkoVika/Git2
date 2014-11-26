package com.example.vv.vkreader.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vv.vkreader.Adapters.CustomAdapter;
import com.example.vv.vkreader.R;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MyListFragment extends BaseFragment implements OnItemClickListener {
    private TextView textView;
    private ImageView imageView;
    private ListView listView;
    private Fragment fragment2;
    public onSomeEventListener someEventListener;
    private HashMap<String, String> map;
    private LoadImageFromNetwork ld;

    public interface onSomeEventListener {
        public void someEvent(Integer i);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement onSomeEventListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_list, container, false);

        imageView = (ImageView) getActivity().findViewById(R.id.imageT);
        textView = (TextView) getActivity().findViewById(R.id.textF);
        listView = (ListView) v.findViewById(R.id.myList);
        ParseTask mt = new ParseTask();
        mt.execute();
        try {
            CustomAdapter arrayAdapter = new CustomAdapter(getActivity(), R.layout.row, mt.get());
            listView.setAdapter(arrayAdapter);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (NullPointerException e) {
        }
        listView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        someEventListener.someEvent(position);
        fragment2 = getActivity().getSupportFragmentManager().findFragmentById(R.id.details_frag);
        if (fragment2 != null) {
            map = (HashMap<String, String>) gs.getArr().get(position);
            imageView.setVisibility(View.INVISIBLE);
            ld = new LoadImageFromNetwork(imageView);
            ld.execute(map.get("imageContent"));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            textView.setText(map.get("textContent") + "\n\n" +
                    sdf.format(Integer.parseInt(map.get("textDate"))));
        }
    }
}