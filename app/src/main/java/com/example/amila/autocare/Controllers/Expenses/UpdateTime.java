package com.example.amila.autocare.Controllers.Expenses;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by pavilion 15 on 06-May-18.
 */

@SuppressLint("ValidFragment")
public class UpdateTime extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    TextView tv;


    public UpdateTime(TextView tv) {
        this.tv = tv;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);*/

        return new TimePickerDialog(getActivity(), this, 12, 00, false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hour;
        String min = Integer.toString(minute);
        String ampm;
        if (hourOfDay > 12) {
            hour = Integer.toString(hourOfDay - 12);
            ampm = "PM";
        } else {
            hour = Integer.toString(hourOfDay);
            ampm = "AM";
        }
        tv.setText(hour + ":" + min + ":00 " + ampm);
    }
}
