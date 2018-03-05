package com.example.amila.autocare.Database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import com.example.amila.autocare.Database.entities.Vehicle;

/**
 * Created by amila on 3/5/18.
 */
@Database(entities = {Vehicle.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public AppDatabase(){

    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    /*private static class AppDatabaseHolder {
        private static final AppDatabase INSTANCE = new AppDatabase();
    }

    public static AppDatabase getInstance() {
        return AppDatabaseHolder.INSTANCE;
    }*/
}
