package com.ingesup.fabienlebon.antredeuxvins.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Wine;

import com.ingesup.fabienlebon.antredeuxvins.R;

import java.util.List;

/**
 * Created by fabienlebon on 02/04/2018.
 */

public class CellarAdapter extends ArrayAdapter<Wine> {

    public CellarAdapter(Context context, List<Wine> wines){
        super(context, 0, wines);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cellar_single_layout,parent, false);
        }

        CellarViewHolder viewHolder = (CellarViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CellarViewHolder();
            viewHolder.color = (ImageView) convertView.findViewById(R.id.list_view_single_wine_color);
            viewHolder.name  = (TextView) convertView.findViewById(R.id.list_view_single_wine_name);
            viewHolder.food  = (TextView) convertView.findViewById(R.id.list_view_single_wine_food);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Wine> wines
        final Wine wine = getItem(position);

        // Remplissage de la vue.
        viewHolder.color.setBackgroundColor(Color.parseColor(wine.getColor().printValue()));
        viewHolder.name.setText(wine.getName());
        viewHolder.food.setText(wine.getFoodsList().replace(" ",",").substring(0,wine.getFoodsList().length()-1));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : Dialog Wine Informations to toggle
            }
        });

        return convertView;
    }

    private class CellarViewHolder {
       public ImageView color;
       public TextView  name;
       public TextView  food;
    }

}
