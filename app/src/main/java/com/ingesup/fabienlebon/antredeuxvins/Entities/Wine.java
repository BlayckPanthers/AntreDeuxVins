package com.ingesup.fabienlebon.antredeuxvins.Entities;

import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Country;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Food;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Region;

import java.util.Date;

/**
 * Created by fabienlebon on 16/03/2018.
 */

public class Wine {

    private String Name;
    private String Type;
    private Date Millesime;
    private ColorEnum Color;
    private Country country;
    private Region region;
    private int Volume;
    private Food[] foods;


    public Wine(String name, String type, Date millesime, int volume, ColorEnum color, Food[] food) {
        Name = name;
        Type = type;
        Millesime = millesime;
        Volume = volume;
        Color = color;
        this.foods = food;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Date getMillesime() {
        return Millesime;
    }

    public void setMillesime(Date millesime) {
        Millesime = millesime;
    }

    public ColorEnum getColor() {
        return Color;
    }

    public void setColor(ColorEnum color) {
        Color = color;
    }

    public Country getPays() {
        return country;
    }

    public void setPays(Country country) {
        this.country = country;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getVolume() {
        return Volume;
    }

    public void setVolume(int volume) {
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
            foodString += foodElement.name() + " " ;
        }
        return foodString;
    }
}
