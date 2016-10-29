package com.fu.ismb.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Beacon;
import com.fu.ismb.model.Point;
import com.fu.ismb.model.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manlm on 9/30/2016.
 */

public class BeaconDao extends GenericDao<Beacon> {

    public BeaconDao(DBHelper dbHelper) {
        super(dbHelper, DBHelper.BEACON_TABLE_NAME, DBHelper.BEACON_COLUMN_ID, Beacon.class);
    }

    public List<Beacon> getListBeacon(List<com.estimote.sdk.Beacon> beaconList) {
        int flag = 0;
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        StringBuilder query = new StringBuilder("SELECT * FROM ")
                .append(DBHelper.BEACON_TABLE_NAME)
                .append(" WHERE ");
        for (int i = 0; i < beaconList.size(); i++) {
            query.append(DBHelper.BEACON_COLUMN_MINOR).append(" = ").append(beaconList.get(i).getMinor());
            if (flag < beaconList.size() - 1) {
                query.append(" OR ");
            }
            flag++;
        }

        List<Beacon> beacons = new ArrayList<>();

        Cursor c = db.rawQuery(String.valueOf(query), null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex(DBHelper.BEACON_COLUMN_ID));
                String uuid = c.getString(c.getColumnIndex(DBHelper.BEACON_COLUMN_UUID));
                int major = c.getInt(c.getColumnIndex(DBHelper.BEACON_COLUMN_MAJOR));
                int minor = c.getInt(c.getColumnIndex(DBHelper.BEACON_COLUMN_MINOR));
                double x = c.getDouble(c.getColumnIndex(DBHelper.BEACON_COLUMN_X));
                double y = c.getDouble(c.getColumnIndex(DBHelper.BEACON_COLUMN_Y));
                double z = c.getDouble(c.getColumnIndex(DBHelper.BEACON_COLUMN_Z));
                Beacon beacon = new Beacon();
                beacon.setUuid(uuid);
                beacon.setId(id);
                beacon.setMajor(major);
                beacon.setMinor(minor);
                beacon.setX(x);
                beacon.setY(y);
                beacon.setZ(z);
                beacons.add(beacon);
            } while (c.moveToNext());
        }
        c.close();
        return beacons;
    }

    public Rectangle findBeacons(Point point) {
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        StringBuilder query = new StringBuilder("SELECT * FROM")
                .append(" ( SELECT ")
                .append(DBHelper.BEACON_COLUMN_X).append(" AS P1X").append(" , ")
                .append(DBHelper.BEACON_COLUMN_Y).append(" AS P1Y").append(" , ")
                .append(DBHelper.BEACON_COLUMN_Z).append(" AS P1Z").append(" , ")
                .append(DBHelper.BEACON_COLUMN_MINOR).append(" AS P1Minor")
                .append(" FROM ")
                .append(DBHelper.BEACON_TABLE_NAME).append(" WHERE ")
                .append(DBHelper.BEACON_COLUMN_X).append(">").append(point.getX()).append(" AND ")
                .append(DBHelper.BEACON_COLUMN_Y).append(">").append(point.getY()).append(") AS P1")
                .append(",")
                .append(" ( SELECT ")
                .append(DBHelper.BEACON_COLUMN_X).append(" AS P2X").append(" , ")
                .append(DBHelper.BEACON_COLUMN_Y).append(" AS P2Y").append(" , ")
                .append(DBHelper.BEACON_COLUMN_Z).append(" AS P2Z").append(" , ")
                .append(DBHelper.BEACON_COLUMN_MINOR).append(" AS P2Minor")
                .append(" FROM ")
                .append(DBHelper.BEACON_TABLE_NAME).append(" WHERE ")
                .append(DBHelper.BEACON_COLUMN_X).append(">").append(point.getX()).append(" AND ")
                .append(DBHelper.BEACON_COLUMN_Y).append("<").append(point.getY()).append(") AS P2")
                .append(",")
                .append(" ( SELECT ")
                .append(DBHelper.BEACON_COLUMN_X).append(" AS P3X").append(" , ")
                .append(DBHelper.BEACON_COLUMN_Y).append(" AS P3Y").append(" , ")
                .append(DBHelper.BEACON_COLUMN_Z).append(" AS P3Z").append(" , ")
                .append(DBHelper.BEACON_COLUMN_MINOR).append(" AS P3Minor")
                .append(" FROM ")
                .append(DBHelper.BEACON_TABLE_NAME).append(" WHERE ")
                .append(DBHelper.BEACON_COLUMN_X).append("<").append(point.getX()).append(" AND ")
                .append(DBHelper.BEACON_COLUMN_Y).append(">").append(point.getY()).append(") AS P3")
                .append(",")
                .append(" ( SELECT ")
                .append(DBHelper.BEACON_COLUMN_X).append(" AS P4X").append(" , ")
                .append(DBHelper.BEACON_COLUMN_Y).append(" AS P4Y").append(" , ")
                .append(DBHelper.BEACON_COLUMN_Z).append(" AS P4Z").append(" , ")
                .append(DBHelper.BEACON_COLUMN_MINOR).append(" AS P4Minor")
                .append(" FROM ")
                .append(DBHelper.BEACON_TABLE_NAME).append(" WHERE ")
                .append(DBHelper.BEACON_COLUMN_X).append("<").append(point.getX()).append(" AND ")
                .append(DBHelper.BEACON_COLUMN_Y).append("<").append(point.getY()).append(") AS P4")
                .append(" WHERE ").append("P1X = P2X AND P1Y = P3Y AND P4X = P3X AND P2Y = P4Y ")
                .append("Order by P1X asc, P1Y asc, P2Y desc, P4X desc limit 1");
        Rectangle rectangle = new Rectangle();
        Cursor c = db.rawQuery(String.valueOf(query), null);
        if (c.moveToFirst()) {
            do {
                double p1x = c.getDouble(c.getColumnIndex("P1X"));
                double p1y = c.getDouble(c.getColumnIndex("P1Y"));
                double p1z = c.getDouble(c.getColumnIndex("P1Z"));
                double p2x = c.getDouble(c.getColumnIndex("P2X"));
                double p2y = c.getDouble(c.getColumnIndex("P2Y"));
                double p2z = c.getDouble(c.getColumnIndex("P2Z"));
                double p3x = c.getDouble(c.getColumnIndex("P3X"));
                double p3y = c.getDouble(c.getColumnIndex("P3Y"));
                double p3z = c.getDouble(c.getColumnIndex("P3Z"));
                double p4x = c.getDouble(c.getColumnIndex("P4X"));
                double p4y = c.getDouble(c.getColumnIndex("P4Y"));
                double p4z = c.getDouble(c.getColumnIndex("P4Z"));

                int p1m = c.getInt(c.getColumnIndex("P1Minor"));
                int p2m = c.getInt(c.getColumnIndex("P2Minor"));
                int p3m = c.getInt(c.getColumnIndex("P3Minor"));
                int p4m = c.getInt(c.getColumnIndex("P4Minor"));

                rectangle.setP1x(p1x);
                rectangle.setP1y(p1y);
                rectangle.setP1z(p1z);
                rectangle.setP2x(p2x);
                rectangle.setP2y(p2y);
                rectangle.setP2z(p2z);
                rectangle.setP3x(p3x);
                rectangle.setP3y(p3y);
                rectangle.setP3z(p3z);
                rectangle.setP4x(p4x);
                rectangle.setP4y(p4y);
                rectangle.setP4z(p4z);
                rectangle.setMinorP1(p1m);
                rectangle.setMinorP2(p2m);
                rectangle.setMinorP3(p3m);
                rectangle.setMinorP4(p4m);

            } while (c.moveToNext());
        }

        c.close();
        return rectangle;
    }

    public String getAreaName(Rectangle rec) {
        SQLiteDatabase db = getDBHelper().getReadableDatabase();
        StringBuilder query = new StringBuilder("SELECT * FROM")
                .append(" ( SELECT ").append(DBHelper.AREA_COLUMN_AREA_NAME).append(" FROM ").append(DBHelper.AREA_TABLE_NAME)
                .append(" WHERE ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_1).append(" = ").append(rec.getMinorP1())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_2).append(" = ").append(rec.getMinorP1())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_3).append(" = ").append(rec.getMinorP1())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_4).append(" = ").append(rec.getMinorP1())
                .append(" ) T1")
                .append(" JOIN ")
                .append(" ( SELECT ").append(DBHelper.AREA_COLUMN_AREA_NAME).append(" FROM ").append(DBHelper.AREA_TABLE_NAME)
                .append(" WHERE ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_1).append(" = ").append(rec.getMinorP2())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_2).append(" = ").append(rec.getMinorP2())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_3).append(" = ").append(rec.getMinorP2())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_4).append(" = ").append(rec.getMinorP2())
                .append(" ) T2")
                .append(" ON ").append(" T1.").append(DBHelper.AREA_COLUMN_AREA_NAME).append(" = ").append(" T2.").append(DBHelper.AREA_COLUMN_AREA_NAME).append(" JOIN ")
                .append(" ( SELECT ").append(DBHelper.AREA_COLUMN_AREA_NAME).append(" FROM ").append(DBHelper.AREA_TABLE_NAME)
                .append(" WHERE ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_1).append(" = ").append(rec.getMinorP3())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_2).append(" = ").append(rec.getMinorP3())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_3).append(" = ").append(rec.getMinorP3())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_4).append(" = ").append(rec.getMinorP3())
                .append(" ) T3")
                .append(" ON ").append(" T1.").append(DBHelper.AREA_COLUMN_AREA_NAME).append(" = ").append(" T3.").append(DBHelper.AREA_COLUMN_AREA_NAME).append(" JOIN ")
                .append(" ( SELECT ").append(DBHelper.AREA_COLUMN_AREA_NAME).append(" FROM ").append(DBHelper.AREA_TABLE_NAME)
                .append(" WHERE ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_1).append(" = ").append(rec.getMinorP4())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_2).append(" = ").append(rec.getMinorP4())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_3).append(" = ").append(rec.getMinorP4())
                .append(" OR ").append(DBHelper.AREA_COLUMN_AREA_BEACON_MINOR_4).append(" = ").append(rec.getMinorP4())
                .append(" ) T4")
                .append(" ON ").append(" T1.").append(DBHelper.AREA_COLUMN_AREA_NAME).append(" = ").append(" T4.").append(DBHelper.AREA_COLUMN_AREA_NAME);
        Cursor c = db.rawQuery(String.valueOf(query), null);
        String areaName = "";
        if (c.moveToFirst()) {
            do {
                areaName = c.getString(c.getColumnIndex("name"));
            } while (c.moveToNext());
        }

        c.close();
        return areaName;
    }

    public double calculateAccuracy(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }

}
