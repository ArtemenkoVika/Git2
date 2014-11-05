package com.example.admin.vika.Activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vika.Fragments.FragmentText;
import com.example.admin.vika.R;

public class TextActivity extends FragmentActivity {
    private TextView textView;
    private TextView text;
    private Fragment fragment2;
    private ActionBar actionBar;
    private static final int IDM_CAT3 = 103;
    private static final int IDM_CAT4 = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        setTitle("Cats");
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.cat_action);
        Bundle extras = getIntent().getExtras();
        Integer position = extras.getInt(MainActivity.IDE_EXTRA);
        fragment2 = new FragmentText();
        getSupportFragmentManager().beginTransaction().add(R.id.frm2, fragment2).commit();
        Bundle args = new Bundle();
        args.putInt(FragmentText.ARG_POSITION, position);
        fragment2.setArguments(args);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String str1 = String.valueOf(textView.getText());
        String str2 = String.valueOf(text.getText());
        outState.putString("textView", str1);
        outState.putString("text", str2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String str1 = savedInstanceState.getString("textView");
        String str2 = savedInstanceState.getString("text");
        textView.setText(str1);
        text.setText(str2);
    }

    @Override
    public void onStart() {
        super.onStart();
        textView = (TextView) findViewById(R.id.textF);
        text = (TextView) findViewById(R.id.text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, IDM_CAT3, 1, "CAT3").setIcon(R.drawable.cat3).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, IDM_CAT4, 2, "CAT4").setIcon(R.drawable.cat4).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }
}
