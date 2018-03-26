package com.example.amila.autocare;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pavilion 15 on 25-Mar-18.
 */

public class searchForPlacesTest {
    @Test
    public void getUrlisCorrect() throws Exception {
        searchForPlaces sFP = new searchForPlaces();
        String returnedURl = sFP.getUrl(4.2563, 5.2658, "gas_station");
        String corectURl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=4.2563,5.2658&radius=2000&type=gas_station&key=AIzaSyAGEmXKuOc06L38gc0btsc8m0XS09z1-NM";
        assertEquals(returnedURl, corectURl);
    }

}
