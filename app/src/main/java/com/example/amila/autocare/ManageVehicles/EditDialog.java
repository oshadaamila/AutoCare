package com.example.amila.autocare.ManageVehicles;

/**
 * Created by pavilion 15 on 28-Mar-18.
 */

import android.app.Activity;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.R;

/**
 * Created by pavilion 15 on 27-Mar-18.
 */

public class EditDialog extends DialogFragment {

    AppDatabase appDatabase;
    private ViewVehicle viewvehicle;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getAppDatabase(getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_layout, container, false);
        Button btn = v.findViewById(R.id.button_update);
        final EditText editText = v.findViewById(R.id.editText_edit_value);
        editText.setText(this.getArguments().getString("value"));
        final String reg_no = this.getArguments().getString("reg_no");
        final String field = this.getArguments().getString("field");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (field.equals("brand")) {
                            appDatabase.vehicledao().updateBrand(editText.getText().toString().trim(), reg_no);
                        } else if (field.equals("name")) {
                            appDatabase.vehicledao().updateName(editText.getText().toString().trim(), reg_no);
                        } else if (field.equals("model")) {
                            appDatabase.vehicledao().updateModel(editText.getText().toString().trim(), reg_no);
                        } else if (field.equals("reg_no")) {
                            appDatabase.vehicledao().updateRegNO(editText.getText().toString().trim(), reg_no);
                        } else if (field.equals("mileage")) {
                            appDatabase.vehicledao().updateMileage(editText.getText().toString().trim(), reg_no);
                        }
                    }
                });
                viewvehicle.recreate();
                dismiss();
            }
        });

        return v;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            viewvehicle = (ViewVehicle) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FeedbackListener");
        }
    }
}

