package com.example.amila.autocare;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.amila.autocare.Controllers.search_for_places.searchForPlaces;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * Created by pavilion 15 on 17-May-18.
 */
@MediumTest
@RunWith(JUnit4.class)
public class search_for_place_test {

    @Rule
    public ActivityTestRule<searchForPlaces> rule = new ActivityTestRule<>(searchForPlaces.class);

    @Test
    public void ensureVehicleSpinnerIsPresent() throws Exception {
        searchForPlaces activity = rule.getActivity();
        Spinner viewById = activity.findViewById(R.id.spinnerCateogary);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(Spinner.class));
        SpinnerAdapter adapter = viewById.getAdapter();
        assertThat(adapter, instanceOf(SpinnerAdapter.class));
        assertThat(adapter.getCount(), greaterThan(1));
    }
}
