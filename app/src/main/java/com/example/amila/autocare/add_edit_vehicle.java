package com.example.amila.autocare;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.entities.Vehicle;
import com.example.amila.autocare.Reminders.ScheduleClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class add_edit_vehicle extends AppCompatActivity {
    TextView tv_insurance_date,tv_model,tv_revenue_license_expiry,tv_reg_no,tv_next_service,tv_name,tv_mileage;
    Spinner spinner_brand;
    AppDatabase appDatabase;
    ScheduleClient scheduleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Calendar cc = dateStringConverter("2011/1/2");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
        Spinner spinner = findViewById(R.id.spinner_select_brand);
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
        scheduleClient = new ScheduleClient(getApplicationContext());
        scheduleClient.doBindService();


       /* ArrayList<String> modellist = new ArrayList<String>();
        Object[] DataTransfer = new Object[2];
        DataTransfer[0] =modellist;
        DataTransfer[1]=findViewById(R.id.textViewModel);
        getBrands getbrands = new getBrands(getApplicationContext(),add_edit_vehicle.this);
        getbrands.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DataTransfer);*/

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

    private Calendar dateStringConverter(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        String pattern = "y/M/d";
        Date date1 = new SimpleDateFormat(pattern).parse(date);
        c.setTime(date1);
        return c;
    }
}


