package com.example.amila.autocare.Views.offers;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.amila.autocare.Views.search_for_places.DownloadUrl;

import org.json.JSONException;

import java.util.List;

/**
 * Created by pavilion 15 on 08-May-18.
 */

public class GetNearbyOffers extends AsyncTask<Object, String, String> {

    String offersData, url;
    Context mcontext;
    RecyclerView rv;

    @Override
    protected String doInBackground(Object... objects) {
        try {
            Log.d("Getoffersdata", "doInBackground entered");
            rv = (RecyclerView) objects[0];
            url = (String) objects[1];
            mcontext = (Context) objects[2];
            DownloadUrl downloadUrl = new DownloadUrl();
            offersData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return offersData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<Offer> nearbyOfferList = null;

        // Data parser object will be used to parse the data from json object
        DataParserForOffer dataParser = new DataParserForOffer(mcontext);
        try {
            nearbyOfferList = dataParser.parse(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ShowOfferLIst(nearbyOfferList);

        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowOfferLIst(List<Offer> nearbyOfferList) {
        OfferAdapter offerAdapter = new OfferAdapter(nearbyOfferList, mcontext);
        rv.setAdapter(offerAdapter);
    }
}
