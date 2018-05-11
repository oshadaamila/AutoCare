package com.example.amila.autocare.Views.ManageVehicles;

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
import com.example.amila.autocare.Views.Reminders.ScheduleClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pavilion 15 on 29-Mar-18.
 */

@SuppressLint("ValidFragment")
public class UpdateDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    String category, id, reg_number;
    AppDatabase appDatabase;
    ScheduleClient scheduleclient1;

    private ViewVehicle viewvehicle;

    @SuppressLint("ValidFragment")
    public UpdateDateDialog(String id, String category, String reg_number) {
        this.category = category;
        this.id = id;
        this.reg_number = reg_number;
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
                if (category.equals("insurance_date")) {
                    appDatabase.vehicledao().updateInsuranceDate(view.getYear() + "/" + view.getMonth() + "/" + view.getDayOfMonth(), id);
                    scheduleclient1.setAlarmForNotification(dateStringConverter(view.getYear() + "/" + view.getMonth() + "/" + view.getDayOfMonth()), "Insurance Expiry Update", reg_number + "1", id, 1);

                } else if (category.equals("revenue_license")) {
                    appDatabase.vehicledao().updateRevenueLicense(view.getYear() + "/" + view.getMonth() + "/" + view.getDayOfMonth(), id);
                    scheduleclient1.setAlarmForNotification(dateStringConverter(view.getYear() + "/"
                                    + view.getMonth() + "/" + view.getDayOfMonth()), "Revenue License Update",
                            reg_number + "2", id, 2);
                    //new AlarmTask(getActivity(),dateStringConverter(view.getYear() + "/" + view.getMonth() + "/" + view.getDayOfMonth()),"test",reg_number+"2",id,2).run();

                } else if (category.equals("next_service")) {
                    appDatabase.vehicledao().updateNextServiceDate(view.getYear() + "/" + view.getMonth() + "/" + view.getDayOfMonth(), id);
                    scheduleclient1.setAlarmForNotification(dateStringConverter(view.getYear() + "/" + view.getMonth() + "/" + view.getDayOfMonth()), "Next Service Update", reg_number + "3", id, 3);

                }
            }
        });
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
