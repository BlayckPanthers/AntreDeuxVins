package com.ingesup.fabienlebon.antredeuxvins.Entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Country;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Region;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by fabienlebon on 16/03/2018.
 */

public class Wine {

    private int id;
    private String Name;
    private Date Millesime;
    private ColorEnum Color;
    private Country Country;
    private Region Region;
    private float Volume;
    private List<Food> foods;


    public Wine(int id, String name, Date millesime, float volume, ColorEnum color, List<Food> food) {
        id = id;
        Name = name;
        Millesime = millesime;
        Volume = volume;
        Color = color;
        this.foods = food;
    }

    public Wine(int id, String name, Date millesime, float volume, ColorEnum color,List<Food> food, Country cntry) {
        id = id;
        Name = name;
        Millesime = millesime;
        Volume = volume;
        Color = color;
        this.foods = food;
        this.Country = cntry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getMillesime() {
        return Millesime;
    }

    public void setMillesime(Date millesime) {
        Millesime = millesime;
    }

    public int getMillesimeYear(){
        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(Millesime));
        return year;
    }

    public ColorEnum getColor() {
        return Color;
    }

    public void setColor(ColorEnum color) {
        Color = color;
    }

    public Country getCountry() {
        return Country;
    }

    public void setPays (Country country) {
        this.Country = country;
    }

    public Region getRegion() {
        return Region;
    }

    public void setRegion(Region region) {
        this.Region = region;
    }

    public float getVolume() {
        return Volume;
    }

    public void setVolume(float volume) {
        Volume = volume;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public String getFoodsList(){
        String foodString = "";
        for (Food foodElement : this.foods){
            if(foodElement != null)
                foodString += foodElement.name() + " " ;

        }
        return foodString;
    }

    protected Wine(Parcel in) {
        id = in.readInt();
        Name=in.readString();
        Millesime=(java.util.Date) in.readSerializable();
        Color= ColorEnum.valueOf(in.readString());
        Volume=in.readFloat();
        List<String> foodStrings = new ArrayList<String>();
        in.readList(foodStrings, null);
        for (String foodElement : foodStrings) {
            foods.add(Food.valueOf(foodElement));
        }
    }

    @Override
    public String toString() {
        return Name +  " " + Color + " " + getFoodsList();
    }


}
