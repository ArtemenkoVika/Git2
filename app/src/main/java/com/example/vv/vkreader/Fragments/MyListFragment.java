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
import com.example.vv.vkreader.JavaClasses.GsonClass;
import com.example.vv.vkreader.R;

import org.json.JSONArray;
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
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyListFragment extends BaseFragment implements OnItemClickListener {
    private TextView textView;
    private ImageView imageView;
    private ListView listView;
    private Fragment fragment2;
    public onSomeEventListener someEventListener;
    private HashMap<String, String> map;
    private LoadImageFromNetwork ld;
    private ParseTask mt;
    private GsonClass gs;

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
        mt = new ParseTask();
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

    protected class ParseTask extends AsyncTask<Void, Void, String[]> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String[] title2;
        List a = new ArrayList();

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                URL url = new URL(getResources().getString(R.string.url));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
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
                jsonObject = jsonObject.getJSONObject("response");
                JSONArray jArray = jsonObject.getJSONArray("wall");
                title2 = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_message = jArray.getJSONObject(i);
                    if (json_message != null) {
                        String text = json_message.getString("text");
                        Pattern pat = Pattern.compile("<.+?>");
                        Matcher mat = pat.matcher(text);
                        mat.find();
                        int k = mat.start();
                        String match = mat.replaceAll("\n");
                        String substring = match.substring(0, k);
                        String toUp = substring.toUpperCase();
                        title2[i] = toUp;
                        JSONObject im = json_message.getJSONObject("attachment");
                        im = im.getJSONObject("photo");
                        String urls = im.getString("src_big");
                        String data = json_message.optString("date").toString();
                        gs = new GsonClass(match, data, urls);
                        gs.getMap().put("textContent", gs.getTextContent());
                        gs.getMap().put("textDate", gs.getTextDate());
                        gs.getMap().put("imageContent", gs.getImageContent());
                        a.add(gs.getMap());
                    }
                }
                gs.setArr(a);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return title2;
        }

        @Override
        protected void onPostExecute(String[] strJson) {
            super.onPostExecute(strJson);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        someEventListener.someEvent(position);
        fragment2 = getActivity().getSupportFragmentManager().findFragmentById(R.id.details_frag);
        if (fragment2 != null) {
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