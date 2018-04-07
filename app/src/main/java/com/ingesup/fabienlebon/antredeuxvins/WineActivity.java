package com.ingesup.fabienlebon.antredeuxvins;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ingesup.fabienlebon.antredeuxvins.Dialogs.AddWineDialog;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Country;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.User;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Wine;
import com.ingesup.fabienlebon.antredeuxvins.Tasks.TaskService;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class WineActivity extends Activity  implements TaskService.OnAsyncRequestComplete{

    private static final String TAG = "WineActivity";
    private static final String apiURL = "https://reqres.in/api/login";
    private static final int ROUGE_ID = 1000;
    private static final int BLANC_ID = 1001;
    private static final int ROSE_ID  = 1002;

    private ArrayList<TextInputLayout> listTIL ;
    private ArrayList<RadioButton> listRadioButton ;
    private ArrayList<CheckBox> listCheckBox;
    private TextInputLayout TILname, TILmillesime, TILvolume;
    private RadioGroup type;
    private RadioButton rouge,blanc,rose;
    private CheckBox viande, fromage, crustace;
    private Button update,cancel,accept;
    private ColorEnum e;
    private Wine wine, newWine;

    private ArrayList<NameValuePair> params ;
    private String results ="";
    private JSONObject objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);

        Bundle b = getIntent().getExtras();
        String Id = b.getString("id");
        String name = b.getString("Name");
        String millesime = b.getString("Millesime");
        String color = b.getString("ColorEnum");
        String country = b.getString("Country");
        String volume = b.getString("Volume");
        String foods = b.getString("foods");
        List<String> list = new ArrayList<String>(Arrays.asList(foods.split(" ")));
        List<Food> foodList = new ArrayList<Food>();
        for(String s : list)
            foodList.add(Food.valueOf(s));

        wine = new Wine(Integer.valueOf(Id), name, new Date(Integer.valueOf(millesime)), Float.valueOf(volume), ColorEnum.valueOf(color), foodList, Country.valueOf(country));

        TILname = (TextInputLayout) findViewById(R.id.activity_name_wine);
        TILmillesime = (TextInputLayout) findViewById(R.id.activity_millesime_wine);
        TILvolume = (TextInputLayout) findViewById(R.id.activity_volume_wine);

        type = (RadioGroup) findViewById(R.id.activity_type_wine);

        rouge = (RadioButton) findViewById(R.id.rouge1);
        blanc = (RadioButton) findViewById(R.id.blanc1);
        rose = (RadioButton) findViewById(R.id.rose1);

        viande = (CheckBox) findViewById(R.id.activity_viande_wine);
        fromage = (CheckBox) findViewById(R.id.activity_fromage_wine);
        crustace = (CheckBox) findViewById(R.id.activity_crustace_wine);

        update = (Button) findViewById(R.id.activity_update_wine);
        cancel = (Button) findViewById(R.id.activity_cancel_wine);
        accept = (Button) findViewById(R.id.activity_accept_wine);

        TILname.getEditText().setText(name);
        TILmillesime.getEditText().setText(millesime);
        TILvolume.getEditText().setText(volume);

        listTIL = new ArrayList<TextInputLayout>(){{
            add(TILname);
            add(TILmillesime);
            add(TILvolume);
        }};

        listRadioButton = new ArrayList<RadioButton>(){{
            add(rouge);
            add(blanc);
            add(rose);
        }};

        listCheckBox = new ArrayList<CheckBox>(){{
            add(viande);
            add(fromage);
            add(crustace);
        }};

        rouge.setId(ROUGE_ID);
        blanc.setId(BLANC_ID);
        rose.setId(ROSE_ID);


        if(ColorEnum.valueOf(color).equals(ColorEnum.Rouge))
            rouge.setChecked(true);
        else if(ColorEnum.valueOf(color).equals(ColorEnum.Blanc))
            blanc.setChecked(true);
        else
            rose.setChecked(true);

        if(foodList.contains(Food.Viande))
            viande.setChecked(true);
        if(foodList.contains(Food.Fromage))
            fromage.setChecked(true);
        if(foodList.contains(Food.Crustace))
            crustace.setChecked(true);



        Log.i(TAG, "onCreate: " +
            "id " + Id +
                " name " + name +
                " millesime " + millesime +
                " color " + color +
                " country " + country +
                " volume " + volume +
                " foods " + foods

        );

        setDisableAll();


    }

    public void setEnableAll(){
        for(TextInputLayout til : listTIL)
            til.setEnabled(true);

        for(RadioButton radio : listRadioButton)
            radio.setEnabled(true);

        for(CheckBox check : listCheckBox)
            check.setEnabled(true);
    }

    public void setDisableAll(){
        for(TextInputLayout til : listTIL)
            til.setEnabled(false);

        for(RadioButton radio : listRadioButton)
            radio.setEnabled(false);

        for(CheckBox check : listCheckBox)
            check.setEnabled(false);
    }

    public void cancelUpdateWine(View view) {
        setDisableAll();
        update.setVisibility(View.VISIBLE);
        accept.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
    }

    public void enableFieldsUpdateWine(View view) {
        setEnableAll();
        update.setVisibility(View.GONE);
        accept.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
    }

    public void updateWine(View view) {
        setDisableAll();
        update.setVisibility(View.VISIBLE);
        accept.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);

        if(!TILname.getEditText().getText().toString().equals("") && !TILmillesime.getEditText().getText().toString().equals("")
                && !TILvolume.getEditText().getText().toString().equals("")) {
            if(Integer.valueOf(TILmillesime.getEditText().getText().toString()) > 1900
                    && Integer.valueOf(TILmillesime.getEditText().getText().toString()) < 2018) {
                if(type.getCheckedRadioButtonId() > 0) {
                    switch(type.getCheckedRadioButtonId()) {
                        case ROUGE_ID:
                            e = ColorEnum.Rouge;
                            break;
                        case BLANC_ID:
                            e = ColorEnum.Blanc;
                            break;
                        case ROSE_ID:
                            e = ColorEnum.Rose;
                            break;
                    }
                    if(viande.isChecked() || fromage.isChecked() || crustace.isChecked()) {
                        List<Food> foodList = new ArrayList<Food>();
                        if(viande.isChecked())
                            foodList.add(Food.Viande);
                        if(fromage.isChecked())
                            foodList.add(Food.Fromage);
                        if(crustace.isChecked())
                            foodList.add(Food.Crustace);


                        newWine = new Wine(13,TILname.getEditText().getText().toString(),
                                new Date(Integer.valueOf(TILmillesime.getEditText().getText().toString())),
                                Float.valueOf(TILvolume.getEditText().getText().toString()),
                                e,
                                foodList, Country.France);

                        params = getParams();
                        TaskService getPosts = new TaskService(this, "POST", params);
                        getPosts.execute(apiURL);

                    }
                    else{
                        Toast.makeText(this,"Choisissez au moins un accompagnement",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this,"Choisissez un type de vin",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                TILmillesime.setError("Entrez une date comprise entre 1900 et 2018");
            }

        }
        else
        {
            Toast.makeText(this,"Veuillez remplir les champs",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }



    private ArrayList<NameValuePair> getParams() {
        // define and ArrayList whose elements are of type NameValuePair
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id",String.valueOf(wine.getId())));
        params.add(new BasicNameValuePair("name", wine.getName()));
        params.add(new BasicNameValuePair("millesime", String.valueOf(wine.getMillesimeYear())));
        params.add(new BasicNameValuePair("color", wine.getColor().name()));
        params.add(new BasicNameValuePair("country", wine.getCountry().name()));
        params.add(new BasicNameValuePair("volume", String.valueOf(wine.getVolume())));
        params.add(new BasicNameValuePair("foods", wine.getFoodsList()));

        return params;
    }

    @Override
    public void asyncResponse(String response, String flag) {

        try {
            objects = new JSONObject(response);

            if(objects.has("token")){
                //TODO some verif ... OK
            }
            else {
                Toast.makeText(this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
