package com.example.amila.autocare;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.amila.autocare.Controllers.Expenses.ViewExpense;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * Created by pavilion 15 on 16-May-18.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class view_expense_test {
    @Rule
    public ActivityTestRule<ViewExpense> rule = new ActivityTestRule<>(ViewExpense.class);

    @Test
    public void ensureVehicleSpinnerIsPresent() throws Exception {
        ViewExpense activity = rule.getActivity();
        Spinner viewById = activity.findViewById(R.id.spinner_vehicle);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(Spinner.class));
        SpinnerAdapter adapter = viewById.getAdapter();
        assertThat(adapter, instanceOf(SpinnerAdapter.class));
        assertThat(adapter.getCount(), greaterThan(1));
    }
}
