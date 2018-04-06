package com.example.amila.autocare.Expenses;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.POJOS.CategorySum;
import com.example.amila.autocare.Database.dao.ExpenseDAO;
import com.example.amila.autocare.Database.dao.VehicleDAO;
import com.example.amila.autocare.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class ViewExpense extends AppCompatActivity {
    Spinner selectVehicle;
    RadioButton byCategory, byDate;
    RadioGroup radioGroup;
    AppDatabase database;
    VehicleDAO vehicleDAO;
    ExpenseDAO expenseDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);
        selectVehicle = findViewById(R.id.spinner_vehicle);
        byCategory = findViewById(R.id.radioButton_category);
        byDate = findViewById(R.id.radioButton_date);
        radioGroup = findViewById(R.id.radioGroup);
        database = AppDatabase.getAppDatabase(getApplicationContext());
        vehicleDAO = database.vehicledao();
        expenseDAO = database.expensedao();
        loadSpinnerData();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (byCategory.isChecked()) {
                    showByCategories();
                } else if (byDate.isChecked()) {
                    //code to show expenses by date
                }
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
}
