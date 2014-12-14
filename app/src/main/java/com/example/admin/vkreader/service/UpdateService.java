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
import android.widget.Toast;

import com.example.admin.vkreader.R;
import com.example.admin.vkreader.activity.PendingActivityStart;
import com.example.admin.vkreader.activity.ResultNotificationActivity;
import com.example.admin.vkreader.async_task.ParseTask;
import com.example.admin.vkreader.patterns.Singleton;

import java.util.concurrent.ExecutionException;

public class UpdateService extends Service {
    private final int mNotificationId = 001;
    private final int mUpdateId = 002;
    private int count;
    private NotificationManager manager;
    private PendingIntent resultPendingIntent;
    private PendingIntent pendingIntentStart;
    private Singleton singleton;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        singleton = Singleton.getInstance();
        count = 0;
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(this,
                ResultNotificationActivity.class);
        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, 0);

        Intent intent = new Intent(this, PendingActivityStart.class);
        pendingIntentStart = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        showNotification(getResources().getString(R.string.ticker),
                getResources().getString(R.string.contentTitle),
                getResources().getString(R.string.contentText), mNotificationId,
                pendingIntentStart);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (count != 0) {
            ParseTask parseTask = new ParseTask(getResources().getString(R.string.url2));
            parseTask.execute();
            try {
                for (int i = 0; i < parseTask.get().size(); i++) {
                    singleton.getArrayAdapter().add(parseTask.get().get(i));
                    singleton.getArrayList().add(parseTask.getArr().get(i));
                }
            } catch (InterruptedException e) {
                Toast.makeText(this, "Please wait", Toast.LENGTH_LONG).show();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
            }
            manager.cancel(mUpdateId);
            showNotification(getResources().getString(R.string.ticker2),
                    getResources().getString(R.string.contentTitle2),
                    getResources().getString(R.string.contentText2), mUpdateId, resultPendingIntent);
            Toast.makeText(this, "APPLICATION UPDATE!", Toast.LENGTH_LONG).show();
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

    public void showNotification(String ticker, String contentTitle, String contentText, int id,
                                 PendingIntent pendingIntent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.portfolio)
                .setAutoCancel(true)
                .setTicker(ticker)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.portfolio));
        manager.notify(id, mBuilder.build());
    }
}
