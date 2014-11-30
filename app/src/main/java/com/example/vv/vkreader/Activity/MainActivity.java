package com.example.vv.vkreader.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.example.vv.vkreader.Fragments.MyListFragment;
import com.example.vv.vkreader.R;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements MyListFragment.onSomeEventListener {
    public final int ACTION_EDIT = 101;
    public final String IDE_EXTRA = "param";
    public final String IDE_ARR = "arr";
    private ActionBar actionBar;
    private Fragment fragment1;
    private Fragment fragment2;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplication();
        Toast toast = Toast.makeText(context, "Нет соединения с интернетом!", Toast.LENGTH_LONG);
        if (!isOnline()) {
            toast.show();
            MainActivity.this.finish();
        }
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.portfolio);
        fragment1 = new MyListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frm, fragment1).commit();
        fragment2 = getSupportFragmentManager().findFragmentById(R.id.details_frag);
    }

    @Override
    public void someEvent(Integer position, ArrayList arr) {
        if (fragment2 == null) {
            intent = new Intent();
            intent.putExtra(IDE_EXTRA, position);
            intent.putExtra(IDE_ARR, arr);
            intent.setClass(this, TextActivity.class);
            startActivityForResult(intent, ACTION_EDIT);
        }
    }

    public boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        }
        return cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
