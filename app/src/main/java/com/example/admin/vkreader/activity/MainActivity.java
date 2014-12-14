package com.example.admin.vkreader.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.admin.vkreader.R;
import com.example.admin.vkreader.fragments.MyListFragment;
import com.example.admin.vkreader.patterns.Singleton;
import com.example.admin.vkreader.service.UpdateService;

public class MainActivity extends FragmentActivity implements MyListFragment.onSomeEventListener {
    public final int ACTION_EDIT = 101;
    private final int IDM_ARROW = 100;
    public final String IDE_EXTRA = "param";
    private MyListFragment fragment1;
    private Fragment fragment2;
    private Intent intent;
    private FrameLayout frameLayout;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Singleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast toast = Toast.makeText(getApplication(), "Нет соединения с интернетом!",
                Toast.LENGTH_LONG);
        if (!isOnline()) {
            toast.show();
            MainActivity.this.finish();
        }
        singleton = Singleton.getInstance();
        frameLayout = (FrameLayout) findViewById(R.id.frm);
        fragment1 = new MyListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frm, fragment1).commit();
        fragment2 = getSupportFragmentManager().findFragmentById(R.id.details_frag);
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
        if (singleton.count == 0)
            t.start();
        singleton.count++;
    }

    @Override
    public void someEvent(Integer position) {

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
        if (fragment2 != null) {
            menu.add(Menu.NONE, IDM_ARROW, Menu.NONE, "Back").setIcon(R.drawable.arrow).
                    setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case IDM_ARROW:
                frameLayout.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("parcelable", singleton);
        if (fragment2 != null) {
            int visibility = frameLayout.getVisibility();
            outState.putInt("visibility", visibility);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        singleton = savedInstanceState.getParcelable("parcelable");
        if (fragment2 != null) {
            int visibility = savedInstanceState.getInt("visibility");
            if (visibility == View.VISIBLE) frameLayout.setVisibility(View.VISIBLE);
            if (visibility == View.GONE) frameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alarmManager.cancel(pendingIntent);
        this.stopService(intent);
    }
}
