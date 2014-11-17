package com.example.vv.vkreader.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vv.vkreader.Adapters.CustomAdapter;
import com.example.vv.vkreader.Fragments.MyListFragment;
import com.example.vv.vkreader.Fragments.TextFragment;
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
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends FragmentActivity implements MyListFragment.onSomeEventListener {
    private static final int IDM_FL1 = 101;
    private static final int IDM_FL2 = 102;
    private static final int IDM_FL3 = 103;
    private static final int IDM_FL4 = 104;
    private static final int IDM_FL5 = 105;
    private static final int IDM_FL6 = 106;
    private static final int IDM_FL7 = 107;
    private static final int IDM_FL8 = 108;
    private ActionBar actionBar;
    private Fragment fragment1;
    private Fragment fragment2;
    public static final int ACTION_EDIT = 101;
    public static final String IDE_EXTRA = "param";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseTask mt = new ParseTask();
        mt.execute();
        try {
            CustomAdapter.setArray(mt.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        setTitle("READER");
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.user);
        fragment1 = new MyListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frm, fragment1).commit();
        fragment2 = (TextFragment) getSupportFragmentManager().findFragmentById(R.id.details_frag);
    }

    private class ParseTask extends AsyncTask<Void, Void, String[]> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String[] title;
        String[] title2;
        String[] imageContent;
        Integer[] date;

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                URL url = new URL("https://api.vk.com/method/wall.getById?posts=-41915192_105,-41915192_116,-41915192_115,-41915192_109,-41915192_107,-41915192_104,-41915192_101,-41915192_99,-41915192_98,-41915192_94,-41915192_85,-41915192_84,-41915192_66,-41915192_54,-41915192_50,-41915192_46,-41915192_45,-41915192_36,-41915192_26,-41915192_15,-41915192_7&extended=1");
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
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(resultJson);
                jsonObject = jsonObject.getJSONObject("response");
                JSONArray jArray = jsonObject.getJSONArray("wall");
                title = new String[jArray.length()];
                title2 = new String[jArray.length()];
                imageContent = new String[jArray.length()];
                date = new Integer[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_message = jArray.getJSONObject(i);
                    if (json_message != null) {
                        String text = json_message.getString("text");
                        Pattern pat = Pattern.compile("<.+?>");
                        Matcher mat = pat.matcher(text);
                        mat.find();
                        int k = mat.start();
                        String match = mat.replaceAll("\n");
                        title[i] = match;
                        if (k > 33) {
                            String substring = text.substring(0, 33);
                            String str = substring.toUpperCase();
                            title2[i] = str + "...";
                        } else {
                            String substring = text.substring(0, k);
                            String str = substring.toUpperCase();
                            title2[i] = str;
                        }

                        JSONObject im = json_message.getJSONObject("attachment");
                        im = im.getJSONObject("photo");
                        String urls = im.getString("src_big");
                        imageContent[i] = urls;
                        Integer data = Integer.parseInt(json_message.optString("date").toString());
                        date[i] = data;
                    }
                }
                new GsonClass(title, date, imageContent);
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
    public void someEvent(Integer position) {
        if (fragment2 == null) {
            intent = new Intent();
            intent.putExtra(IDE_EXTRA, position);
            intent.setClass(this, TextActivity.class);
            startActivityForResult(intent, ACTION_EDIT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (fragment2 == null) {
            menu.add(Menu.NONE, IDM_FL1, 1, "FL1").setIcon(R.drawable.flower1).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_FL2, 2, "FL2").setIcon(R.drawable.flower2).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(Menu.NONE, IDM_FL1, 1, "FL1").setIcon(R.drawable.flower1).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_FL2, 2, "FL2").setIcon(R.drawable.flower2).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_FL3, 3, "FL3").setIcon(R.drawable.flower4).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_FL4, 4, "FL4").setIcon(R.drawable.flower5).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_FL1, 5, "FL1").setIcon(R.drawable.flower6).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_FL2, 6, "FL2").setIcon(R.drawable.flower7).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_FL3, 7, "FL3").setIcon(R.drawable.flower8).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_FL4, 8, "FL4").setIcon(R.drawable.flower9).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }
}
