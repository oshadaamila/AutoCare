package com.example.amila.autocare.Expenses;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.dao.VehicleDAO;
import com.example.amila.autocare.R;

import java.util.List;

public class add_expense extends AppCompatActivity {

    Spinner vehicle;
    AppDatabase appDatabase;
    List<String> nameList;
    VehicleDAO vehicleDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        vehicle = findViewById(R.id.spinner_select_vehicle);
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
        vehicleDAO = appDatabase.vehicledao();
        loadSpinnerData();


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
    }
}
