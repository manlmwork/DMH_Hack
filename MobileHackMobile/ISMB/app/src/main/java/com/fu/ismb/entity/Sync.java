package com.fu.ismb.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.fu.ismb.database.DBHelper;

/**
 * Created by PhucNT on 10/20/2016.
 */

public class Sync implements SqliteTable {

    private String id;
    private long lastSync;

    @Override
    public void setValue(Cursor cursor) {
        this.setId(cursor.getString(cursor.getColumnIndex(DBHelper.SYNC_COLUMN_ID)));
        this.setLastSync(cursor.getLong(cursor.getColumnIndex(DBHelper.SYNC_COLUMN_LAST_SYNC)));
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DBHelper.SYNC_COLUMN_ID, this.getId());
        values.put(DBHelper.SYNC_COLUMN_LAST_SYNC, this.getLastSync());
        return values;
    }

    @Override
    public String getPrimaryValue() {
        return this.getId();
    }

    public long getLastSync() {
        return lastSync;
    }

    public void setLastSync(long lastSync) {
        this.lastSync = lastSync;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
