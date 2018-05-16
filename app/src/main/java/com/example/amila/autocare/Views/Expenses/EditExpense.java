package com.example.amila.autocare.Views.Expenses;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import android.support.v4.app.DialogFragment;

/**
 * Created by pavilion 15 on 03-May-18.
 */

public class EditExpense extends DialogFragment {
    AppDatabase appDatabase;
    private ViewExpense viewExpense;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getAppDatabase(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.expense_layout, container, false);
        final TextView date = v.findViewById(R.id.textView_expense_date);
        final TextView time = v.findViewById(R.id.textView_expense_time);
        final EditText amount = v.findViewById(R.id.editText_amount_dialog);
        final Spinner category = v.findViewById(R.id.spinner_expense_dialog);
        final Button delete = v.findViewById(R.id.button_delete_expense_dialog);
        final Button update = v.findViewById(R.id.button_save_expense_dialog);
        final String category1 = this.getArguments().getString("category");
        appDatabase.expenseCategoryDAO().getAllCategories().observe((LifecycleOwner) getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, strings);
                spinnerAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                category.setAdapter(spinnerAdapter2);
                category.setSelection(spinnerAdapter2.getPosition(category1));
            }
        });
        date.setText(this.getArguments().getString("date"));
        time.setText(this.getArguments().getString("time"));
        amount.setText(this.getArguments().getString("amount"));
        final int expenseID = this.getArguments().getInt("expense_id");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.expensedao().deleteExpense(Integer.toString(expenseID));
                    }

                });
                viewExpense.recreate();
                dismiss();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dte1 = null;
                try {
                    dte1 = new SimpleDateFormat("d/M/y").parse(date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final Date dte = dte1;
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.expensedao().updateExpense(dte, time.getText().toString(), category.getSelectedItem().toString()
                                , amount.getText().toString(), Integer.toString(expenseID));
                    }
                });
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                UpdateDate updateDateDialog = new UpdateDate(date);
                updateDateDialog.show(ft, "datePicker");
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                UpdateTime updateTimeDialog = new UpdateTime(time);
                updateTimeDialog.show(ft, "datePicker");
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        super.onAttach(activity);
        try {
            viewExpense = (ViewExpense) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FeedbackListener");
        }
    }
}
