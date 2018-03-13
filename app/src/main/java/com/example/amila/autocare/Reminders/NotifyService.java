package com.example.amila.autocare.Reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.amila.autocare.R;

/**
 * Created by pavilion 15 on 12-Mar-18.
 */

public class NotifyService extends Service {

    // Name of an intent extra we can use to identify if this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.blundell.tut.service.INTENT_NOTIFY";
    // Unique id to identify the notification.
    private static final int NOTIFICATION = 123;
    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();
    // The system notification manager
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // If this service was started by out AlarmTask intent then we want to show our notification
        if (intent.getBooleanExtra(INTENT_NOTIFY, false)) {
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString("Message");
            showNotification(msg);
        }
        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification(String msg) {
        // This is the 'title' of the notification
        CharSequence title = "Alarm!!";
        // This is the icon to use on the notification
        int icon = R.drawable.ic_launcher_background;
        // This is the scrolling text of the notification
        CharSequence text = "Your notification time is upon us.";
        // What time to show on the notification
        long time = System.currentTimeMillis();
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, ReminderActivity.class), 0);

        //Notification notification = new Notification(icon, text, time);
        Notification notification = new Notification.Builder(this).setContentTitle("Notification From AutoCare")
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_launcher_background).setContentIntent(contentIntent)
                .build();
        // The PendingIntent to launch our activity if the user selects this notification

        // Set the info for the views that show in the notification panel.
        //notification.setLatestEventInfo(this, title, text, contentIntent);

        // Clear the notification when it is pressed
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Send the notification to the system.
        mNM.notify(NOTIFICATION, notification);

        // Stop the service when we are finished
        stopSelf();
    }

    /**
     * Class for clients to access
     */
    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }
}
