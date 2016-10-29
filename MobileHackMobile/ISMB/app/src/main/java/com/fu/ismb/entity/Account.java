package com.fu.ismb.entity;

import android.content.ContentValues;
import android.database.Cursor;

import com.fu.ismb.database.DBHelper;

/**
 * Created by manlm on 9/30/2016.
 */

public class Account implements SqliteTable {

    private String token;

    private String appFbId;

    private String botFbId;

    private String firstName;

    private String lastName;

    private String phone;

    @Override
    public void setValue(Cursor cursor) {
        this.setToken(cursor.getString(cursor.getColumnIndex(DBHelper.FB_COLUMN_TOKEN)));
        this.setAppFbId(cursor.getString(cursor.getColumnIndex(DBHelper.FB_COLUMN_APP_ID)));
        this.setBotFbId(cursor.getString(cursor.getColumnIndex(DBHelper.FB_COLUMN_BOT_ID)));
        this.setFirstName(cursor.getString(cursor.getColumnIndex(DBHelper.FB_COLUMN_FIRST_NAME)));
        this.setLastName(cursor.getString(cursor.getColumnIndex(DBHelper.FB_COLUMN_LAST_NAME)));
        this.setPhone(cursor.getString(cursor.getColumnIndex(DBHelper.FB_COLUMN_PHONE)));
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DBHelper.FB_COLUMN_TOKEN, this.getToken());
        values.put(DBHelper.FB_COLUMN_APP_ID, this.getAppFbId());
        values.put(DBHelper.FB_COLUMN_BOT_ID, this.getBotFbId());
        values.put(DBHelper.FB_COLUMN_FIRST_NAME, this.getFirstName());
        values.put(DBHelper.FB_COLUMN_LAST_NAME, this.getLastName());
        values.put(DBHelper.FB_COLUMN_PHONE, this.getPhone());
        return values;
    }

    @Override
    public String getPrimaryValue() {
        return this.getToken();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppFbId() {
        return appFbId;
    }

    public void setAppFbId(String appFbId) {
        this.appFbId = appFbId;
    }

    public String getBotFbId() {
        return botFbId;
    }

    public void setBotFbId(String botFbId) {
        this.botFbId = botFbId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
