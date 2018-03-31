package com.example.amila.autocare.Expenses;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.dao.ExpenseCategoryDAO;
import com.example.amila.autocare.Database.dao.VehicleDAO;
import com.example.amila.autocare.R;

import java.util.List;

public class add_expense extends AppCompatActivity {

    Spinner vehicle;
    Spinner category;
    AppDatabase appDatabase;
    List<String> nameList;
    VehicleDAO vehicleDAO;
    ExpenseCategoryDAO expenseCategoryDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        vehicle = findViewById(R.id.spinner_select_vehicle);
        category = findViewById(R.id.spinner_category);
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
        vehicleDAO = appDatabase.vehicledao();
        expenseCategoryDAO = appDatabase.expenseCategoryDAO();
        loadSpinnerData();
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

    }

    private void loadSpinnerData() {

        vehicleDAO.getAllNames().observe(this, new Observer<List<String>>() {
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
