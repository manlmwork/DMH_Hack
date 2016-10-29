package com.fu.ismb.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fu.ismb.database.DBHelper;
import com.fu.ismb.database.SqliteTableFactory;
import com.fu.ismb.entity.SqliteTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manlm on 9/30/2016.
 */

public abstract class GenericDao<E extends SqliteTable> {

    private final DBHelper dbHelper;
    private final String tableName;
    private final String primaryField;
    private final Class<E> entityClass;

    public GenericDao(DBHelper dbHelper, String tableName, String primaryField, Class<E> entityClass) {
        this.dbHelper = dbHelper;
        this.tableName = tableName;
        this.primaryField = primaryField;
        this.entityClass = entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public DBHelper getDBHelper() {
        return dbHelper;
    }

    public E getByPrimary(int id) {
        return getByPrimary(String.valueOf(id));
    }

    public E getByPrimary(String id) {
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(this.getTableName())
                .append(" WHERE ").append(primaryField).append(" = ?");
        String[] param = new String[]{id};
        Cursor cursor = db.rawQuery(String.valueOf(query), param);
        if (cursor.moveToFirst()) {
            E entity = SqliteTableFactory.getInstance().getSqliteTableObject(entityClass);
            entity.setValue(cursor);
            return entity;
        }
        return null;
    }

    public List<E> getBy(String where, String value) {
        List<E> result = new ArrayList<>();
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(this.getTableName())
                .append(" WHERE ").append(where).append(" = ?");
        String[] param = new String[]{value};
        Cursor cursor = db.rawQuery(String.valueOf(query), param);
        if (cursor.moveToFirst()) {
            do {
                E entity = SqliteTableFactory.getInstance().getSqliteTableObject(entityClass);
                entity.setValue(cursor);
                result.add(entity);
            } while (cursor.moveToNext());

        }
        return result;
    }

    public boolean insert(E entity) {
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        ContentValues value = entity.getContentValues();
        long executedQuery = db.insert(this.tableName, null, value);
        return executedQuery > 0;
    }

    public boolean update(E entity) {
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        ContentValues value = entity.getContentValues();
        String[] param = new String[]{entity.getPrimaryValue()};
        long executedQuery = db.update(this.tableName, value, this.primaryField + " = ?", param);
        return executedQuery > 0;
    }

    public boolean delete(E entity) {
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        String[] param = new String[]{entity.getPrimaryValue()};
        long executedQuery = db.delete(this.tableName, this.primaryField + " = ?", param);
        return executedQuery > 0;
    }

    public void deleteAll() {
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        db.execSQL("delete from " + this.getTableName());
    }

    public List<E> getAll() {
        List<E> result = new ArrayList<>();
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        String query = "SELECT * FROM " + this.getTableName();
        String[] param = null;
        Cursor cursor = db.rawQuery(query, param);

        if (cursor.moveToFirst()) {
            do {
                E entity = SqliteTableFactory.getInstance().getSqliteTableObject(entityClass);
                entity.setValue(cursor);
                result.add(entity);
            } while (cursor.moveToNext());

        }
        return result;
    }
}
