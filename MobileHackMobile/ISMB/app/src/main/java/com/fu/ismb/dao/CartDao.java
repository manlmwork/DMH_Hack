package com.fu.ismb.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Cart;
import com.fu.ismb.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manlm on 10/2/2016.
 */
public class CartDao extends GenericDao<Cart> {

    public CartDao(DBHelper dbHelper) {
        super(dbHelper, DBHelper.CART_TABLE_NAME, DBHelper.CART_COLUMN_PRODUCT_ID, Cart.class);
    }

    public List<Product> sortedListCart() {
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        StringBuilder query = new StringBuilder("SELECT ")
                .append(DBHelper.PRODUCT_COLUMN_ID).append(",")
                .append(DBHelper.PRODUCT_COLUMN_NAME).append(",")
                .append(DBHelper.PRODUCT_COLUMN_AREA_NAME)
                .append(" FROM ").append(DBHelper.CART_TABLE_NAME).append(",")
                .append(DBHelper.PRODUCT_TABLE_NAME)
                .append(" WHERE ").append(DBHelper.CART_TABLE_NAME).append(".")
                .append(DBHelper.CART_COLUMN_PRODUCT_ID).append("=")
                .append(DBHelper.PRODUCT_TABLE_NAME).append(".").append(DBHelper.PRODUCT_COLUMN_ID)
                .append(" ORDER BY ").append(DBHelper.PRODUCT_COLUMN_AREA_WEIGHT).append(",")
                .append(DBHelper.PRODUCT_COLUMN_AREA_NAME).append(" ASC");

        List<Product> list = new ArrayList<>();

        Cursor c = db.rawQuery(String.valueOf(query), null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DBHelper.PRODUCT_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(DBHelper.PRODUCT_COLUMN_NAME));
                String areaName = c.getString(c.getColumnIndex(DBHelper.PRODUCT_COLUMN_AREA_NAME));
                Product pro = new Product();
                pro.setId(id);
                pro.setName(name);
                pro.setAreaName(areaName);
                list.add(pro);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public List<Product> getProductInCartByAreaName(String AreaName) {
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        StringBuilder query = new StringBuilder("SELECT ").append(DBHelper.PRODUCT_COLUMN_ID)
                .append(",").append(DBHelper.PRODUCT_COLUMN_NAME).append(",")
                .append(DBHelper.PRODUCT_COLUMN_AREA_NAME)
                .append(" FROM ").append(DBHelper.CART_TABLE_NAME).append(",")
                .append(DBHelper.PRODUCT_TABLE_NAME)
                .append(" WHERE ").append(DBHelper.CART_TABLE_NAME).append(".")
                .append(DBHelper.CART_COLUMN_PRODUCT_ID).append("=")
                .append(DBHelper.PRODUCT_TABLE_NAME).append(".")
                .append(DBHelper.PRODUCT_COLUMN_ID).append(" AND ")
                .append(DBHelper.PRODUCT_TABLE_NAME).append(".")
                .append(DBHelper.PRODUCT_COLUMN_AREA_NAME).append("=").append("'").append(AreaName).append("'").append(" AND ")
                .append(DBHelper.CART_COLUMN_IS_FOUND).append("=").append(" 0 ");
        Log.e("BEE "," BEE "+query);
        List<Product> list = new ArrayList<>();

        Cursor c = db.rawQuery(String.valueOf(query), null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DBHelper.PRODUCT_COLUMN_ID));
                String name = c.getString(c.getColumnIndex(DBHelper.PRODUCT_COLUMN_NAME));
                String areaName = c.getString(c.getColumnIndex(DBHelper.PRODUCT_COLUMN_AREA_NAME));
                Product pro = new Product();
                pro.setId(id);
                pro.setName(name);
                pro.setAreaName(areaName);
                list.add(pro);
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}
