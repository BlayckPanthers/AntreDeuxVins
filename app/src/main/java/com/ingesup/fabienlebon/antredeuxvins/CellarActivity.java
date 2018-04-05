package com.ingesup.fabienlebon.antredeuxvins;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ingesup.fabienlebon.antredeuxvins.Adapters.CellarAdapter;
import com.ingesup.fabienlebon.antredeuxvins.Dialogs.AddWineDialog;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Country;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Wine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CellarActivity extends FragmentActivity implements AddWineDialog.addWineDialogListener, OnRefreshListener{

    private static String TAG = "CellarActivity";

    private ListView wineListView;
    private EditText searchEditText;
    private CellarAdapter cellarAdapter;
    private List<Wine> winesList ;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new AddWineDialog();
                newFragment.show(getSupportFragmentManager(),"addWine");

            }
        });

        wineListView = (ListView) findViewById(R.id.cellar_wineList);
        searchEditText = (EditText) findViewById(R.id.cellar_search);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        winesList = new ArrayList<>();

        List<Wine> wines = genererWines();

        cellarAdapter = new CellarAdapter(CellarActivity.this, wines);
        wineListView.setAdapter(cellarAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (CellarActivity.this).cellarAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        wineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // When clicked, show a toast
                Toast.makeText(getApplicationContext(),
                        "test" + position  + " id : " + id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Wine> genererWines() {

        Food[] foods = new Food[]{Food.Fromage, Food.Viande, Food.Crustace};
        Food[] foodc = new Food[]{Food.Crustace};
        Food[] foodd = new Food[]{Food.Fromage};
        Food[] fooda = new Food[]{Food.Viande, Food.Crustace};
        Food[] foodb = new Food[]{Food.Viande};
        winesList.add(new Wine("Saint-Emillion",new Date(1995), 1, ColorEnum.Rouge, foodc, Country.France));
        winesList.add(new Wine("Cabernet",new Date(2000), 1, ColorEnum.Rose,fooda));
        winesList.add(new Wine("Mouton-cadet",new Date(1890), 1, ColorEnum.Blanc,foodb));
        winesList.add(new Wine("Saint-Estephe",new Date(2005), 1, ColorEnum.Rouge,foods));
        winesList.add(new Wine("Chateau-neuf-du-pape",new Date(2016), 1, ColorEnum.Rouge,foodd));
        winesList.add(new Wine("Beaujolais",new Date(2010), 1, ColorEnum.Blanc,fooda));
        winesList.add(new Wine("Saint-Emillion",new Date(1995), 1, ColorEnum.Rouge, foods));
        winesList.add(new Wine("Cabernet",new Date(2000), 1, ColorEnum.Rose,fooda));
        winesList.add(new Wine("Mouton-cadet",new Date(1890), 1, ColorEnum.Blanc,foodb));
        winesList.add(new Wine("Saint-Estephe",new Date(2005), 1, ColorEnum.Rouge,foods));
        winesList.add(new Wine("Chateau-neuf-du-pape",new Date(2016), 1, ColorEnum.Rouge,foods));
        winesList.add(new Wine("Beaujolais",new Date(2010), 1, ColorEnum.Rouge,fooda));


        return winesList;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Wine n) {
        Log.i(TAG, "onDialogPositiveClick: " + n.toString());
        winesList.add(n);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        cellarAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);


    }
}
