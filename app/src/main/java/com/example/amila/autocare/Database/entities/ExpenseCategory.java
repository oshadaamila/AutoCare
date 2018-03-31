package com.example.amila.autocare.Database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by pavilion 15 on 31-Mar-18.
 */
@Entity(tableName = "ExpenseCategory")
public class ExpenseCategory {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "category")
    String category;

    public ExpenseCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
