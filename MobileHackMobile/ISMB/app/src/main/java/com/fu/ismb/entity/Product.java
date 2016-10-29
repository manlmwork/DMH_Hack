package com.fu.ismb.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.fu.ismb.database.DBHelper;

/**
 * Created by manlm on 9/30/2016.
 */

public class Product implements SqliteTable {

    private int id;

    private String name;

    private String areaName;

    private String areaLocation;

    private int areaWeight;

    private int beaconMinor1;

    private int beaconMinor2;

    private int beaconMinor3;

    private int beaconMinor4;

    @Override
    public void setValue(Cursor cursor) {
        this.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.PRODUCT_COLUMN_ID)));
        this.setName(cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCT_COLUMN_NAME)));
        this.setAreaName(cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCT_COLUMN_AREA_NAME)));
        this.setAreaLocation(cursor.getString(cursor.getColumnIndex(DBHelper.PRODUCT_COLUMN_AREA_LOCATION)));
        this.setAreaWeight(cursor.getInt(cursor.getColumnIndex(DBHelper.PRODUCT_COLUMN_AREA_WEIGHT)));
        this.setBeaconMinor1(cursor.getInt(cursor.getColumnIndex(DBHelper.PRODUCT_COLUMN_BEACON_MINOR_1)));
        this.setBeaconMinor2(cursor.getInt(cursor.getColumnIndex(DBHelper.PRODUCT_COLUMN_BEACON_MINOR_2)));
        this.setBeaconMinor3(cursor.getInt(cursor.getColumnIndex(DBHelper.PRODUCT_COLUMN_BEACON_MINOR_3)));
        this.setBeaconMinor4(cursor.getInt(cursor.getColumnIndex(DBHelper.PRODUCT_COLUMN_BEACON_MINOR_4)));
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DBHelper.PRODUCT_COLUMN_ID, this.getId());
        values.put(DBHelper.PRODUCT_COLUMN_NAME, this.getName());
        values.put(DBHelper.PRODUCT_COLUMN_AREA_NAME, this.getAreaName());
        values.put(DBHelper.PRODUCT_COLUMN_AREA_LOCATION, this.getAreaLocation());
        values.put(DBHelper.PRODUCT_COLUMN_AREA_WEIGHT, this.getAreaWeight());
        values.put(DBHelper.PRODUCT_COLUMN_BEACON_MINOR_1, this.getBeaconMinor1());
        values.put(DBHelper.PRODUCT_COLUMN_BEACON_MINOR_2, this.getBeaconMinor2());
        values.put(DBHelper.PRODUCT_COLUMN_BEACON_MINOR_3, this.getBeaconMinor3());
        values.put(DBHelper.PRODUCT_COLUMN_BEACON_MINOR_4, this.getBeaconMinor4());
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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
