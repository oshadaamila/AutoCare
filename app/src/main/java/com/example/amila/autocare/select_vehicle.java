package com.example.amila.autocare;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class select_vehicle extends AppCompatActivity {
    TextView tv_insurance_date,tv_model,tv_revenue_license_expiry,tv_reg_no,tv_next_service;
    Spinner spinner_brand;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    long vehicle_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
        mDatabase = FirebaseDatabase.getInstance().getReference("Vehicles");
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();
        Spinner spinner = (Spinner) findViewById(R.id.spinner_select_brand);
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
        findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand,model,reg_no,insurance_expiry,revenue_license_expiry,next_service;
                brand = spinner_brand.getSelectedItem().toString().trim();
                model= tv_model.getText().toString().trim();
                reg_no = tv_reg_no.getText().toString().trim();
                insurance_expiry = tv_insurance_date.getText().toString().trim();
                revenue_license_expiry=tv_revenue_license_expiry.getText().toString().trim();
                next_service = tv_next_service.getText().toString().trim();
                Vehicle vehicle = new Vehicle(brand,model,reg_no,insurance_expiry,revenue_license_expiry,next_service);
                String userId = mUser.getUid();
                setVehicleCount(mUser.getUid());
                mDatabase.child(userId).child(Long.toString(vehicle_count+1)).setValue(vehicle).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            tv_reg_no.setText("");
                            tv_model.setText("");
                            tv_insurance_date.setText("");
                            tv_revenue_license_expiry.setText("");
                            Toast.makeText(getApplicationContext(),"Vehicle Added",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Check Values",Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });

    }



    
    public void setVehicleCount(String userID){
        DatabaseReference users_vehicles=FirebaseDatabase.getInstance().getReference("Vehicles").
                child(userID);
        users_vehicles.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vehicle_count = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


