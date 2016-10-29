package com.fu.ismb.dao;

import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Sync;

/**
 * Created by PhucNT on 10/20/2016.
 */

public class SyncDao extends GenericDao<Sync> {
    public SyncDao(DBHelper dbHelper) {
        super(dbHelper, DBHelper.SYNC_TABLE_NAME, DBHelper.SYNC_COLUMN_ID, Sync.class);
    }
}
