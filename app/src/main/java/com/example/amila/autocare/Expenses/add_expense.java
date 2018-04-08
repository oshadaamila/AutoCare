package com.example.amila.autocare.Expenses;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.dao.ExpenseCategoryDAO;
import com.example.amila.autocare.Database.dao.ExpenseDAO;
import com.example.amila.autocare.Database.dao.VehicleDAO;
import com.example.amila.autocare.Database.entities.Expenses;
import com.example.amila.autocare.DatePickerFragment;
import com.example.amila.autocare.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class add_expense extends AppCompatActivity {

    Spinner vehicle;
    Spinner category;
    TextView date;
    EditText amount;
    Button add;
    AppDatabase appDatabase;
    List<String> nameList;
    VehicleDAO vehicleDAO;
    ExpenseDAO expenseDAO;
    ExpenseCategoryDAO expenseCategoryDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        date = findViewById(R.id.textView_date_selector);
        amount = findViewById(R.id.editText_amount);
        vehicle = findViewById(R.id.spinner_select_vehicle);
        category = findViewById(R.id.spinner_category);
        add = findViewById(R.id.button_add_expense);
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
        vehicleDAO = appDatabase.vehicledao();
        expenseCategoryDAO = appDatabase.expenseCategoryDAO();
        expenseDAO = appDatabase.expensedao();
        loadSpinnerData();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        //String formattedDate = df.format(c);
        date.setText(df.format(c));
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (category.getSelectedItem().toString().equals("Add new category...")) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    AddCategory newFragment = new AddCategory();
                    newFragment.show(ft, "dialog");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(date.getId());
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense(date.getText().toString());
            }
        });

    }

    private void addExpense(String date) {
        Calendar c = Calendar.getInstance();
        String pattern = "d-M-y";
        Date date1 = null;
        String time = null;
        try {
            date1 = new SimpleDateFormat(pattern).parse(date);
            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
            Date date3 = new Date();
            time = dateFormat.format(date3);
        } catch (Exception e) {
            Log.d("parseError", e.getMessage());
        }
        c.setTime(date1);
        final Expenses expense = new Expenses(vehicle.getSelectedItem().toString(), category.getSelectedItem().toString(),
                Float.parseFloat(amount.getText().toString()), date1, time, "test");
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                expenseDAO.insertAll(expense);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Expense Added", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void loadSpinnerData() {

        vehicleDAO.getAllRegNO().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.simple_spinner_item, strings);
                spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                vehicle.setAdapter(spinnerAdapter);
            }
        });

        expenseCategoryDAO.getAllCategories().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(getApplicationContext(), R.layout.simple_spinner_item, strings);
                spinnerAdapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                category.setAdapter(spinnerAdapter2);
            }
        });
    }
}
