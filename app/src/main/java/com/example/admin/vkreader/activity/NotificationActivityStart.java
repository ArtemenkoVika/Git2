package com.example.admin.vkreader.activity;

import android.os.Bundle;

import com.example.admin.vkreader.R;

public class NotificationActivityStart extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_activity_start);
        String title = getResources().getString(R.string.contentTitle);
        String message = getResources().getString(R.string.service_started);
        showDialog(title, message);
    }
}
