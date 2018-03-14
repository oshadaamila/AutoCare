package com.example.amila.autocare.ManageVehicles;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.entities.Vehicle;
import com.example.amila.autocare.R;

import java.util.Arrays;

public class ViewVehicle extends AppCompatActivity {

    String reg_no;
    Vehicle vehicle;
    TextView tv_insurance_date, tv_model, tv_revenue_license_expiry, tv_reg_no, tv_next_service, tv_name, tv_mileage;
    Spinner spinner_brand;
    AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle);
        Bundle bundle = getIntent().getExtras();
        reg_no = bundle.getString("Reg_No");
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());

        tv_insurance_date = findViewById(R.id.insurance_dateView);
        tv_model = findViewById(R.id.editText_modelView);
        tv_reg_no = findViewById(R.id.editText_reg_noView);
        tv_revenue_license_expiry = findViewById(R.id.revenue_license_expiryView);
        tv_next_service = findViewById(R.id.next_serviceView);
        tv_name = findViewById(R.id.editTextNameView);
        tv_mileage = findViewById(R.id.editTextMileageView);
        spinner_brand = findViewById(R.id.spinner_select_brandView);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_brands, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_brand.setAdapter(adapter);

        vehicle = getVehicle(reg_no);

    }

    private Vehicle getVehicle(final String reg_no) {
        final Vehicle[] vehicle = new Vehicle[1];
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                vehicle[0] = appDatabase.vehicledao().selectOne(reg_no);
                setValues(vehicle[0]);
            }
        });
        return vehicle[0];
    }


    private void setValues(Vehicle vehicle) {
        String[] array = getResources().getStringArray(R.array.vehicle_brands);
        tv_name.setText(vehicle.getName());
        spinner_brand.setSelection(Arrays.asList(array).indexOf(vehicle.getBrand()));
        tv_model.setText(vehicle.getModel());
        tv_insurance_date.setText(vehicle.getInsurance_expiry_date());
        tv_revenue_license_expiry.setText(vehicle.getRevenue_license_expiry_date());
        tv_next_service.setText(vehicle.getNext_service_date());
        tv_mileage.setText(vehicle.getMileage());
        tv_reg_no.setText(vehicle.getReg_no());
        tv_name.setKeyListener(null);
    }

}
