package com.fu.ismb.fragment;

/**
 * Created by PhucNT on 10/19/2016.
 */

public class AreaName {
    private String name;
    private double x;
    private double y;

    public AreaName(String name,double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
