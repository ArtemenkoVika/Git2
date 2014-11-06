package com.example.admin.vika.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.vika.Fragments.Frag2;
import com.example.admin.vika.Fragments.Frag3;
import com.example.admin.vika.Fragments.Fragment1;
import com.example.admin.vika.Fragments.FragmentText;
import com.example.admin.vika.R;

public class MainActivity extends FragmentActivity implements Fragment1.onSomeEventListener {
    private DrawerLayout myDrawerLayout;
    private CharSequence myTitle;
    private ListView myDrawerList;
    private ActionBarDrawerToggle myDrawerToggle;
    private CharSequence myDrawerTitle;
    private String[] viewsNames;
    private TextView textView;
    private TextView text;
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
        setTitle("Cats");
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.cat_action);
        fragment2 = (FragmentText) getSupportFragmentManager().findFragmentById(R.id.details_frag);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment2 != null) {
            String str1 = String.valueOf(textView.getText());
            String str2 = String.valueOf(text.getText());
            outState.putString("textView", str1);
            outState.putString("text", str2);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (fragment2 != null) {
            String str1 = savedInstanceState.getString("textView");
            String str2 = savedInstanceState.getString("text");
            textView.setText(str1);
            text.setText(str2);
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
    public void onStart() {
        super.onStart();
        if (fragment2 != null) {
            textView = (TextView) findViewById(R.id.textF);
            text = (TextView) findViewById(R.id.text);
        }
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Frag2();
                break;
            case 2:
                fragment = new Frag3();
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
