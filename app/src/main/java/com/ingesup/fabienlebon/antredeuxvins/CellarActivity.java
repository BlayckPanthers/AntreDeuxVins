package com.ingesup.fabienlebon.antredeuxvins;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.ingesup.fabienlebon.antredeuxvins.Adapters.CellarAdapter;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Wine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CellarActivity extends AppCompatActivity {

    private ListView wineListView;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        wineListView = (ListView) findViewById(R.id.cellar_wineList);
        searchEditText = (EditText) findViewById(R.id.cellar_search);

        List<Wine> wines = genererWines();
        CellarAdapter cellarAdapter = new CellarAdapter(CellarActivity.this, wines);
        wineListView.setAdapter(cellarAdapter);

    }

    private List<Wine> genererWines() {
        List<Wine> wines = new ArrayList<>();
        Food[] foods = new Food[]{Food.Fromage, Food.Viande, Food.Crustace};
        Food[] fooda = new Food[]{Food.Viande, Food.Crustace};
        Food[] foodb = new Food[]{Food.Viande};
        wines.add(new Wine("Saint-Emillion","type",new Date(1995), 1, ColorEnum.Rouge, foods));
        wines.add(new Wine("Cabernet","type",new Date(2000), 1, ColorEnum.Rose,fooda));
        wines.add(new Wine("Mouton-cadet","type",new Date(1890), 1, ColorEnum.Blanc,foodb));
        wines.add(new Wine("Saint-Estephe","type",new Date(2005), 1, ColorEnum.Rouge,foods));
        wines.add(new Wine("Chateau-neuf-du-pape","type",new Date(2016), 1, ColorEnum.Rouge,foods));
        wines.add(new Wine("Beaujolais","type",new Date(2010), 1, ColorEnum.Blanc,fooda));
        wines.add(new Wine("Saint-Emillion","type",new Date(1995), 1, ColorEnum.Rouge, foods));
        wines.add(new Wine("Cabernet","type",new Date(2000), 1, ColorEnum.Rose,fooda));
        wines.add(new Wine("Mouton-cadet","type",new Date(1890), 1, ColorEnum.Blanc,foodb));
        wines.add(new Wine("Saint-Estephe","type",new Date(2005), 1, ColorEnum.Rouge,foods));
        wines.add(new Wine("Chateau-neuf-du-pape","type",new Date(2016), 1, ColorEnum.Rouge,foods));
        wines.add(new Wine("Beaujolais","type",new Date(2010), 1, ColorEnum.Blanc,fooda));


        return wines;
    }

}
