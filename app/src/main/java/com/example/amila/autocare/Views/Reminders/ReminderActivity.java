package com.example.amila.autocare.Views.Reminders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.amila.autocare.R;

public class ReminderActivity extends AppCompatActivity {
    TextView tv1, tv2;
    String reg_no;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        tv1 = findViewById(R.id.textView_title);
        tv2 = findViewById(R.id.textView_date_reminder);
        Intent intent = getIntent();
        reg_no = (intent.getStringExtra("reg_no"));
        type = intent.getIntExtra("type", 1);
        createTextViews();
        tv2.setText(intent.getStringExtra("date"));
    }

    private void createTextViews() {
        if (type == 1) {
            tv1.setText("Insurance of your vehicle " + reg_no + " will expire on");
        }
        if (type == 2) {
            tv1.setText("Revenue License of your vehicle " + reg_no + " will expire on");
        } else {
            tv1.setText("Next Service of your wehicle " + reg_no + " is scheduled on");
        }
    }
}
