package com.fu.ismb.model;

import com.fu.ismb.entity.Area;
import com.fu.ismb.entity.Beacon;
import com.fu.ismb.entity.Product;

import java.util.List;

/**
 * Created by manlm on 10/2/2016.
 */

public class Data {

    private ProductModel productList;

    private BeaconModel beaconList;

    private AreaModel areaList;

    public ProductModel getProductList() {
        return productList;
    }

    public void setProductList(ProductModel productList) {
        this.productList = productList;
    }

    public AreaModel getAreaList() {
        return areaList;
    }

    public void setAreaList(AreaModel areaList) {
        this.areaList = areaList;
    }

    public BeaconModel getBeaconList() {
        return beaconList;
    }

    public void setBeaconList(BeaconModel beaconList) {
        this.beaconList = beaconList;
    }
}
