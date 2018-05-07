package com.example.amila.autocare.offers;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.amila.autocare.R;
import com.example.amila.autocare.navigationDrawer;

public class ViewOffers extends AppCompatActivity {
    Spinner spinnerCateogary;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offers);
        spinnerCateogary = findViewById(R.id.spinner_select_shop_type);
        setSpinnerValues();
        rv = findViewById(R.id.recycler_viewer_offers);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new navigationDrawer.GridSpacingItemDecoration(1, dpToPx(10), true));
        rv.setItemAnimator(new DefaultItemAnimator());

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
}
