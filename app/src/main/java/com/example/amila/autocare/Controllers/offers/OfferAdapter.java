package com.example.amila.autocare.Controllers.offers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.amila.autocare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavilion 15 on 07-May-18.
 */

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private List<Offer> mDataset = new ArrayList<>();
    private Context mContext;

    public OfferAdapter(List<Offer> offers, Context context) {
        this.mDataset = offers;
        this.mContext = context;
    }

    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_card, parent, false);
        OfferAdapter.ViewHolder vh = new OfferAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(OfferAdapter.ViewHolder holder, final int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.description.setText(mDataset.get(position).getDescription());
        holder.store.setText(mDataset.get(position).getStore_name());
        final ViewHolder holder2 = holder;
        final String store_name = mDataset.get(position).getStore_name();
        final Float lat = Float.parseFloat(mDataset.get(position).getStore_lat());
        final Float lng = Float.parseFloat(mDataset.get(position).getStore_lng());
        holder.view_on_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowStore.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("name", store_name);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, store;
        Button view_on_map;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_offer_card);
            description = itemView.findViewById(R.id.description_offer_card);
            store = itemView.findViewById(R.id.store_offer_card);
            view_on_map = itemView.findViewById(R.id.btn_view_in_map);
        }
    }
}
