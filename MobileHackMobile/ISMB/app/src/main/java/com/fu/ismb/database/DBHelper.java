package com.fu.ismb.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by manlm on 9/21/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    /**
     * b
     * Database
     */
    public static final String DATABASE_NAME = "ismb.db";

    /**
     * Table Account
     */

    /**
     * Table Facebook Account
     */
    public static final String FB_COLUMN_TOKEN = "token";

    public static final String FB_COLUMN_APP_ID = "appFbId";

    public static final String FB_COLUMN_BOT_ID = "botFbId";

    public static final String FB_COLUMN_FIRST_NAME = "firstName";

    public static final String FB_COLUMN_LAST_NAME = "lastName";

    public static final String FB_COLUMN_PHONE = "phone";

    public static final String FB_TABLE_NAME = "facebook";


    /**
     * Table Product
     */
    public static final String PRODUCT_TABLE_NAME = "product";

    public static final String PRODUCT_COLUMN_ID = "id";

    public static final String PRODUCT_COLUMN_NAME = "name";

    public static final String PRODUCT_COLUMN_AREA_NAME = "areaName";

    public static final String PRODUCT_COLUMN_AREA_LOCATION = "areaLocation";

    public static final String PRODUCT_COLUMN_AREA_WEIGHT = "areaWeight";

    public static final String PRODUCT_COLUMN_BEACON_MINOR_1 = "beaconMinor1";

    public static final String PRODUCT_COLUMN_BEACON_MINOR_2 = "beaconMinor2";

    public static final String PRODUCT_COLUMN_BEACON_MINOR_3 = "beaconMinor3";

    public static final String PRODUCT_COLUMN_BEACON_MINOR_4 = "beaconMinor4";

    /**
     * Table Beacon
     */
    public static final String BEACON_TABLE_NAME = "beacon";

    public static final String BEACON_COLUMN_ID = "id";

    public static final String BEACON_COLUMN_UUID = "uuid";

    public static final String BEACON_COLUMN_MAJOR = "major";

    public static final String BEACON_COLUMN_MINOR = "minor";

    public static final String BEACON_COLUMN_X = "x";

    public static final String BEACON_COLUMN_Y = "y";

    public static final String BEACON_COLUMN_Z = "z";

    /**
     * Table Cart
     */
    public static final String CART_TABLE_NAME = "cart";

    public static final String CART_COLUMN_PRODUCT_ID = "productId";

    public static final String CART_COLUMN_IS_FOUND = "isFound";

    public static final String CART_COLUMN_LAST_UPDATE = "lastUpdate";

    /**
     * Table Area
     */
    public static final String AREA_TABLE_NAME = "area";

    public static final String AREA_COLUMN_AREA_ID = "id";

    public static final String AREA_COLUMN_AREA_NAME = "name";

    public static final String AREA_COLUMN_AREA_LOCATION = "location";

    public static final String AREA_COLUMN_AREA_WEIGHT = "weight";

    public static final String AREA_COLUMN_AREA_BEACON_MINOR_1 = "beaconMinor1";

    public static final String AREA_COLUMN_AREA_BEACON_MINOR_2 = "beaconMinor2";

    public static final String AREA_COLUMN_AREA_BEACON_MINOR_3 = "beaconMinor3";

    public static final String AREA_COLUMN_AREA_BEACON_MINOR_4 = "beaconMinor4";

    /**
     * Table Sync
     */

    public static final String SYNC_TABLE_NAME = "sync";

    public static final String SYNC_COLUMN_ID = "id";

    public static final String SYNC_COLUMN_LAST_SYNC = "lastSync";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + FB_TABLE_NAME
                        + " ("
                        + FB_COLUMN_TOKEN + " text primary key, "
                        + FB_COLUMN_APP_ID + " text, "
                        + FB_COLUMN_BOT_ID + " text, "
                        + FB_COLUMN_FIRST_NAME + " text, "
                        + FB_COLUMN_LAST_NAME + " text, "
                        + FB_COLUMN_PHONE + " text"
                        + ")"
        );
        db.execSQL(
                "create table " + PRODUCT_TABLE_NAME
                        + " ("
                        + PRODUCT_COLUMN_ID + " integer primary key, "
                        + PRODUCT_COLUMN_NAME + " text not null, "
                        + PRODUCT_COLUMN_AREA_NAME + " text not null, "
                        + PRODUCT_COLUMN_AREA_LOCATION + " text not null, "
                        + PRODUCT_COLUMN_AREA_WEIGHT + " integer not null, "
                        + PRODUCT_COLUMN_BEACON_MINOR_1 + " integer not null, "
                        + PRODUCT_COLUMN_BEACON_MINOR_2 + " integer not null, "
                        + PRODUCT_COLUMN_BEACON_MINOR_3 + " integer not null, "
                        + PRODUCT_COLUMN_BEACON_MINOR_4 + " integer not null "
                        + ")"
        );
        db.execSQL(
                "create table " + BEACON_TABLE_NAME
                        + " ("
                        + BEACON_COLUMN_ID + " integer primary key, "
                        + BEACON_COLUMN_UUID + " text not null, "
                        + BEACON_COLUMN_MAJOR + " integer not null, "
                        + BEACON_COLUMN_MINOR + " integer not null, "
                        + BEACON_COLUMN_X + " double not null, "
                        + BEACON_COLUMN_Y + " double not null, "
                        + BEACON_COLUMN_Z + " double not null "
                        + ")"
        );
        db.execSQL(
                "create table " + CART_TABLE_NAME
                        + " ("
                        + CART_COLUMN_PRODUCT_ID + " integer primary key, "
                        + CART_COLUMN_LAST_UPDATE + " long not null,"
                        + CART_COLUMN_IS_FOUND + " integer not null"
                        + ")"
        );
        db.execSQL(
                "create table " + AREA_TABLE_NAME
                        + " ("
                        + AREA_COLUMN_AREA_ID + " integer primary key, "
                        + AREA_COLUMN_AREA_NAME + " text not null, "
                        + AREA_COLUMN_AREA_LOCATION + " text not null, "
                        + AREA_COLUMN_AREA_WEIGHT + " integer not null, "
                        + AREA_COLUMN_AREA_BEACON_MINOR_1 + " integer not null, "
                        + AREA_COLUMN_AREA_BEACON_MINOR_2 + " integer not null, "
                        + AREA_COLUMN_AREA_BEACON_MINOR_3 + " integer not null, "
                        + AREA_COLUMN_AREA_BEACON_MINOR_4 + " integer not null "
                        + ")"
        );
        db.execSQL(
                "create table " + SYNC_TABLE_NAME
                        + " ("
                        + SYNC_COLUMN_ID + " integer primary key, "
                        + SYNC_COLUMN_LAST_SYNC + " long not null "
                        + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FB_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BEACON_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AREA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SYNC_TABLE_NAME);
        onCreate(db);
    }
}
