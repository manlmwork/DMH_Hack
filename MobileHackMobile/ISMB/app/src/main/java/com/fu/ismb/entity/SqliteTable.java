package com.fu.ismb.entity;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by manlm on 9/30/2016.
 */

public interface SqliteTable {

    void setValue(Cursor cursor);

    ContentValues getContentValues();

    String getPrimaryValue();
}
