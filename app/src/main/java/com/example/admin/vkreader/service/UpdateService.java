package com.example.admin.vkreader.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.example.admin.vkreader.R;
import com.example.admin.vkreader.activity.MainActivity;
import com.example.admin.vkreader.async_task.ParseTask;
import com.example.admin.vkreader.fragments.MyListFragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class UpdateService extends Service {
    public static ArrayList arrayUpdate;
    private final int mNotificationId = 001;
    private final int mUpdateId = 002;
    private NotificationManager manager;
    private PendingIntent resultPendingIntent;
    private int count;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        count = 0;
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this,
                MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        showNotification(getResources().getString(R.string.ticker),
                getResources().getString(R.string.contentTitle),
                getResources().getString(R.string.contentText), mNotificationId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (count != 0) {
            Toast.makeText(this, "APPLICATION UPDATE!", Toast.LENGTH_LONG).show();
            manager.cancel(mUpdateId);
            showNotification(getResources().getString(R.string.ticker2),
                    getResources().getString(R.string.contentTitle2),
                    getResources().getString(R.string.contentText2), mUpdateId);
            manager.cancel(mUpdateId);
            ParseTask parseTask = new ParseTask(getResources().getString(R.string.url2));
            parseTask.execute();
            try {
                for (int i = 0; i < parseTask.get().size(); i++) {
                    MyListFragment.arrayAdapter.add(parseTask.get().get(i));
                    arrayUpdate = parseTask.getArr();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
            }
        }
        count++;
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        manager.cancel(mNotificationId);
        manager.cancel(mUpdateId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void showNotification(String ticker, String contentTitle, String contentText, int id) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.portfolio)
                .setAutoCancel(true)
                .setTicker(ticker)
                .setContentIntent(resultPendingIntent)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.portfolio));
        manager.notify(id, mBuilder.build());
    }
}
