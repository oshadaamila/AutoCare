package com.example.amila.autocare.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.amila.autocare.Database.dao.ExpenseCategoryDAO;
import com.example.amila.autocare.Database.dao.ExpenseDAO;
import com.example.amila.autocare.Database.dao.VehicleDAO;
import com.example.amila.autocare.Database.entities.ExpenseCategory;
import com.example.amila.autocare.Database.entities.Expenses;
import com.example.amila.autocare.Database.entities.Vehicle;

/**
 * Created by amila on 3/5/18.
 */
@Database(entities = {Vehicle.class, Expenses.class, ExpenseCategory.class}, version = 18, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "database")
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    ExpenseCategory cat1 = new ExpenseCategory("Add new category...");
                                    ExpenseCategory cat4 = new ExpenseCategory("Fuel");
                                    ExpenseCategory cat2 = new ExpenseCategory("Spare Parts");
                                    ExpenseCategory cat3 = new ExpenseCategory("Extra");

                                    getAppDatabase(context).expenseCategoryDAO().insertAll(cat1, cat2, cat3, cat4);
                                }
                            });
                        }
                    }).build();

        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract VehicleDAO vehicledao();

    public abstract ExpenseDAO expensedao();

    public abstract ExpenseCategoryDAO expenseCategoryDAO();
}
