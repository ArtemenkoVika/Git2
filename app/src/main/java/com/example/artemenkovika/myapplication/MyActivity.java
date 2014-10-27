package com.example.artemenkovika.myapplication;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyActivity extends Activity {
    private DrawerLayout myDrawerLayout;
    private ListView myDrawerList;
    private ActionBarDrawerToggle myDrawerToggle;
    private CharSequence myDrawerTitle;
    private CharSequence myTitle;
    private String[] viewsNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        myTitle = getTitle();
        myDrawerTitle = getResources().getString(R.string.menu);
        viewsNames = getResources().getStringArray(R.array.views_array);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        myDrawerList = (ListView) findViewById(R.id.left_drawer);
        myDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.layout, viewsNames));
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        myDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.drawable.ic_launcher, R.string.app_name, R.string.app_name) {
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

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new Fragment3();
                break;
        }
        if (fragment != null) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
        getMenuInflater().inflate(R.menu.my, menu);
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
    public void setTitle(CharSequence title) {
        myTitle = title;
        getActionBar().setTitle(myTitle);
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
