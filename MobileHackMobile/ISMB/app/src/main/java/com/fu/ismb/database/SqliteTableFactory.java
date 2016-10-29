package com.fu.ismb.database;

import com.fu.ismb.entity.Account;
import com.fu.ismb.entity.Area;
import com.fu.ismb.entity.Beacon;
import com.fu.ismb.entity.Cart;
import com.fu.ismb.entity.Product;
import com.fu.ismb.entity.SqliteTable;
import com.fu.ismb.entity.Sync;

/**
 * Created by manlm on 9/30/2016.
 */

public class SqliteTableFactory {

    private static SqliteTableFactory instance;

    private SqliteTableFactory() {

    }

    public static SqliteTableFactory getInstance() {
        if (instance == null) {
            instance = new SqliteTableFactory();
        }
        return instance;
    }

    public <E extends SqliteTable> E getSqliteTableObject(Class<E> c) {
        if (c == Beacon.class) {
            return (E) new Beacon();
        } else if (c == Product.class) {
            return (E) new Product();
        } else if (c == Account.class) {
            return (E) new Account();
        } else if (c == Cart.class) {
            return (E) new Cart();
        } else if (c == Area.class) {
            return (E) new Area();
        }else if (c == Sync.class) {
            return (E) new Sync();
        }
        return null;
    }
}
