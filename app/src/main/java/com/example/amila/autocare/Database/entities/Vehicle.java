package com.example.amila.autocare.Database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.widget.Spinner;

/**
 * Created by amila on 2/21/18.
 */
@Entity(tableName = "vehicle")
public class Vehicle {

    //private String brand,model,reg_no,insurance_expiry_date,revenue_license_expiry_date,next_service_date;

    @ColumnInfo(name = "name")
    private String name;

    @PrimaryKey
    @NonNull
    private String reg_no;

    @ColumnInfo(name = "mileage")
    private String mileage;

    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "insurance_expiry_date")
    private String insurance_expiry_date;

    @ColumnInfo(name = "revenue_license_expiry_date")
    private String revenue_license_expiry_date;

    @ColumnInfo(name = "next_service_date")
    private String next_service_date;




    public Vehicle(String brand,String model, String reg_no, String insurance_expiry_date,
                   String revenue_license_expiry_date, String next_service_date,String name,String mileage){
        this.brand=brand;
        this.model = model;
        this.reg_no=reg_no;
        this.insurance_expiry_date = insurance_expiry_date;
        this.revenue_license_expiry_date = revenue_license_expiry_date;
        this.next_service_date = next_service_date;
        this.name=name;
        this.mileage = mileage;
    }

    public String getRevenue_license_expiry_date() {
        return revenue_license_expiry_date;
    }

    public String getReg_no() {
        return reg_no;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getNext_service_date() {
        return next_service_date;
    }

    public String getInsurance_expiry_date() {
        return insurance_expiry_date;
    }

    public String getName() {
        return name;
    }

    public String getMileage() {
        return mileage;
    }
}
