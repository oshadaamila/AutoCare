package com.example.amila.autocare.Views.offers;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.amila.autocare.R;
import com.example.amila.autocare.Views.Start.navigationDrawer;

public class ViewOffers extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerCateogary;
    RecyclerView rv;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offers);
        spinnerCateogary = findViewById(R.id.spinner_select_shop_type);
        spinnerCateogary.setOnItemSelectedListener(this);
        error = findViewById(R.id.no_internet_text_view);
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        if (checkInternetConnectivity()) {
            error.setVisibility(View.INVISIBLE);
            setSpinnerValues();
            rv = findViewById(R.id.recycler_viewer_offers);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv.setLayoutManager(mLayoutManager);
            rv.addItemDecoration(new navigationDrawer.GridSpacingItemDecoration(1, dpToPx(10), true));
            rv.setItemAnimator(new DefaultItemAnimator());
        } else {
            spinnerCateogary.setVisibility(View.INVISIBLE);
            error.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnectivity();
    }

    private void setSpinnerValues() {
        String[] cateogaries = {"Service Center", "Tyre Service Center", "Spare Part Store"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cateogaries);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCateogary.setAdapter(arrayAdapter);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public boolean checkInternetConnectivity() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity.getActiveNetworkInfo() != null) {
            if (connectivity.getActiveNetworkInfo().isConnected()) {
                //do nothing since there is already a connection
                return true;
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Internet Connection");  // GPS not found
            builder.setMessage("You may need to connect to the internet to use this service"); // Want to enable?
            builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Don't Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //insert code to system response when user clicks dont enable

                }
            });
            builder.create().show();

        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemIdAtPosition(position) == 0) {
            findCateogary("Service%20Center");
        } else if (parent.getItemIdAtPosition(position) == 1) {
            Log.d("spinnerTag", parent.getItemAtPosition(1).toString());
            findCateogary("Tyre%20Service%20Center");
        } else if (parent.getItemIdAtPosition(position) == 2) {
            Log.d("spinnerTag", parent.getItemAtPosition(2).toString());
            findCateogary("Spare%20Part%20Store");
        }
    }

    private void findCateogary(String cateogary) {

        String url = getUrl(cateogary);
        Object[] DataTransfer = new Object[3];
        DataTransfer[0] = rv;
        DataTransfer[1] = url;
        DataTransfer[2] = getApplicationContext();
        Log.d("onClick", url);
        GetNearbyOffers getNearbyOffers = new GetNearbyOffers();
        getNearbyOffers.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DataTransfer);

    }

    private String getUrl(String cateogary) {
        String url = "https://troppo-parachutes.000webhostapp.com/includes/get_Offers.php?category=" + cateogary;
        return url;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
