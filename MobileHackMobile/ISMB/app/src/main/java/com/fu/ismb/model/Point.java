package com.fu.ismb.model;

/**
 * Created by manlm on 9/28/2016.
 */

public class Point implements Comparable<Point> {

    private double x;

    private double y;

    private double z;

    public Point() {
        // default constructor
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public int compareTo(Point o) {
        if (this.getX() < o.getX()) {
            return -1;
        }

        if (this.getX() > o.getX()) {
            return 1;
        }

        if (this.getY() < o.getY()) {
            return -1;
        }

        if (this.getY() > o.getY()) {
            return 1;
        }

        if (this.getZ() < o.getZ()) {
            return -1;
        }

        if (this.getZ() > o.getZ()) {
            return 1;
        }

        return 0;
    }
}
