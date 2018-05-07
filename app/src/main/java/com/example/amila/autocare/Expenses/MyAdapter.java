package com.example.amila.autocare.Expenses;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amila.autocare.Database.entities.Expenses;
import com.example.amila.autocare.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavilion 15 on 09-Apr-18.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    final Activity mActivity;
    final Context mContext;
    private List<Expenses> mDataset = new ArrayList<>();
    // Provide a suitable constructor (depends on the kind of dataset)

    public MyAdapter(List<Expenses> expenses, Activity mActivity, Context mContext) {

        this.mDataset = expenses;
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_card, parent, false);
        ViewHolder vh = new ViewHolder(v, mActivity, mContext);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DateFormat df = new SimpleDateFormat("d-M-y");

        String date = df.format(mDataset.get(position).getDate());
        holder.category.setText(mDataset.get(position).getCategory());
        holder.sum.setText((Float.toString(mDataset.get(position).getExpense())));
        holder.date.setText(date);
        holder.time.setText(mDataset.get(position).getTime());
        holder.setId(mDataset.get(position).getExpense_id());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final Activity mActivity;
        final Context mContext;
        // each data item is just a string in this case
        public TextView sum;
        public TextView category, date, time;
        public int id;

        public ViewHolder(View v, final Activity mActivity, final Context mContext) {
            super(v);
            sum = v.findViewById(R.id.textView_sum);
            category = v.findViewById(R.id.textView_category);
            date = v.findViewById(R.id.textView_date);
            time = v.findViewById(R.id.textView_time);
            this.mActivity = mActivity;
            this.mContext = mContext;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext,Integer.toString(id),Toast.LENGTH_LONG).show();
                    FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                    Fragment prev = mActivity.getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    EditExpense newFragment = new EditExpense();
                    Bundle bundle = new Bundle();
                    bundle.putInt("expense_id", id);
                    bundle.putString("date", date.getText().toString());
                    bundle.putString("time", time.getText().toString());
                    bundle.putString("amount", sum.getText().toString());
                    bundle.putString("category", category.getText().toString());
                    newFragment.setArguments(bundle);
                    newFragment.show(ft, "dialog");

                }
            });
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
