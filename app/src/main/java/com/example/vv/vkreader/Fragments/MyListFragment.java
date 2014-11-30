package com.example.vv.vkreader.Fragments;

import android.app.Activity;
import android.os.AsyncTask;
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
import com.example.vv.vkreader.JavaClasses.ListClass;
import com.example.vv.vkreader.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MyListFragment extends BaseFragment implements OnItemClickListener {
    public onSomeEventListener someEventListener;
    private ListClass list;
    private TextView textView;
    private ImageView imageView;
    private ListView listView;
    private Fragment fragment2;
    private HashMap<String, String> map;
    private LoadImageFromNetwork ld;
    private ParseTask parseTask;
    private SimpleDateFormat sdf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public interface onSomeEventListener {
        public void someEvent(Integer i, ArrayList arr);
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
        parseTask = new ParseTask();
        parseTask.execute();
        try {
            CustomAdapter arrayAdapter = new CustomAdapter(getActivity(), R.layout.row, parseTask.get());
            listView.setAdapter(arrayAdapter);
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        } catch (NullPointerException e) {
        }
        listView.setOnItemClickListener(this);
        return v;
    }

    protected class ParseTask extends AsyncTask<Void, Void, String[]> {
        String resultJson;

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                URL url = new URL(getResources().getString(R.string.url));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(resultJson);
                list = new ListClass(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return list.title;
        }

        @Override
        protected void onPostExecute(String[] strJson) {
            super.onPostExecute(strJson);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        someEventListener.someEvent(position, list.getArr());
        fragment2 = getActivity().getSupportFragmentManager().findFragmentById(R.id.details_frag);
        if (fragment2 != null) {
            map = (HashMap<String, String>) list.getArr().get(position);
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