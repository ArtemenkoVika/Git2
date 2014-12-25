package com.example.admin.vkreader.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.vkreader.R;
import com.example.admin.vkreader.adapters.CustomAdapter;
import com.example.admin.vkreader.adapters.DataDeleteAdapter;
import com.example.admin.vkreader.fragments.MyListFragment;
import com.example.admin.vkreader.service.UpdateService;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MyListFragment.onSomeEventListener,
        View.OnClickListener {
    public static final int ACTION_EDIT = 101;
    public static final String IDE_EXTRA = "param";
    public static final String IDE_BUNDLE_BOOL = "bool";
    private MyListFragment fragment1;
    private Fragment fragment2;
    private Intent intent;
    private FrameLayout frameLayout;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private MenuItem menuSave;
    private MenuItem menuBack;
    private boolean isOnline;
    private boolean back = false;
    private ListView listView;
    private CustomAdapter customAdapter;
    private DataDeleteAdapter deleteAdapter;
    private CustomAdapter favoriteAdapter;
    private TextView textView;
    private ImageView imageView;
    private ArrayList arNull = new ArrayList();
    private ArrayList arrayFavorite;
    private ArrayList arrayDelete;
    private int positionDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isOnline = isOnline();
        if (!isOnline) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.net),
                    Toast.LENGTH_LONG).show();
        }
        customAdapter = new CustomAdapter(this, R.layout.row, arNull);
        fragment1 = new MyListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frm, fragment1).commit();
        savedInstanceState = new Bundle();
        savedInstanceState.putBoolean(IDE_BUNDLE_BOOL, isOnline);
        fragment1.setArguments(savedInstanceState);
        fragment2 = getSupportFragmentManager().findFragmentById(R.id.details_frag);
        frameLayout = (FrameLayout) findViewById(R.id.frm);
        intent = new Intent(MainActivity.this, UpdateService.class);
        pendingIntent = PendingIntent.getService(getApplicationContext(), 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                long time = SystemClock.elapsedRealtime();
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, time + 5000,
                        30000, pendingIntent);
            }
        });
        if (isOnline) {
            if (singleton.count == 0) t.start();
            singleton.count++;
        }
    }

    @Override
    public void someEvent(Integer position) {
        this.position = position;
        if (singleton.isDateBase() == true) menuSave.setEnabled(false);
        else menuSave.setEnabled(true);
        if (fragment2 == null) {
            Intent intent = new Intent();
            intent.putExtra(IDE_EXTRA, position);
            intent.setClass(this, DetailsActivity.class);
            startActivityForResult(intent, ACTION_EDIT);
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null) {
            return false;
        } else return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuBack = menu.findItem(R.id.IDM_BACK);
        menuSave = menu.findItem(R.id.IDM_SAVE).setEnabled(false);
        if (fragment2 == null) {
            menuBack.setVisible(false);
            menuSave.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.IDM_BACK:
                if (back && (frameLayout.getVisibility() == View.VISIBLE)) {
                    menuSave.setEnabled(false);
                    singleton.setDateBase(false);
                    listView = (ListView) findViewById(R.id.my_list);
                    if (isOnline) listView.setAdapter(singleton.getArrayAdapter());
                    else listView.setAdapter(customAdapter);
                    inVisible();
                }
                frameLayout.setVisibility(View.VISIBLE);
                return true;


            case R.id.IDM_SAVE:
                saveArticles(menuSave);
                return true;


            case R.id.IDM_FAVORITE:
                inVisible();
                menuSave.setEnabled(false);
                arrayFavorite = dataBase.showSavedArticles(this);
                if (dataBase.isCursorToFirst()) {
                    singleton.setDateBase(true);
                    back = true;
                    frameLayout.setVisibility(View.VISIBLE);
                    listView = (ListView) findViewById(R.id.my_list);
                    favoriteAdapter = new CustomAdapter(this, R.layout.row, arrayFavorite);
                    favoriteAdapter.setNotifyOnChange(true);
                    listView.setAdapter(favoriteAdapter);
                } else showDialog(getResources().getString(R.string.favorite),
                        getResources().getString(R.string.dialog_nothing));
                return true;


            case R.id.IDM_DELETE:
                dataBase.showSavedArticles(this);
                if (dataBase.isCursorToFirst()) {
                    arrayDelete = dataBase.showSavedArticles(this);
                    deleteAdapter = new DataDeleteAdapter(this, R.layout.row_delete, arrayDelete);
                    deleteAdapter.setNotifyOnChange(true);
                    showDialog(getResources().getString(R.string.delete));
                } else
                    showDialog(getResources().getString(R.string.delete),
                            getResources().getString(R.string.dialog_nothing));
                return true;


            case R.id.IDM_BACK_TO_MAIN:
                inVisible();
                menuSave.setEnabled(false);
                singleton.setDateBase(false);
                back = false;
                frameLayout.setVisibility(View.VISIBLE);
                listView = (ListView) findViewById(R.id.my_list);
                if (isOnline) listView.setAdapter(singleton.getArrayAdapter());
                else listView.setAdapter(customAdapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment2 != null) {
            int visibility = frameLayout.getVisibility();
            boolean menu_vis = menuSave.isEnabled();
            outState.putInt("visibility", visibility);
            outState.putBoolean("menu_vis", menu_vis);
        }
        boolean b1 = isOnline;
        boolean b2 = back;
        outState.putBoolean("b1", b1);
        outState.putBoolean("b2", b2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (fragment2 != null) {
            int visibility = savedInstanceState.getInt("visibility");
            boolean menu_vis = savedInstanceState.getBoolean("menu_vis");
            if (visibility == View.VISIBLE) frameLayout.setVisibility(View.VISIBLE);
            if (visibility == View.GONE) frameLayout.setVisibility(View.GONE);
            if (menu_vis) menuSave.setEnabled(true);
            else menuSave.setEnabled(false);
        }
        isOnline = savedInstanceState.getBoolean("b1");
        back = savedInstanceState.getBoolean("b2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alarmManager.cancel(pendingIntent);
        this.stopService(intent);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }

    public void inVisible() {
        if (fragment2 != null) {
            imageView = (ImageView) findViewById(R.id.image);
            textView = (TextView) findViewById(R.id.text);
            textView.setText("");
            imageView.setVisibility(View.INVISIBLE);
        }
        menuSave.setEnabled(false);
    }

    public void showDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setAdapter(deleteAdapter, this);
        builder.setNegativeButton("Cancel", this);
        builder.setNeutralButton("Delete all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (singleton.isDateBase()) {
                    inVisible();
                    listView.setAdapter(customAdapter);
                }
                singleton.setDateBase(false);
                dataBase.deleteAll(MainActivity.this);
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        positionDelete = (Integer) v.getTag();
        dataBase.deleteArticles(this, positionDelete);
        deleteAdapter.clear();
        deleteAdapter.addAll(dataBase.showSavedArticles(this));
        if (favoriteAdapter != null) {
            favoriteAdapter.clear();
            favoriteAdapter.addAll(dataBase.showSavedArticles(this));
        }
        if (!dataBase.isCursorToFirst()) {
            if (singleton.isDateBase()) {
                inVisible();
                listView.setAdapter(customAdapter);
            }
            singleton.setDateBase(false);
        }
    }
}
