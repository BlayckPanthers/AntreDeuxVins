package com.ingesup.fabienlebon.antredeuxvins.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ingesup.fabienlebon.antredeuxvins.CellarActivity;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Wine;

import com.ingesup.fabienlebon.antredeuxvins.R;
import com.ingesup.fabienlebon.antredeuxvins.WineActivity;

import java.util.List;

/**
 * Created by fabienlebon on 02/04/2018.
 */

public class CellarAdapter extends RecyclerView.Adapter<CellarAdapter.ViewHolder> {

    private static final String TAG = "CellarAdapter";

    private List<Wine> wineList;
    private Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView color;
        public TextView name;
        public TextView food;
        public RelativeLayout viewBackground, viewForeground;

        public ViewHolder(View v) {
            super(v);
            color = (ImageView) v.findViewById(R.id.list_view_single_wine_color);
            name = (TextView) v.findViewById(R.id.list_view_single_wine_name);
            food = (TextView) v.findViewById(R.id.list_view_single_wine_food);
            viewBackground = (RelativeLayout) v.findViewById(R.id.viewBackground);
            viewForeground = (RelativeLayout) v.findViewById(R.id.viewForeground);
        }
    }

    public CellarAdapter(List<Wine> wineList, Activity activity) {
        this.wineList = wineList;
        this.activity = activity;
    }

    @Override
    public CellarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cellar_single_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CellarAdapter.ViewHolder holder, int position) {
        final Wine w = wineList.get(position);
        holder.color.setBackgroundColor(Color.parseColor(w.getColor().printValue()));
        holder.name.setText(w.getName());

        holder.food.setText(w.getFoodsList(activity));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WineActivity.class);


                Log.i(TAG, "onCreate: " +
                        "id " + w.getId() +
                        " name " + w.getName() +
                        " millesime " + w.getMillesime().toString() +
                        " color " + w.getColor().name() +
                        " country " + w.getCountry().name() +
                        " volume " + String.valueOf(w.getVolume()) +
                        " foods " + w.getFoodsList(activity)

                );

                intent.putExtra("id", String.valueOf(w.getId()));
                intent.putExtra("Name", w.getName());
                intent.putExtra("Millesime", String.valueOf(w.getMillesimeYear()));
                intent.putExtra("ColorEnum", w.getColor().name());
                intent.putExtra("Country", w.getCountry().name());
                intent.putExtra("Volume", String.valueOf(w.getVolume()));
                intent.putExtra("foods", w.getFoodsList(activity));

                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public void updateList(List<Wine> list) {
        wineList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return wineList.size();
    }


    public void removeItem(int position) {
        wineList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Wine wine, int position) {
        wineList.add(position, wine);
        // notify item added by position
        notifyItemInserted(position);
    }
}
