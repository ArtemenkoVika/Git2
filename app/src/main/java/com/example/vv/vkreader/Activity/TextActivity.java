package com.example.vv.vkreader.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vv.vkreader.Fragments.TextFragment;
import com.example.vv.vkreader.R;

public class TextActivity extends FragmentActivity {
    private Fragment fragment2;
    private ActionBar actionBar;
    private static final int IDM_BACK = 103;
    private static final int IDM_FL4 = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        setTitle("READER");
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.user);
        Bundle extras = getIntent().getExtras();
        Integer position = extras.getInt(MainActivity.IDE_EXTRA);
        fragment2 = new TextFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frm2, fragment2).commit();
        Bundle args = new Bundle();
        args.putInt(TextFragment.ARG_POSITION, position);
        fragment2.setArguments(args);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, IDM_BACK, 1, "BACK").setIcon(R.drawable.arrow).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, IDM_FL4, 2, "FL4").setIcon(R.drawable.flower5).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case IDM_BACK:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
