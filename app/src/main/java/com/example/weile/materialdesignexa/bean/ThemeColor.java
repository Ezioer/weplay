package com.example.weile.materialdesignexa.bean;

/**
 * Created by weile on 2016/12/16.
 */
public class ThemeColor {
    private int color;
    private String themename;
    public ThemeColor(int color,String themename){
        this.color=color;
        this.themename=themename;
    }
    public int getColor(){
        return color;
    }
    public String getThemename(){
        return themename;
    }
}
