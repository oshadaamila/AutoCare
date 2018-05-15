package com.example.amila.autocare.Views.ManageVehicles;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.R;

/**
 * Created by pavilion 15 on 14-May-18.
 */

public class DeleteDialog extends DialogFragment {
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

        View v = inflater.inflate(R.layout.delete_dialog, container, false);
        TextView yes = v.findViewById(R.id.yes_delete);
        TextView no = v.findViewById(R.id.no_delete);
        final String reg_no = this.getArguments().getString("reg_no");
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.vehicledao().deleteVehicle(reg_no);
                    }
                });
                viewvehicle.finish();
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