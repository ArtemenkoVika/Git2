package com.example.admin.vkreader.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class BaseActivity extends Activity implements DialogInterface.OnClickListener {
    protected String title = null;
    protected String message = null;

    protected void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("OK", this);
        builder.create().show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        onBackPressed();
    }
}
