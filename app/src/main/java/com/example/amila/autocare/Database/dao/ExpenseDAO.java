package com.example.amila.autocare.Database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.amila.autocare.Database.entities.Expenses;

/**
 * Created by pavilion 15 on 14-Mar-18.
 */
@Dao
public interface ExpenseDAO {

    @Insert
    void insertAll(Expenses... expenses);

}
