package com.example.amila.autocare.Views.Reminders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.entities.Vehicle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by pavilion 15 on 10-May-18.
 */

public class ResetNotifications extends BroadcastReceiver {
    AppDatabase appDatabase;
    List<Vehicle> vehicleList;

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.RECEIVE_BOOT_COMPLETED".equals(intent.getAction())) {
            ////// reset your alrarms here
            Toast.makeText(context, "Testing 123", Toast.LENGTH_LONG).show();
            appDatabase = AppDatabase.getAppDatabase(context);
            final Context mContext = context;

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    vehicleList = (List<Vehicle>) appDatabase.vehicledao().getAll();
                    for (Vehicle x : vehicleList) {
                        String date = x.getInsurance_expiry_date();
                        new AlarmTask(mContext, dateStringConverter(x.getInsurance_expiry_date()), "Insurance Expiry", x.getReg_no_number(), x.getReg_no(), 1).run();

                    }
                }
            });


        }

    }

    private Calendar dateStringConverter(String date) {
        Calendar c = Calendar.getInstance();
        String pattern = "y/M/d";
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat(pattern).parse(date);
        } catch (Exception e) {
            Log.d("parseError", e.getMessage());
        }
        c.setTime(date1);
        return c;
    }
}
