package com.example.amila.autocare.Database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.amila.autocare.Database.POJOS.CategorySum;
import com.example.amila.autocare.Database.entities.Expenses;

import java.util.Date;
import java.util.List;

/**
 * Created by pavilion 15 on 14-Mar-18.
 */
@Dao
public interface ExpenseDAO {

    @Insert
    void insertAll(Expenses... expenses);

    @Query("select category,sum(expense) as sum from expenses where reg_no =:Reg_NO group by category")
    LiveData<List<CategorySum>> getCategoriesAndSums(String Reg_NO);

    @Query("SELECT * FROM expenses WHERE date BETWEEN (:from) AND date(:to) ")
    LiveData<List<Expenses>> getExpensesByDate(Date from, Date to);
}
