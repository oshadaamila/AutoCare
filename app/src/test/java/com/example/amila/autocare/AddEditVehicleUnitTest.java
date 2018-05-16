package com.example.amila.autocare;

import com.example.amila.autocare.Controllers.ManageVehicles.add_edit_vehicle;

import org.junit.Test;

import java.util.Calendar;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pavilion 15 on 17-May-18.
 */

public class AddEditVehicleUnitTest {
    add_edit_vehicle aev = new add_edit_vehicle();

    @Test
    public void dateToStringConverter() throws Exception {
        Calendar returnedDate = aev.dateStringConverter("17/05/2015");
        Calendar date = Calendar.getInstance();
        assertEquals(returnedDate, date);
    }

}
