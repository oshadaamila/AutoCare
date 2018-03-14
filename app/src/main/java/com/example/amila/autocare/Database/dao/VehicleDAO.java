package com.example.amila.autocare.Database.dao;

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
    List<Vehicle> getAll();

    @Insert
    void insertAll(Vehicle...vehicles);

    @Query("SELECT * FROM Vehicle WHERE reg_no=:Reg_NO")
    Vehicle selectOne(String Reg_NO);
}
