package com.example.amila.autocare.Views.Expenses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by pavilion 15 on 06-May-18.
 */

@SuppressLint("ValidFragment")
public class UpdateDate extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView tv;


    @SuppressLint("ValidFragment")
    public UpdateDate(TextView tv) {
        this.tv = tv;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(final DatePicker view, int year, int month, int dayOfMonth) {
        //TextView tv1 = getActivity().findViewById(id);
        int month2 = view.getMonth() + 1;
        tv.setText(view.getDayOfMonth() + "/" + month2 + "/" + view.getYear());
        dismiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


}

