package com.example.admin.vkreader.activity;

import android.os.Bundle;

import com.example.admin.vkreader.R;

public class PendingActivityStart extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_activity_start);
        title = getResources().getString(R.string.contentTitle);
        message = "Сервис запущен!";
        showDialog();
    }
}
