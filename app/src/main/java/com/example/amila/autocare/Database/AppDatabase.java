package com.example.amila.autocare.Database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.amila.autocare.Database.dao.VehicleDAO;
import com.example.amila.autocare.Database.entities.Vehicle;

/**
 * Created by amila on 3/5/18.
 */
@Database(entities = {Vehicle.class}, version = 16,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public abstract VehicleDAO vehicledao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,AppDatabase.class,"database").build();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
