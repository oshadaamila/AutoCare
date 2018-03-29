package com.example.amila.autocare.ManageVehicles;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by pavilion 15 on 29-Mar-18.
 */

@SuppressLint("ValidFragment")
public class UpdateDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    String category, id;

    @SuppressLint("ValidFragment")
    public UpdateDateDialog(String id, String category) {
        this.category = category;
        this.id = id;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


    }

}
