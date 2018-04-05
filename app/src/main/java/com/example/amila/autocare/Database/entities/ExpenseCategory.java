package com.example.amila.autocare.Database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by pavilion 15 on 31-Mar-18.
 */
@Entity(tableName = "ExpenseCategory")
public class ExpenseCategory {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "category")
    String category;

    public ExpenseCategory(String category) {
        this.category = category;
    }

    @Ignore
    public ExpenseCategory(@NonNull int id, String category) {
        this.id = id;
        this.category = category;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
