package com.example.amila.autocare.Database.POJOS;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by pavilion 15 on 06-Apr-18.
 */

public class CategorySum {
    @ColumnInfo(name = "category")
    public String category;
    @ColumnInfo(name = "sum")
    public String sum;

    public CategorySum(String category, String sum) {
        this.category = category;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
