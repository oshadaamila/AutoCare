package com.example.amila.autocare;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.amila.autocare.Controllers.offers.ViewOffers;

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
class ViewOffersTest {
    @Rule
    public ActivityTestRule<ViewOffers> rule = new ActivityTestRule<>(ViewOffers.class);

    @Test
    public void ensureVehicleSpinnerIsPresent() throws Exception {
        ViewOffers activity = rule.getActivity();
        Spinner viewById = activity.findViewById(R.id.spinner_select_shop_type);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(Spinner.class));
        SpinnerAdapter adapter = viewById.getAdapter();
        assertThat(adapter, instanceOf(SpinnerAdapter.class));
        assertThat(adapter.getCount(), greaterThan(1));
    }
}
