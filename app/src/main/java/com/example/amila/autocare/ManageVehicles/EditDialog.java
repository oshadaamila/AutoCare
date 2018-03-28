package com.example.amila.autocare.ManageVehicles;

/**
 * Created by pavilion 15 on 28-Mar-18.
 */

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amila.autocare.R;

/**
 * Created by pavilion 15 on 27-Mar-18.
 */

public class EditDialog extends DialogFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_layout, container, false);
        Button btn = v.findViewById(R.id.button_update);
        EditText editText = v.findViewById(R.id.editText_edit_value);
        editText.setText(this.getArguments().getString("value"));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This is clixked", Toast.LENGTH_LONG).show();
            }
        });

        return v;

    }
}

