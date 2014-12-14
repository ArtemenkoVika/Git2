package com.example.admin.vkreader.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.vkreader.R;
import com.example.admin.vkreader.adapters.CustomAdapter;
import com.example.admin.vkreader.async_task.ParseTask;
import com.example.admin.vkreader.patterns.Singleton;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MyListFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    public onSomeEventListener someEventListener;
    private TextView textView;
    private ImageView imageView;
    private ListView listView;
    private Fragment fragment2;
    private HashMap<String, String> map;
    private ParseTask parseTask;
    private FrameLayout frameLayout;
    private LinearLayout linearLayout;
    private Singleton singleton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

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
        View viewList = inflater.inflate(R.layout.fragment_my_list, container, false);
        singleton = Singleton.getInstance();
        imageView = (ImageView) getActivity().findViewById(R.id.image);
        textView = (TextView) getActivity().findViewById(R.id.text);
        frameLayout = (FrameLayout) getActivity().findViewById(R.id.frm);
        fragment2 = getActivity().getSupportFragmentManager().findFragmentById(R.id.details_frag);
        if (fragment2 != null) {
            linearLayout = (LinearLayout) getActivity().findViewById(R.id.fragment2);
            linearLayout.setOnClickListener(this);
            textView.setOnClickListener(this);
        }
        listView = (ListView) viewList.findViewById(R.id.my_list);
        parseTask = new ParseTask(getResources().getString(R.string.url));
        parseTask.execute();
        try {
            singleton.setArrayAdapter(new CustomAdapter(getActivity(), R.layout.row, parseTask.get()));
            listView.setAdapter(singleton.getArrayAdapter());
        } catch (InterruptedException e) {
            Toast.makeText(getActivity(), "Please wait", Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
        } catch (NullPointerException e) {
        }
        if (singleton.getArrayList() == null) singleton.setArrayList(parseTask.getArr());
        singleton.getArrayAdapter().setNotifyOnChange(true);
        listView.setOnItemClickListener(this);
        return viewList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        someEventListener.someEvent(position);
        if (fragment2 != null) {
            try {
                frameLayout.setVisibility(View.GONE);
                map = (HashMap<String, String>) singleton.getArrayList().get(position);
                System.out.println(singleton.getArrayList().size());
                click(textView, imageView, map);
            } catch (NullPointerException e) {
            }
        }
    }

    @Override
    public void onClick(View v) {
        frameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            textView.setText(map.get("textContent") + "\n\n" +
                    sdf.format(Integer.parseInt(map.get("textDate"))));
            imageView.setImageBitmap(ld.image);
        } catch (NullPointerException e) {
        }
    }
}