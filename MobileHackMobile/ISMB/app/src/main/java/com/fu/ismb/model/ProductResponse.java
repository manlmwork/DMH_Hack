package com.fu.ismb.model;

import java.util.List;

/**
 * Created by PhucNT on 10/3/2016.
 */

public class ProductResponse {

    private List<SaveData> list;
    private int statusCode;

    public ProductResponse(List<SaveData> list, int statusCode) {
        this.list = list;
        this.statusCode = statusCode;
    }

    public List<SaveData> getList() {
        return list;
    }

    public void setList(List<SaveData> list) {
        this.list = list;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
