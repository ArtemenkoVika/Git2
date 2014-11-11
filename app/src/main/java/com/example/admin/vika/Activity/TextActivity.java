package com.example.admin.vika.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.admin.vika.Fragments.TextFragment;
import com.example.admin.vika.R;

public class TextActivity extends FragmentActivity {
    private Fragment fragment2;
    private ActionBar actionBar;
    private static final int IDM_CAT3 = 103;
    private static final int IDM_CAT4 = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        setTitle("RSS Reader");
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.cat_action);
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
        menu.add(Menu.NONE, IDM_CAT3, 1, "Back").setIcon(R.drawable.arrow).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        menu.add(Menu.NONE, IDM_CAT4, 2, "CAT4").setIcon(R.drawable.cat4).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case IDM_CAT3:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
