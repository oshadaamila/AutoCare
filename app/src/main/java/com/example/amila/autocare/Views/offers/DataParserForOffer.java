package com.example.amila.autocare.Views.offers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavilion 15 on 08-May-18.
 */

public class DataParserForOffer {
    private Context mContext;

    public DataParserForOffer(Context mContext) {
        this.mContext = mContext;
    }

    public List<Offer> parse(String jsonData) throws JSONException {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            Log.d("Places", "parse");
            if (jsonData == null || jsonData.equals("{\"results\":[]}")) {
                Toast.makeText(mContext, "No result found", Toast.LENGTH_LONG).show();
            }
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            Log.d("Places", "parse error");
            e.printStackTrace();
        }
        return getOffers(jsonArray);
    }

    private List<Offer> getOffers(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<Offer> offersList = new ArrayList<>();
        Offer offer = null;
        Log.d("Places", "getPlaces");

        for (int i = 0; i < placesCount; i++) {
            try {
                offer = getOffer((JSONObject) jsonArray.get(i));
                offersList.add(offer);
                Log.d("Places", "Adding places");

            } catch (JSONException e) {
                Log.d("Places", "Error in Adding places");
                e.printStackTrace();
            }
        }
        return offersList;
    }

    private Offer getOffer(JSONObject googlePlaceJson) {
        String title, description, store_name;
        Offer offer = new Offer();

        Log.d("getPlace", "Entered");

        try {
            title = googlePlaceJson.getString("title");
            description = googlePlaceJson.getString("details");
            store_name = googlePlaceJson.getString("name");
            offer.setTitle(title);
            offer.setDescription(description);
            offer.setStore_name(store_name);

            Log.d("getPlace", "Putting Places");
        } catch (JSONException e) {
            Log.d("getPlace", "Error");
            e.printStackTrace();
        }
        return offer;
    }


}
