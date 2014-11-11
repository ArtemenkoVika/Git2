package com.example.admin.vika.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.admin.vika.Adapters.CustomAdapter;
import com.example.admin.vika.Fragments.AnimationFragment;
import com.example.admin.vika.Fragments.HideActionBarFragment;
import com.example.admin.vika.Fragments.MyListFragment;
import com.example.admin.vika.Fragments.TextFragment;
import com.example.admin.vika.R;

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
    private DrawerLayout myDrawerLayout;
    private CharSequence myTitle;
    private ListView myDrawerList;
    private ActionBarDrawerToggle myDrawerToggle;
    private CharSequence myDrawerTitle;
    private String[] viewsNames;
    private static final int IDM_CAT1 = 101;
    private static final int IDM_CAT2 = 102;
    private static final int IDM_CAT3 = 103;
    private static final int IDM_CAT4 = 104;
    private ActionBar actionBar;
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
            new CustomAdapter().setArray(mt.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        setTitle("RSS Reader");
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.cat_action);
        fragment2 = (TextFragment) getSupportFragmentManager().findFragmentById(R.id.details_frag);
        myTitle = getTitle();
        myDrawerTitle = getResources().getString(R.string.menu);
        viewsNames = getResources().getStringArray(R.array.views_array);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        myDrawerList = (ListView) findViewById(R.id.left_drawer);
        myDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.layout, viewsNames));
        actionBar.setDisplayHomeAsUpEnabled(true);
        myDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.drawable.ic_launcher, R.string.cats, R.string.cats) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(myTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(myDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        myDrawerLayout.setDrawerListener(myDrawerToggle);
        if (savedInstanceState == null) {
            displayView(0);
        }
        myDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private class ParseTask extends AsyncTask<Void, String, String[]> {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String[] title;
        String[] content;

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                URL url = new URL("https://ajax.googleapis.com/ajax/services/feed/load?v=2.0&q=http://habrahabr.ru/rss/hubs/&num=20");
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
                jsonObject = jsonObject.getJSONObject("responseData");
                jsonObject = jsonObject.getJSONObject("feed");
                JSONArray jArray = jsonObject.getJSONArray("entries");
                title = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_message = jArray.getJSONObject(i);
                    if (json_message != null) {
                        String ls = json_message.getString("title");
                        title[i] = ls;
                    }
                }
                content = new String[jArray.length()];
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_message = jArray.getJSONObject(i);
                    if (json_message != null) {
                        String ls = json_message.getString("content");
                        Pattern pat = Pattern.compile("<.+>");
                        Matcher mat = pat.matcher(ls);
                        ls = mat.replaceAll("");
                        content[i] = ls;
                    }
                }
                publishProgress(content);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return title;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            MyListFragment.setContent(values);
            TextFragment.setContent(values);
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

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MyListFragment();
                break;
            case 1:
                fragment = new AnimationFragment();
                break;
            case 2:
                fragment = new HideActionBarFragment();
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frm, fragment).commit();
            myDrawerList.setItemChecked(position, true);
            myDrawerList.setSelection(position);
            setTitle(viewsNames[position]);
            myDrawerLayout.closeDrawer(myDrawerList);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (fragment2 == null) {
            menu.add(Menu.NONE, IDM_CAT1, 1, "CAT1").setIcon(R.drawable.cat1).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_CAT2, 2, "CAT2").setIcon(R.drawable.cat2).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            menu.add(Menu.NONE, IDM_CAT1, 1, "CAT1").setIcon(R.drawable.cat1).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_CAT2, 2, "CAT2").setIcon(R.drawable.cat2).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_CAT3, 3, "CAT3").setIcon(R.drawable.cat3).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(Menu.NONE, IDM_CAT4, 4, "CAT4").setIcon(R.drawable.cat4).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public void setTitle(CharSequence title) {
        myTitle = title;
        getActionBar().setTitle(myTitle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.isShiftPressed()) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fragment1);
            linearLayout.setBackgroundColor(Color.RED);
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (event.isShiftPressed()) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fragment1);
            linearLayout.setBackgroundColor(Color.blue(3));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = myDrawerLayout.isDrawerOpen(myDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        myDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        myDrawerToggle.onConfigurationChanged(newConfig);
    }
}
