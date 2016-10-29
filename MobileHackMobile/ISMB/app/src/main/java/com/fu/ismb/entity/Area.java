package com.fu.ismb.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.fu.ismb.database.DBHelper;

/**
 * Created by PhucNT on 10/19/2016.
 */

public class Area implements SqliteTable{
    private int id;

    private String name;

    private String location;

    private int weight;

    private int beaconMinor1;

    private int beaconMinor2;

    private int beaconMinor3;

    private int beaconMinor4;

    @Override
    public void setValue(Cursor cursor) {
        this.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.AREA_COLUMN_AREA_ID)));
        this.setName(cursor.getString(cursor.getColumnIndex(DBHelper.AREA_COLUMN_AREA_NAME)));
        this.setLocation(cursor.getString(cursor.getColumnIndex(DBHelper.AREA_COLUMN_AREA_LOCATION)));
        this.setWeight(cursor.getInt(cursor.getColumnIndex(DBHelper.AREA_COLUMN_AREA_WEIGHT)));
        this.setBeaconMinor1(cursor.getInt(cursor.getColumnIndex(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_1)));
        this.setBeaconMinor2(cursor.getInt(cursor.getColumnIndex(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_2)));
        this.setBeaconMinor3(cursor.getInt(cursor.getColumnIndex(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_3)));
        this.setBeaconMinor4(cursor.getInt(cursor.getColumnIndex(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_4)));
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DBHelper.AREA_COLUMN_AREA_ID, this.getId());
        values.put(DBHelper.AREA_COLUMN_AREA_NAME, this.getName());
        values.put(DBHelper.AREA_COLUMN_AREA_LOCATION, this.getLocation());
        values.put(DBHelper.AREA_COLUMN_AREA_WEIGHT, this.getWeight());
        values.put(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_1, this.getBeaconMinor1());
        values.put(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_2, this.getBeaconMinor2());
        values.put(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_3, this.getBeaconMinor3());
        values.put(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_4, this.getBeaconMinor4());
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBeaconMinor1() {
        return beaconMinor1;
    }

    public void setBeaconMinor1(int beaconMinor1) {
        this.beaconMinor1 = beaconMinor1;
    }

    public int getBeaconMinor2() {
        return beaconMinor2;
    }

    public void setBeaconMinor2(int beaconMinor2) {
        this.beaconMinor2 = beaconMinor2;
    }

    public int getBeaconMinor3() {
        return beaconMinor3;
    }

    public void setBeaconMinor3(int beaconMinor3) {
        this.beaconMinor3 = beaconMinor3;
    }

    public int getBeaconMinor4() {
        return beaconMinor4;
    }

    public void setBeaconMinor4(int beaconMinor4) {
        this.beaconMinor4 = beaconMinor4;
    }
}
