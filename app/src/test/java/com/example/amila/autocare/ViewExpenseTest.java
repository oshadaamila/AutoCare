package com.example.amila.autocare;

import com.example.amila.autocare.Views.Expenses.ViewExpense;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pavilion 15 on 15-May-18.
 */

public class ViewExpenseTest {
    ViewExpense viewExpense = new ViewExpense();

    @Test
    public void dptopxtest() throws Exception {
        int returnedValue = viewExpense.dpToPx(20);
        int corectValue = 25;
        assertEquals(returnedValue, corectValue);
    }
}
