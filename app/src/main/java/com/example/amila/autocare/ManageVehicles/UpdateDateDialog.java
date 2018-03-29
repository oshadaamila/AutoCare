package com.example.amila.autocare.ManageVehicles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Reminders.ScheduleClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pavilion 15 on 29-Mar-18.
 */

@SuppressLint("ValidFragment")
public class UpdateDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    String category, id;
    AppDatabase appDatabase;
    ScheduleClient scheduleclient1;

    private ViewVehicle viewvehicle;

    @SuppressLint("ValidFragment")
    public UpdateDateDialog(String id, String category) {
        this.category = category;
        this.id = id;
    }

    public UpdateDateDialog() {
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);

        appDatabase = AppDatabase.getAppDatabase(getActivity());
        scheduleclient1 = new ScheduleClient(getActivity());
        scheduleclient1.doBindService();
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(final DatePicker view, int year, int month, int dayOfMonth) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.vehicledao().updateInsuranceDate(view.getYear() + "/" + view.getMonth() + "/" + view.getDayOfMonth(), id);
            }
        });
        scheduleclient1.setAlarmForNotification(dateStringConverter(view.getYear() + "/" + view.getMonth() + "/" + view.getDayOfMonth()), "Insurance Expiry Update", id + "1");
        viewvehicle.recreate();
        dismiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            viewvehicle = (ViewVehicle) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FeedbackListener");
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
