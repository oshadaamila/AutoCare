package com.example.amila.autocare.ManageVehicles;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.entities.Vehicle;
import com.example.amila.autocare.R;

public class ViewVehicle extends AppCompatActivity {

    String reg_no;
    Vehicle vehicle;
    TextView tv_insurance_date, tv_brand, tv_model, tv_revenue_license_expiry, tv_reg_no, tv_next_service, tv_name2, tv_mileage;
    AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle);
        Bundle bundle = getIntent().getExtras();
        reg_no = bundle.getString("Reg_No");
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());

        tv_insurance_date = findViewById(R.id.textView_insurance);
        tv_model = findViewById(R.id.textView_model);
        tv_reg_no = findViewById(R.id.textView_reg_no);
        tv_revenue_license_expiry = findViewById(R.id.textView_revenue_license);
        tv_next_service = findViewById(R.id.textView_next_service);
        tv_name2 = findViewById(R.id.textView_name);
        tv_mileage = findViewById(R.id.textView_mileage);
        tv_brand = findViewById(R.id.textView_brand);
        vehicle = getVehicle(reg_no);


    }

    public Vehicle getVehicle(final String reg_no) {
        final Vehicle[] vehicle = new Vehicle[1];
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                vehicle[0] = appDatabase.vehicledao().selectOne(reg_no);
                setValues(vehicle[0]);
                tv_brand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog(vehicle[0].getReg_no(), "brand", vehicle[0].getBrand());
                    }
                });
                tv_model.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog(vehicle[0].getReg_no(), "model", vehicle[0].getModel());
                    }
                });
                tv_name2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog(vehicle[0].getReg_no(), "name", vehicle[0].getName());
                    }
                });
                tv_reg_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog(vehicle[0].getReg_no(), "reg_no", vehicle[0].getReg_no());
                    }
                });
                tv_insurance_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateDateDialog updateDateDialog = new UpdateDateDialog(vehicle[0].getReg_no(), "insurance");
                        updateDateDialog.show(getSupportFragmentManager(), "datePicker");
                    }
                });
                tv_revenue_license_expiry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateDateDialog updateDateDialog = new UpdateDateDialog(vehicle[0].getReg_no(), "revenue_license");
                        updateDateDialog.show(getSupportFragmentManager(), "datePicker");
                    }
                });
                tv_next_service.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UpdateDateDialog updateDateDialog = new UpdateDateDialog(vehicle[0].getReg_no(), "next_service");
                        updateDateDialog.show(getSupportFragmentManager(), "datePicker");
                    }
                });
                tv_mileage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialog(vehicle[0].getReg_no(), "mileage", vehicle[0].getMileage());
                    }
                });
            }
        });
        return vehicle[0];
    }


    private void setValues(Vehicle vehicle) {
        tv_name2.setText(vehicle.getName());
        tv_brand.setText(vehicle.getBrand());
        tv_model.setText(vehicle.getModel());
        tv_insurance_date.setText(vehicle.getInsurance_expiry_date());
        tv_revenue_license_expiry.setText(vehicle.getRevenue_license_expiry_date());
        tv_next_service.setText(vehicle.getNext_service_date());
        tv_mileage.setText(vehicle.getMileage());
        tv_reg_no.setText(vehicle.getReg_no());
    }

    private void openDialog(String reg_no, String field, String value) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        EditDialog newFragment = new EditDialog();
        Bundle bundle = new Bundle();
        bundle.putString("reg_no", reg_no);
        bundle.putString("field", field);
        bundle.putString("value", value);
        newFragment.setArguments(bundle);
        newFragment.show(ft, "dialog");
    }


}
