package com.example.admin.vkreader.activity;

import android.os.Bundle;

import com.example.admin.vkreader.R;

public class ResultNotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_notification);
        title = getResources().getString(R.string.contentTitle2);
        message = "Приложение обновилось!";
        showDialog();
    }
}
