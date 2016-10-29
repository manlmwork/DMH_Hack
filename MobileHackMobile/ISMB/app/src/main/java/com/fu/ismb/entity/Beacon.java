package com.fu.ismb.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.estimote.sdk.Utils;
import com.fu.ismb.database.DBHelper;

import java.util.Comparator;

/**
 * Created by manlm on 9/30/2016.
 */

public class Beacon implements SqliteTable {

    private int id;

    private String uuid;

    private int major;

    private int minor;

    private double x;

    private double y;

    private double z;

    private double distance;

    private String areaLocation;

    private int areaWeight;

    @Override
    public void setValue(Cursor cursor) {
        this.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.BEACON_COLUMN_ID)));
        this.setUuid(cursor.getString(cursor.getColumnIndex(DBHelper.BEACON_COLUMN_UUID)));
        this.setMajor(cursor.getInt(cursor.getColumnIndex(DBHelper.BEACON_COLUMN_MAJOR)));
        this.setMinor(cursor.getInt(cursor.getColumnIndex(DBHelper.BEACON_COLUMN_MINOR)));
        this.setX(cursor.getInt(cursor.getColumnIndex(DBHelper.BEACON_COLUMN_X)));
        this.setY(cursor.getInt(cursor.getColumnIndex(DBHelper.BEACON_COLUMN_Y)));
        this.setZ(cursor.getInt(cursor.getColumnIndex(DBHelper.BEACON_COLUMN_Z)));
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DBHelper.BEACON_COLUMN_ID, this.getId());
        values.put(DBHelper.BEACON_COLUMN_UUID, this.getUuid());
        values.put(DBHelper.BEACON_COLUMN_MAJOR, this.getMajor());
        values.put(DBHelper.BEACON_COLUMN_MINOR, this.getMinor());
        values.put(DBHelper.BEACON_COLUMN_X, this.getX());
        values.put(DBHelper.BEACON_COLUMN_Y, this.getY());
        values.put(DBHelper.BEACON_COLUMN_Z, this.getZ());
        return values;
    }

    @Override
    public String getPrimaryValue() {
        return String.valueOf(this.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getAreaLocation() {
        return areaLocation;
    }

    public void setAreaLocation(String areaLocation) {
        this.areaLocation = areaLocation;
    }

    public int getAreaWeight() {
        return areaWeight;
    }

    public void setAreaWeight(int areaWeight) {
        this.areaWeight = areaWeight;
    }

    public static class BeaconComparator implements Comparator<Beacon> {

        @Override
        public int compare(Beacon lhs, Beacon rhs) {
            return Double.compare(lhs.getZ(), rhs.getZ());
        }
    }

    public static class BeaconComparatorbyMinor implements Comparator<Beacon> {

        @Override
        public int compare(Beacon lhs, Beacon rhs) {
            return Double.compare(lhs.getMinor(), rhs.getMinor());
        }
    }

    public static class BeaconComparatorbyDistanceAndZ implements Comparator<Beacon> {

        @Override
        public int compare(Beacon lhs, Beacon rhs) {
            int sComp = Double.compare(lhs.getZ(), rhs.getZ());
            if (sComp != 0) {
                return sComp;
            } else {
                return Double.compare(lhs.getDistance(), rhs.getDistance());
            }
        }
    }

    public static class BeaconSDKComparator implements Comparator<com.estimote.sdk.Beacon> {

        @Override
        public int compare(com.estimote.sdk.Beacon lhs, com.estimote.sdk.Beacon rhs) {
            return Integer.compare(lhs.getMinor(), rhs.getMinor());
        }
    }

}
