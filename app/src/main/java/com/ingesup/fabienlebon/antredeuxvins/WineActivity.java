package com.ingesup.fabienlebon.antredeuxvins;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Country;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Wine;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WineActivity extends Activity {

    private static final String TAG = "WineActivity";

    private ArrayList<TextInputLayout> listTIL ;
    private ArrayList<RadioButton> listRadioButton ;
    private ArrayList<CheckBox> listCheckBox;
    private TextInputLayout TILname, TILmillesime, TILvolume;
    private RadioButton rouge,blanc,rose;
    private CheckBox viande, fromage, crustace;
    private Button update,cancel,accept;

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

        Wine n = new Wine(Integer.valueOf(Id), name, new Date(Integer.valueOf(millesime)), Float.valueOf(volume), ColorEnum.valueOf(color), foodList, Country.valueOf(country));

        TILname = (TextInputLayout) findViewById(R.id.activity_name_wine);
        TILmillesime = (TextInputLayout) findViewById(R.id.activity_millesime_wine);
        TILvolume = (TextInputLayout) findViewById(R.id.activity_volume_wine);

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
    }
}
