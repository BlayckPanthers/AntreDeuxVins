package com.ingesup.fabienlebon.antredeuxvins.Entities.Enum;

import android.util.Log;

/**
 * Created by fabienlebon on 16/03/2018.
 */

public enum ColorEnum {
    Rouge("#9c27b0"), Blanc("#f5f5f5"), Rose("#e5ac80");

    private String value;

    ColorEnum(){}

    private ColorEnum(String i){
        this.value = i;
    }

    public String printValue(){
        return this.value;
    }
}
