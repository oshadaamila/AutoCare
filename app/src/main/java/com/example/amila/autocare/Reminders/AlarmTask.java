package com.example.amila.autocare.Reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by pavilion 15 on 12-Mar-18.
 */

public class AlarmTask implements Runnable {
    // The date selected for the alarm
    private final Calendar date;
    // The android system alarm manager
    private final AlarmManager am;
    // Your context to retrieve the alarm manager from
    private final Context context;

    private final String msg;
    public AlarmTask(Context context, Calendar date, String msg) {
        this.context = context;
        this.am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        this.date = date;
        this.msg = msg;
    }

    @Override
    public void run() {
        // Request to start are service when the alarm date is upon us
        // We don't start an activity as we just want to pop up a notification into the system bar not a full activity
        Random rand = new Random();
        Integer request_code = rand.nextInt(100000);
        Intent intent = new Intent(context, NotifyService.class);
        intent.setAction("uniqueCode");
        intent.putExtra(NotifyService.INTENT_NOTIFY, true);
        intent.putExtra("Message", msg);
        PendingIntent pendingIntent = PendingIntent.getService(context, request_code, intent, 0);

        // Sets an alarm - note this alarm will be lost if the phone is turned off and on again
        am.set(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), pendingIntent);
    }
}

