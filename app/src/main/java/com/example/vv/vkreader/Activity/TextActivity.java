package com.example.vv.vkreader.Activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vv.vkreader.Fragments.TextFragment;
import com.example.vv.vkreader.R;

import java.util.ArrayList;

public class TextActivity extends FragmentActivity {
    private Fragment fragment2;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.portfolio);
        savedInstanceState = getIntent().getExtras();
        Integer position = savedInstanceState.getInt(new MainActivity().IDE_EXTRA);
        ArrayList arr = savedInstanceState.getStringArrayList(new MainActivity().IDE_ARR);
        fragment2 = new TextFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frm2, fragment2).commit();
        Bundle args = new Bundle();
        args.putInt(new TextFragment().ARG_POSITION, position);
        args.putStringArrayList(new TextFragment().ARG_ARR, arr);
        fragment2.setArguments(args);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.IDM_BACK:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
