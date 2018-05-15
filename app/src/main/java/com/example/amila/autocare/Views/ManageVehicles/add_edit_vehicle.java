package com.example.amila.autocare.Views.ManageVehicles;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.entities.Vehicle;
import com.example.amila.autocare.R;
import com.example.amila.autocare.Views.Fragments.DatePickerFragment;
import com.example.amila.autocare.Views.Reminders.ScheduleClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class add_edit_vehicle extends AppCompatActivity {
    TextView tv_insurance_date, tv_model, tv_revenue_license_expiry, tv_reg_no_letters, tv_reg_no_number, tv_next_service, tv_name, tv_mileage;
    Spinner spinner_brand;
    AppDatabase appDatabase;
    String requestCode;
    ScheduleClient scheduleclient1, scheduleclient2, scheduleclient3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
        Spinner spinner = findViewById(R.id.spinner_select_brand);
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
        scheduleclient1 = new ScheduleClient(this);
        scheduleclient1.doBindService();
        scheduleclient2 = new ScheduleClient(this);
        scheduleclient2.doBindService();
        scheduleclient3 = new ScheduleClient(this);
        scheduleclient3.doBindService();

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_brands, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        tv_insurance_date = findViewById(R.id.insurance_date);
        spinner_brand=findViewById(R.id.spinner_select_brand);
        tv_model = findViewById(R.id.editText_model);
        tv_reg_no_letters = findViewById(R.id.editText_reg_no_letters);
        tv_reg_no_number = findViewById(R.id.editText_reg_no_number);
        tv_revenue_license_expiry =findViewById(R.id.revenue_license_expiry);
        tv_next_service = findViewById(R.id.next_service);
        tv_name=findViewById(R.id.editTextName);
        tv_mileage = findViewById(R.id.editTextMileage);
        tv_reg_no_letters.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String brand, model, reg_no, reg_no_number, insurance_expiry, revenue_license_expiry, next_service, name, mileage;

                    brand = spinner_brand.getSelectedItem().toString().trim();
                    model = tv_model.getText().toString().trim();
                reg_no = tv_reg_no_letters.getText().toString().trim() + tv_reg_no_number.getText().toString().trim();
                reg_no_number = tv_reg_no_number.getText().toString().trim();
                requestCode = tv_reg_no_number.getText().toString().trim();
                    insurance_expiry = tv_insurance_date.getText().toString().trim();
                    revenue_license_expiry = tv_revenue_license_expiry.getText().toString().trim();
                    next_service = tv_next_service.getText().toString().trim();
                    name=tv_name.getText().toString().trim();
                    mileage=tv_mileage.getText().toString().trim();

                final Vehicle vehicle = new Vehicle(brand, model, reg_no, reg_no_number, insurance_expiry, revenue_license_expiry, next_service, name, mileage);

                if(model.isEmpty() || reg_no.isEmpty() || insurance_expiry.isEmpty() ||
                        revenue_license_expiry.isEmpty() ||
                        next_service.isEmpty() || name.isEmpty() || mileage.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the values correctly!",
                            Toast.LENGTH_LONG).show();
                }else{
                    Calendar insurance_remind_date = dateStringConverter(insurance_expiry);
                    insurance_remind_date.add(Calendar.DATE, -14);
                    Calendar revenue_license_reminder_date = dateStringConverter(revenue_license_expiry);
                    revenue_license_reminder_date.add(Calendar.DATE, -14);
                    Calendar next_service_reminder = dateStringConverter(next_service);
                    next_service_reminder.add(Calendar.DATE, -7);
                    scheduleclient1.setAlarmForNotification(insurance_remind_date,
                            "Insurance of " + name + " will expire soon!", requestCode + "1", reg_no, 1);
                    //new AlarmTask(getApplicationContext(),insurance_remind_date,"testing",requestCode,reg_no,1).run();
                    scheduleclient2.setAlarmForNotification(revenue_license_reminder_date,
                            "Revenue License of" + name + " will expire soon!", requestCode + "2", reg_no, 2);
                    scheduleclient3.setAlarmForNotification(next_service_reminder,
                            "Service is due for" + name, requestCode + "3", reg_no, 3);

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
                    finish();

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

    private Calendar dateStringConverter(String date) {
        Calendar c = Calendar.getInstance();
        String pattern = "d/M/y";
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat(pattern).parse(date);
        } catch (Exception e) {
            Log.d("parseError", e.getMessage());
        }
        c.setTime(date1);
        return c;
    }

    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if (scheduleclient1 != null) {
            scheduleclient1.doUnbindService();
        } else if (scheduleclient2 != null) {
            scheduleclient2.doUnbindService();
        } else if (scheduleclient3 != null) {
            scheduleclient3.doUnbindService();
        }
        super.onStop();
    }
}


