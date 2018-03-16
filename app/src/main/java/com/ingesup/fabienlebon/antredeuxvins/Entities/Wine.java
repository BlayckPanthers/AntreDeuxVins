package com.ingesup.fabienlebon.antredeuxvins.Entities;

import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.ColorEnum;
import com.ingesup.fabienlebon.antredeuxvins.Entities.Enum.Pays;
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
    private Pays pays;
    private Region region;
    private int Volume;


    public Wine(String name, String type, Date millesime, int volume) {
        Name = name;
        Type = type;
        Millesime = millesime;
        Volume = volume;
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

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
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
}
