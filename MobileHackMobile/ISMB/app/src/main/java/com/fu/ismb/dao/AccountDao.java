package com.fu.ismb.dao;

import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Account;

/**
 * Created by manlm on 9/30/2016.
 */

public class AccountDao extends GenericDao<Account> {

    public AccountDao(DBHelper dbHelper) {
        super(dbHelper, DBHelper.FB_TABLE_NAME, DBHelper.FB_COLUMN_TOKEN, Account.class);
    }
}
