package com.fu.ismb.dao;

import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Area;

/**
 * Created by PhucNT on 10/19/2016.
 */

public class AreaDao extends GenericDao<Area> {
    public AreaDao(DBHelper dbHelper) {
        super(dbHelper, DBHelper.AREA_TABLE_NAME, DBHelper.AREA_COLUMN_AREA_ID, Area.class);
    }
}
