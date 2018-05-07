package com.example.amila.autocare.offers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amila.autocare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavilion 15 on 07-May-18.
 */

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

    private List<Offer> mDataset = new ArrayList<>();

    public OfferAdapter(List<Offer> offers) {
        this.mDataset = offers;
    }

    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_card, parent, false);
        OfferAdapter.ViewHolder vh = new OfferAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(OfferAdapter.ViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.description.setText(mDataset.get(position).getDescription());
        holder.store.setText(mDataset.get(position).getStore_name());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, store;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_offer_card);
            description = itemView.findViewById(R.id.description_offer_card);
            store = itemView.findViewById(R.id.store_offer_card);
        }
    }
}
