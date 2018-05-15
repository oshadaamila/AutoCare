package com.example.amila.autocare.Views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amila.autocare.Database.entities.Vehicle;
import com.example.amila.autocare.R;
import com.example.amila.autocare.Views.ManageVehicles.ViewVehicle;

import java.util.List;

/**
 * Created by pavilion 15 on 06-Mar-18.
 */

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {

    private Context mcontext;
    private List<Vehicle> vehicleList;

    public VehicleAdapter(Context mContext, List<Vehicle> vehicleList) {
        this.mcontext = mContext;
        this.vehicleList = vehicleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_card,parent,
                false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Vehicle vehicle = vehicleList.get(position);
        holder.reg_no.setText(vehicle.getReg_no());
        holder.brand.setText(vehicle.getBrand());
        holder.model.setText(vehicle.getModel());
        holder.viewFullDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcontext, ViewVehicle.class);
                intent.putExtra("Reg_No", vehicle.getReg_no());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {

        return vehicleList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView reg_no, brand, model, viewFullDetails;
        public MyViewHolder(View view){
            super(view);
            reg_no= view.findViewById(R.id.Reg_no);
            brand = view.findViewById(R.id.Brand);
            model = view.findViewById(R.id.model);
            viewFullDetails = view.findViewById(R.id.viewFullDetail);
        }
    }

}
