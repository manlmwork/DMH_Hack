package com.fu.ismb.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.fu.ismb.database.DBHelper;

/**
 * Created by manlm on 10/2/2016.
 */

public class Cart implements SqliteTable {

    private int productId;

    private int isFound;

    private long lastUpdate;

    @Override
    public void setValue(Cursor cursor) {
        this.setProductId(cursor.getInt(cursor.getColumnIndex(DBHelper.CART_COLUMN_PRODUCT_ID)));
        this.setIsFound(cursor.getInt(cursor.getColumnIndex(DBHelper.CART_COLUMN_IS_FOUND)));
        this.setLastUpdate(cursor.getLong(cursor.getColumnIndex(DBHelper.CART_COLUMN_LAST_UPDATE)));
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DBHelper.CART_COLUMN_PRODUCT_ID, this.getProductId());
        values.put(DBHelper.CART_COLUMN_IS_FOUND, this.getIsFound());
        values.put(DBHelper.CART_COLUMN_LAST_UPDATE, this.getLastUpdate());
        return values;
    }

    @Override
    public String getPrimaryValue() {
        return String.valueOf(this.getProductId());
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getIsFound() {
        return isFound;
    }

    public void setIsFound(int isFound) {
        this.isFound = isFound;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
