package com.example.amila.autocare;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.dao.VehicleDAO;
import com.example.amila.autocare.Database.entities.Vehicle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class add_edit_vehicle extends AppCompatActivity {
    TextView tv_insurance_date,tv_model,tv_revenue_license_expiry,tv_reg_no,tv_next_service,tv_name,tv_mileage;
    Spinner spinner_brand;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_select_brand);
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());


// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_brands, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        tv_insurance_date = findViewById(R.id.insurance_date);
        spinner_brand=findViewById(R.id.spinner_select_brand);
        tv_model= findViewById(R.id.editText_model);
        tv_reg_no = findViewById(R.id.editText_reg_no);
        tv_revenue_license_expiry =findViewById(R.id.revenue_license_expiry);
        tv_next_service = findViewById(R.id.next_service);
        tv_name=findViewById(R.id.editTextName);
        tv_mileage = findViewById(R.id.editTextMileage);


        findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand,model,reg_no,insurance_expiry,revenue_license_expiry,next_service,name,mileage;

                    brand = spinner_brand.getSelectedItem().toString().trim();
                    model = tv_model.getText().toString().trim();
                    reg_no = tv_reg_no.getText().toString().trim();
                    insurance_expiry = tv_insurance_date.getText().toString().trim();
                    revenue_license_expiry = tv_revenue_license_expiry.getText().toString().trim();
                    next_service = tv_next_service.getText().toString().trim();
                    name=tv_name.getText().toString().trim();
                    mileage=tv_mileage.getText().toString().trim();

                    final Vehicle vehicle = new Vehicle(brand, model, reg_no, insurance_expiry, revenue_license_expiry, next_service,name,mileage);

                if(model.isEmpty() || reg_no.isEmpty() || insurance_expiry.isEmpty() ||
                        revenue_license_expiry.isEmpty()||next_service.isEmpty()|| name.isEmpty()||mileage.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the values correctly!", Toast.LENGTH_LONG).show();
                }else{

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                appDatabase.vehicledao().insertAll(vehicle);
                            }catch (Exception e){
                                final String error= e.getMessage();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        }
                    });

                 }

            }
        });

        findViewById(R.id.insurance_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(R.id.insurance_date);
                newFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

        findViewById(R.id.next_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(R.id.next_service);
                newFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

        findViewById(R.id.revenue_license_expiry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(R.id.revenue_license_expiry);
                newFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

    }
}


