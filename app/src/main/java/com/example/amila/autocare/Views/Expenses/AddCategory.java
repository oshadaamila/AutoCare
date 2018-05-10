package com.example.amila.autocare.Views.Expenses;

import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.amila.autocare.Database.AppDatabase;
import com.example.amila.autocare.Database.dao.ExpenseCategoryDAO;
import com.example.amila.autocare.Database.entities.ExpenseCategory;
import com.example.amila.autocare.R;

/**
 * Created by pavilion 15 on 31-Mar-18.
 */

public class AddCategory extends DialogFragment {
    AppDatabase appDatabase;

    public AddCategory() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getAppDatabase(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_new_expense_category, container, false);
        Button btn = v.findViewById(R.id.button_update_category);
        final EditText editTextCategory = v.findViewById(R.id.editText_new_expense_category);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String category = editTextCategory.getText().toString().trim();
                        ExpenseCategoryDAO expenseCategoryDAO = appDatabase.expenseCategoryDAO();
                        ExpenseCategory expenseCategory = new ExpenseCategory(category);
                        expenseCategoryDAO.insertAll(expenseCategory);
                    }
                });
            }
        });
        return v;
    }
}
