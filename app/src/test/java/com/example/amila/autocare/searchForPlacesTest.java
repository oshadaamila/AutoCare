package com.example.amila.autocare;

import com.example.amila.autocare.Controllers.search_for_places.searchForPlaces;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pavilion 15 on 25-Mar-18.
 */

public class searchForPlacesTest {
    searchForPlaces sFP = new searchForPlaces();
    @Test
    public void getUrlisCorrect() throws Exception {

        String returnedURl = sFP.getUrl(4.2563, 5.2658, "gas_station");
        String corectURl = "https://troppo-parachutes.000webhostapp.com/includes/get_nearby_stores.php?lat=4.2563&lng=5.2658&category=gas_station";
        assertEquals(returnedURl, corectURl);
    }

}
