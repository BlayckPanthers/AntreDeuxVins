package com.ingesup.fabienlebon.antredeuxvins.Entities;

import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Country;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Region;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by fabienlebon on 16/03/2018.
 */

public class Wine {

    private String Name;
    private Date Millesime;
    private ColorEnum Color;
    private Country Country;
    private Region Region;
    private float Volume;
    private Food[] foods;


    public Wine(String name, Date millesime, float volume, ColorEnum color, Food[] food) {
        Name = name;
        Millesime = millesime;
        Volume = volume;
        Color = color;
        this.foods = food;
    }

    public Wine(String name, Date millesime, float volume, ColorEnum color, Food[] food, Country cntry) {
        Name = name;
        Millesime = millesime;
        Volume = volume;
        Color = color;
        this.foods = food;
        this.Country = cntry;
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

    public Country getPays() {
        return Country;
    }

    public void setPays(Country country) {
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

    public Food[] getFoods() {
        return foods;
    }

    public void setFoods(Food[] foods) {
        this.foods = foods;
    }

    public String getFoodsList(){
        String foodString = "";
        for (Food foodElement : this.foods){
            if(foodElement != null){
                foodString += foodElement.name() + " " ;
            }
        }
        return foodString;
    }

    @Override
    public String toString() {
        return Name + " " + getMillesimeYear() + " " + Color + " " + Country + " " + Region + " " + Volume + getFoodsList();
    }
}
