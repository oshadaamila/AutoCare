package com.example.amila.autocare;

/**
 * Created by amila on 2/21/18.
 */

public class Vehicle {

    public String brand,model,reg_no,insurance_expiry_date,revenue_license_expiry_date,next_service_date;

    public Vehicle(){

    }

    public Vehicle(String brand,String model, String reg_no, String insurance_expiry_date,
                   String revenue_license_expiry_date, String next_service_date){
        this.brand=brand;
        this.model = model;
        this.reg_no=reg_no;
        this.insurance_expiry_date = insurance_expiry_date;
        this.revenue_license_expiry_date = revenue_license_expiry_date;
        this.next_service_date = next_service_date;
    }
}
