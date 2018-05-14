package com.example.amila.autocare.Views.ManageVehicles;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amila.autocare.R;

/**
 * Created by pavilion 15 on 14-May-18.
 */

public class DeleteDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.delete_dialog, container, false);
        return v;
    }
}