package com.example.amila.autocare.Database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.amila.autocare.Database.entities.ExpenseCategory;

import java.util.List;

/**
 * Created by pavilion 15 on 31-Mar-18.
 */
@Dao
public interface ExpenseCategoryDAO {

    @Insert
    void insertAll(ExpenseCategory... expenseCategories);

    @Query("SELECT category FROM ExpenseCategory")
    LiveData<List<String>> getAllCategories();
}

