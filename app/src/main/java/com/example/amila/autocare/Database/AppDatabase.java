package com.example.amila.autocare.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.amila.autocare.Database.dao.ExpenseDAO;
import com.example.amila.autocare.Database.dao.VehicleDAO;
import com.example.amila.autocare.Database.entities.Expenses;
import com.example.amila.autocare.Database.entities.Vehicle;

/**
 * Created by amila on 3/5/18.
 */
@Database(entities = {Vehicle.class, Expenses.class}, version = 18, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class,"database").build();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract VehicleDAO vehicledao();

    public abstract ExpenseDAO expensedao();
}
