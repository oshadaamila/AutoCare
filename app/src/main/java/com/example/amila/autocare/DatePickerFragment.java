package com.example.amila.autocare;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by amila on 2/22/18.
 */

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    int id;

    @SuppressLint("ValidFragment")
    public DatePickerFragment(int id) {
        this.id = id;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c =Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month =  c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        return  new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        TextView tv1 = getActivity().findViewById(id);
        int month2 = view.getMonth() + 1;
        tv1.setText(view.getDayOfMonth() + "-" + month2 + "-" + view.getYear());

    }

}
