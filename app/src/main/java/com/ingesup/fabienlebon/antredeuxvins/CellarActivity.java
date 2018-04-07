package com.ingesup.fabienlebon.antredeuxvins;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.ingesup.fabienlebon.antredeuxvins.Tasks.TaskService;
import com.ingesup.fabienlebon.antredeuxvins.Tools.GlobalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class CellarActivity extends FragmentActivity implements AddWineDialog.addWineDialogListener, OnRefreshListener, TaskService.OnAsyncRequestComplete{

    private static final String TAG = "CellarActivity";
    private static final String apiURL = "https://reqres.in/api/login";

    private ListView wineListView;
    private EditText searchEditText;
    private CellarAdapter cellarAdapter;
    private List<Wine> winesList ;

    private SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<NameValuePair> params ;
    private String results ="";
    private JSONObject objects;

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

        Log.i(TAG, "onCreate: Globaldata" + GlobalData.getInstance().getUserDao().selectionnerTout().toString());


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
                Intent intent = new Intent(getApplicationContext(), WineActivity.class);
                Wine n = (Wine) wineListView.getItemAtPosition(position);

                Log.i(TAG, "onCreate: " +
                        "id " + n.getId() +
                        " name " + n.getName() +
                        " millesime " + n.getMillesime().toString() +
                        " color " +  n.getColor().name() +
                        " country " + n.getCountry().name() +
                        " volume " + String.valueOf(n.getVolume()) +
                        " foods " + n.getFoodsList()

                );

                intent.putExtra("id", String.valueOf(n.getId()));
                intent.putExtra("Name", n.getName());
                intent.putExtra("Millesime", String.valueOf(n.getMillesimeYear()));
                intent.putExtra("ColorEnum", n.getColor().name());
                intent.putExtra("Country", n.getCountry().name());
                intent.putExtra("Volume", String.valueOf(n.getVolume()));
                intent.putExtra("foods",n.getFoodsList());

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public void ShowUserInfo(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private List<Wine> genererWines() {

        Food[] foods = new Food[]{Food.Fromage, Food.Viande, Food.Crustace};
        Food[] foodc = new Food[]{Food.Crustace};
        Food[] foodd = new Food[]{Food.Fromage};
        Food[] fooda = new Food[]{Food.Viande, Food.Crustace};
        Food[] foodb = new Food[]{Food.Viande};
        winesList.add(new Wine(1,"Saint-Emillion",new Date(1995), 1, ColorEnum.Rouge, Arrays.asList(foodc), Country.France));
        winesList.add(new Wine(2,"Cabernet",new Date(2000), 1, ColorEnum.Rose, Arrays.asList(fooda), Country.France));
        winesList.add(new Wine(3,"Mouton-cadet",new Date(1890), 1, ColorEnum.Blanc,Arrays.asList(foodb), Country.France));
        winesList.add(new Wine(4,"Saint-Estephe",new Date(2005), 1, ColorEnum.Rouge,Arrays.asList(foods), Country.France));
        winesList.add(new Wine(5,"Chateau-neuf-du-pape",new Date(2016), 1, ColorEnum.Rouge,Arrays.asList(foodd), Country.France));
        winesList.add(new Wine(6,"Beaujolais",new Date(2010), 1, ColorEnum.Blanc,Arrays.asList(fooda), Country.France));
        winesList.add(new Wine(7,"Saint-Emillion",new Date(1995), 1, ColorEnum.Rouge, Arrays.asList(foods), Country.France));
        winesList.add(new Wine(8,"Cabernet",new Date(2000), 1, ColorEnum.Rose,Arrays.asList(fooda), Country.France));
        winesList.add(new Wine(9,"Mouton-cadet",new Date(1890), 1, ColorEnum.Blanc,Arrays.asList(foodb), Country.France));
        winesList.add(new Wine(10,"Saint-Estephe",new Date(2005), 1, ColorEnum.Rouge,Arrays.asList(foodd), Country.France));
        winesList.add(new Wine(11,"Chateau-neuf-du-pape",new Date(2016), 1, ColorEnum.Rouge,Arrays.asList(foodc), Country.France));
        winesList.add(new Wine(12,"Beaujolais",new Date(2010), 1, ColorEnum.Rouge,Arrays.asList(fooda), Country.France));


        return winesList;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Wine n) {
        Log.i(TAG, "onDialogPositiveClick: " + n.toString());
        // winesList.add(n);
        params = getParams(n);
        TaskService getPosts = new TaskService(this, "POST", params,"POST_WINE");
        getPosts.execute(apiURL);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onRefresh() {
        //TODO getWines
        swipeRefreshLayout.setRefreshing(true);
        /*
        TaskService getPosts = new TaskService(this, "GET", params,"GET_WINES");
        getPosts.execute(apiURL);

         */

        TaskService getPosts = new TaskService(this, "GET", params,"GET_WINES");
        getPosts.execute("https://reqres.in/api/unknown");
        cellarAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void asyncResponse(String response, String label) {
        switch(label){
            //TODO get list of wine
            case "GET_WINES" :
                Log.i(TAG, "asyncResponse: GET_WINES" );
                Log.i(TAG, "asyncResponse: WINELIST" + response.toString());

                /*
                JSONArray list = null;
                try {
                    list = new JSONArray(response.toString());
                    for(int i = 0 ; i < list.length() ; i++){
                        int id=Integer.parseInt(list.getJSONObject(i).getString("id"));
                        String name= list.getJSONObject(i).getString("name");
                        String millesime=list.getJSONObject(i).getString("millesime");
                        String color = list.getJSONObject(i).getString("ColorEnum");
                        String country = list.getJSONObject(i).getString("Country");
                        String volume = list.getJSONObject(i).getString("Volume");
                        String foods = list.getJSONObject(i).getString("foods");
                        List<String> listF = new ArrayList<String>(Arrays.asList(foods.split(" ")));
                        List<Food> foodList = new ArrayList<Food>();
                        for(String s : listF)
                            foodList.add(Food.valueOf(s));
                        Wine wine = new Wine(Integer.valueOf(id), name, new Date(Integer.valueOf(millesime)), Float.valueOf(volume), ColorEnum.valueOf(color), foodList, Country.valueOf(country));
                        winesList.add(wine);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */

                break;
            //TODO post wine
            case "POST_WINE" :
                Log.i(TAG, "asyncResponse: POST_WINE" );
                break;

        }
    }


    public ArrayList<NameValuePair> getParams(Wine wine){
        // define and ArrayList whose elements are of type NameValuePair
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", wine.getName()));
        params.add(new BasicNameValuePair("millesime", String.valueOf(wine.getMillesimeYear())));
        params.add(new BasicNameValuePair("color", wine.getColor().name()));
        params.add(new BasicNameValuePair("country", wine.getCountry().name()));
        params.add(new BasicNameValuePair("volume", String.valueOf(wine.getVolume())));
        params.add(new BasicNameValuePair("foods", wine.getFoodsList()));
        return params;
    }

    @Override
    public void onBackPressed() {
        // Disable backbutton (to Login Activity) in cellarAcitivty
    }


}
