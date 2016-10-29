package com.fu.ismb.dao;

import com.fu.ismb.database.DBHelper;
import com.fu.ismb.entity.Product;

/**
 * Created by manlm on 9/30/2016.
 */

public class ProductDao extends GenericDao<Product> {

    public ProductDao(DBHelper dbHelper) {
        super(dbHelper, DBHelper.PRODUCT_TABLE_NAME, DBHelper.PRODUCT_COLUMN_ID, Product.class);
    }
}
