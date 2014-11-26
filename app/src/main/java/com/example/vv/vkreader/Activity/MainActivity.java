package com.example.vv.vkreader.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vv.vkreader.Fragments.MyListFragment;
import com.example.vv.vkreader.R;

public class MainActivity extends FragmentActivity implements MyListFragment.onSomeEventListener {
    private ActionBar actionBar;
    private Fragment fragment1;
    private Fragment fragment2;
    public static final int ACTION_EDIT = 101;
    public static final String IDE_EXTRA = "param";
    private Intent intent;
    private TextView textView;
    private ImageView imageView;

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
        textView = (TextView) findViewById(R.id.textF);
        imageView = (ImageView) findViewById(R.id.imageT);
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.user);
        fragment1 = new MyListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frm, fragment1).commit();
        fragment2 = getSupportFragmentManager().findFragmentById(R.id.details_frag);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment2 != null) {
            String str1 = String.valueOf(textView.getText());
            outState.putString("textView", str1);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (fragment2 != null) {
            String str1 = savedInstanceState.getString("textView");
            textView.setText(str1);
            imageView = (ImageView) getLastCustomNonConfigurationInstance();
        }
    }


    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return imageView;
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
