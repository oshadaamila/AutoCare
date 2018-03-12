package com.example.amila.autocare.Carquery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amila.autocare.add_edit_vehicle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.google.android.gms.internal.zzahn.runOnUiThread;

/**
 * Created by pavilion 15 on 10-Mar-18.
 */

public class getBrands extends AsyncTask<Object, String, Void> {
    List tempList;
    TextView textView;
    Context mContext;
    Activity mActivity;
    ProgressDialog progressDialog;
    public getBrands(Context context, Activity activity){
        this.mContext = context;
        this.mActivity=activity;
    }


    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Downloading your data...");
        progressDialog.show();

    }
    @Override
    protected Void doInBackground(Object...objects) {
        HttpHandler sh = new HttpHandler();
        tempList = (List)objects[0];
        textView = (TextView)objects[1];
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall("https://www.carqueryapi.com/api/0.3/?callback=?&cmd=getMakes");

        Log.e("getBrands", "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr.substring(jsonStr.indexOf("{"), jsonStr.lastIndexOf("}") + 1));

                // Getting JSON Array node
                JSONArray makes = jsonObj.getJSONArray("Makes");

                // looping through All Contacts
                for (int i = 0; i < makes.length(); i++) {
                    JSONObject c = makes.getJSONObject(i);

                    String Model = c.getString("make_display");

                   //publishProgress(Model);
                    tempList.add(Model);
                    textView.setText(Model);
                    progressDialog.dismiss();
                    // tmp hash map for single contact




                }
            } catch (final JSONException e) {
                Log.e("getBrand", "Json parsing error: " + e.getMessage());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(,"Error",Toast.LENGTH_LONG).show();
                    }
                });

            }
        } else {
            Log.e("getBrand", "Couldn't get json from server.");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    /*Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();*/
                }
            });

        }
        return  null;

    }


    @Override
    protected void onProgressUpdate(String...strings) {
        tempList.add(strings);
    }

    @Override
    protected void onPostExecute(Void v){
        //do nothing
        progressDialog.dismiss();
        Log.d("getBrand","onPostExecute");


    }


}

