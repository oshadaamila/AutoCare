package com.example.amila.autocare.Database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.amila.autocare.Database.entities.Vehicle;

import java.util.List;

/**
 * Created by amila on 3/5/18.
 */
@Dao
public interface VehicleDAO {

    @Query("SELECT * FROM Vehicle")
    LiveData<List<Vehicle>> getAll();

    @Query("SELECT reg_no FROM Vehicle")
    LiveData<List<String>> getAllRegNO();

    @Insert
    void insertAll(Vehicle...vehicles);

    @Query("SELECT * FROM Vehicle WHERE reg_no=:Reg_NO")
    Vehicle selectOne(String Reg_NO);

    @Query("UPDATE Vehicle SET brand = :value WHERE reg_no=:Reg_NO")
    void updateBrand(String value, String Reg_NO);

    @Query("UPDATE Vehicle SET name = :value WHERE reg_no=:Reg_NO")
    void updateName(String value, String Reg_NO);

    @Query("UPDATE Vehicle SET model = :value WHERE reg_no=:Reg_NO")
    void updateModel(String value, String Reg_NO);

    @Query("UPDATE Vehicle SET reg_no = :value WHERE reg_no=:Reg_NO")
    void updateRegNO(String value, String Reg_NO);

    @Query("UPDATE Vehicle SET mileage = :value WHERE reg_no=:Reg_NO")
    void updateMileage(String value, String Reg_NO);

    @Query("UPDATE Vehicle SET insurance_expiry_date = :value WHERE reg_no=:insuranceDate")
    void updateInsuranceDate(String value, String insuranceDate);

    @Query("UPDATE Vehicle SET revenue_license_expiry_date = :value WHERE reg_no=:revenueLicenseDate")
    void updateRevenueLicense(String value, String revenueLicenseDate);

    @Query("UPDATE Vehicle SET next_service_date = :value WHERE reg_no=:nextServiceDate")
    void updateNextServiceDate(String value, String nextServiceDate);

    @Query("DELETE FROM Vehicle WHERE reg_no=:Reg_NO ")
    void deleteVehicle(String Reg_NO);
}
