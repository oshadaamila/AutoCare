package com.example.amila.autocare;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.amila.autocare.Controllers.ManageVehicles.add_edit_vehicle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;


/**
 * Created by pavilion 15 on 25-Mar-18.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddEditVehicleTest {

    @Rule
    public ActivityTestRule<add_edit_vehicle> mActivityRule = new ActivityTestRule<>(
            add_edit_vehicle.class);
    private String vehicle_name, model, reg_no_letter, reg_no_number, insurance_expiry_date,
            revenue_license_expiry, next_service_date, mileage;

    @Before
    public void initValidString() {
        // Specify a valid string.
        vehicle_name = "Testname";
        model = "Testmodel";
        reg_no_letter = "KH";
        reg_no_number = "4567";
        insurance_expiry_date = "5-6-2018";
        revenue_license_expiry = "8-7-2018";
        next_service_date = "9-9-2018";
        mileage = "45676";
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextName))
                .perform(typeText(vehicle_name), closeSoftKeyboard());
        //onView(withId(R.id.insurance_date)).perform(ViewActions.click());
        onView(withId(R.id.next_service)).perform(ViewActions.click());

        // Check that the text was changed.
        //onView(withId(R.id.textToBeChanged))
        //.check(matches(withText(mStringToBetyped)));
    }

    @Test
    public void ensureVehicleSpinnerIsPresent() throws Exception {
        add_edit_vehicle activity = mActivityRule.getActivity();
        Spinner viewById = activity.findViewById(R.id.spinner_select_brand);
        assertThat(viewById, notNullValue());
        assertThat(viewById, instanceOf(Spinner.class));
        SpinnerAdapter adapter = viewById.getAdapter();
        assertThat(adapter, instanceOf(SpinnerAdapter.class));
        assertThat(adapter.getCount(), greaterThan(1));
    }

}
