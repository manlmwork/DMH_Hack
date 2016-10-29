package com.fu.ismb.model;

import com.fu.ismb.entity.Product;

import java.util.List;

/**
 * Created by PhucNT on 10/20/2016.
 */

public class ProductModel {

    private List<Product> productList;

    private long lastSync;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public long getLastSync() {
        return lastSync;
    }

    public void setLastSync(long lastSync) {
        this.lastSync = lastSync;
    }
}
