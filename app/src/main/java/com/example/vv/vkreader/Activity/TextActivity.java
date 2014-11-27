package com.example.vv.vkreader.Activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vv.vkreader.Fragments.TextFragment;
import com.example.vv.vkreader.R;

public class TextActivity extends FragmentActivity {
    private Fragment fragment2;
    private ActionBar actionBar;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        setTitle("Reader");
        textView = (TextView) findViewById(R.id.textF);
        imageView = (ImageView) findViewById(R.id.imageT);
        actionBar = getActionBar();
        actionBar.setIcon(R.drawable.user);
        Bundle extras = getIntent().getExtras();
        Integer position = extras.getInt(new MainActivity().IDE_EXTRA);
        fragment2 = new TextFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frm2, fragment2).commit();
        Bundle args = new Bundle();
        args.putInt(new TextFragment().ARG_POSITION, position);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String str1 = String.valueOf(textView.getText());
        outState.putString("textView", str1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String str1 = savedInstanceState.getString("textView");
        textView.setText(str1);
        imageView = (ImageView) getLastCustomNonConfigurationInstance();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return imageView;
    }
}
