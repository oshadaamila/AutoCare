package com.example.amila.autocare.Views.Expenses;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.POJOS.CategorySum;
import com.example.amila.autocare.Database.dao.ExpenseDAO;
import com.example.amila.autocare.Database.dao.VehicleDAO;
import com.example.amila.autocare.Database.entities.Expenses;
import com.example.amila.autocare.R;
import com.example.amila.autocare.Views.Fragments.DatePickerFragment;
import com.example.amila.autocare.Views.Start.navigationDrawer;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewExpense extends AppCompatActivity {
    Spinner selectVehicle;
    RadioButton byCategory, byDate;
    RadioGroup radioGroup;
    AppDatabase database;
    VehicleDAO vehicleDAO;
    ExpenseDAO expenseDAO;
    TextView toDate, fromDate;
    RecyclerView rv;
    Button show;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);
        selectVehicle = findViewById(R.id.spinner_vehicle);
        byCategory = findViewById(R.id.radioButton_category);
        byDate = findViewById(R.id.radioButton_date);
        radioGroup = findViewById(R.id.radioGroup);
        toDate = findViewById(R.id.textView_to_date);
        fromDate = findViewById(R.id.textView_from_date);
        show = findViewById(R.id.button_show);
        rv = findViewById(R.id.recycler_view_expenses);
        activity = this;
        //rv.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new navigationDrawer.GridSpacingItemDecoration(1, dpToPx(10), true));
        rv.setItemAnimator(new DefaultItemAnimator());
        database = AppDatabase.getAppDatabase(getApplicationContext());
        vehicleDAO = database.vehicledao();
        expenseDAO = database.expensedao();

        loadSpinnerData();
        setDates();
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PieChart chart = findViewById(R.id.chart);
                RecyclerView rv = findViewById(R.id.recycler_view_expenses);
                rv.setVisibility(View.INVISIBLE);
                chart.setVisibility(View.INVISIBLE);
                DialogFragment newFragment = new DatePickerFragment(toDate.getId());
                newFragment.show(getSupportFragmentManager(), "datePicker");


            }
        });
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PieChart chart = findViewById(R.id.chart);
                RecyclerView rv = findViewById(R.id.recycler_view_expenses);
                rv.setVisibility(View.INVISIBLE);
                chart.setVisibility(View.INVISIBLE);
                DialogFragment newFragment = new DatePickerFragment(fromDate.getId());
                newFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (byCategory.isChecked()) {
                    showByCategories();
                } else if (byDate.isChecked()) {
                    //code to show expenses by date
                    showByExpenses();
                } else {
                    Toast.makeText(getApplicationContext(), "Select Category or Date", Toast.LENGTH_SHORT).show();
                    PieChart chart = findViewById(R.id.chart);
                    RecyclerView rv = findViewById(R.id.recycler_view_expenses);
                    rv.setVisibility(View.INVISIBLE);
                    chart.setVisibility(View.INVISIBLE);
                }
            }
        });
        radioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PieChart chart = findViewById(R.id.chart);
                RecyclerView rv = findViewById(R.id.recycler_view_expenses);
                rv.setVisibility(View.INVISIBLE);
                chart.setVisibility(View.INVISIBLE);
            }
        });



    }

    private void showByExpenses() {
        Date to = null;
        Date from = null;
        try {
            from = new SimpleDateFormat("d-M-y").parse(fromDate.getText().toString());
            to = new SimpleDateFormat("d-M-y").parse(toDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        expenseDAO.getExpensesByDate(from, to).observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(@Nullable List<Expenses> expenses) {
                PieChart chart = findViewById(R.id.chart);
                RecyclerView rv = findViewById(R.id.recycler_view_expenses);
                rv.setVisibility(View.VISIBLE);
                chart.setVisibility(View.INVISIBLE);
                /*rv.setHasFixedSize(true);

                // use a linear layout manager
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rv.setLayoutManager(mLayoutManager);
                rv.addItemDecoration(new navigationDrawer.GridSpacingItemDecoration(2, dpToPx(10), true));
                rv.setItemAnimator(new DefaultItemAnimator());*/
                // specify an adapter (see also next example)
                MyAdapter mAdapter = new MyAdapter(expenses, activity, getApplicationContext());
                rv.setAdapter(mAdapter);
            }
        });
    }

    private void loadSpinnerData() {

        vehicleDAO.getAllRegNO().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.simple_spinner_item, strings);
                spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                selectVehicle.setAdapter(spinnerAdapter);
            }
        });
    }

    private void showByCategories() {
        /*PieChart chart = new PieChart(getApplicationContext());
        RelativeLayout r1 = findViewById(R.id.relativeLayout);
        r1.addView(chart);*/
        expenseDAO.getCategoriesAndSums(selectVehicle.getSelectedItem().toString()).observe(this, new Observer<List<CategorySum>>() {
            @Override
            public void onChanged(@Nullable List<CategorySum> categorySums) {
                PieChart chart = findViewById(R.id.chart);
                RecyclerView rv = findViewById(R.id.recycler_view_expenses);
                rv.setVisibility(View.INVISIBLE);
                chart.setVisibility(View.VISIBLE);
                List<PieEntry> entries = new ArrayList<>();
                for (CategorySum CS : categorySums) {
                    entries.add(new PieEntry(Float.parseFloat(CS.getSum()), CS.getCategory()));
                }
                PieDataSet set = new PieDataSet(entries, "Expense Categories");
                set.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData data = new PieData(set);
                chart.setData(data);
                chart.invalidate();
            }
        });
    }

    private void setDates() {
        Calendar c = Calendar.getInstance();
        Date today = c.getTime();
        DateFormat df = new SimpleDateFormat("d-M-y");
        String date = df.format(today).toString();
        toDate.setText(date);
        fromDate.setText(date);
    }

    public int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

