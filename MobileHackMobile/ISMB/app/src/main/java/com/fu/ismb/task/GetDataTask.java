package com.fu.ismb.task;

import android.os.AsyncTask;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.reflect.TypeToken;
import com.fu.ismb.entity.Area;
import com.fu.ismb.entity.Beacon;
import com.fu.ismb.entity.Product;
import com.fu.ismb.model.AreaModel;
import com.fu.ismb.model.BeaconModel;
import com.fu.ismb.model.Data;
import com.fu.ismb.model.ProductModel;
import com.fu.ismb.util.Constant;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by manlm on 10/2/2016.
 */

public class GetDataTask extends AsyncTask<String, Void, Data> {

    public AsyncResponse delegate = null;

    @Override
    protected Data doInBackground(String... params) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Device-Token", params[0]);

        HttpEntity<String> request = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        Data data = new Data();
        Gson gson = new Gson();

        String url = Constant.SERVER_URL + "getProduct";
        String result = restTemplate.postForObject(url, request, String.class);
        Type type = new TypeToken<ProductModel>() {
        }.getType();

        ProductModel productModal = gson.fromJson(result, type);
        if (productModal != null) {
            data.setProductList(productModal);
        }

        url = Constant.SERVER_URL + "getBeacon";
        result = restTemplate.postForObject(url, request, String.class);

        type = new TypeToken<BeaconModel>() {
        }.getType();
        BeaconModel beaconModal = gson.fromJson(result, type);
        if (beaconModal != null) {
            data.setBeaconList(beaconModal);
        }

        url = Constant.SERVER_URL + "getArea";
        result = restTemplate.postForObject(url, request, String.class);

        type = new TypeToken<AreaModel>() {
        }.getType();
        AreaModel AreaModal = gson.fromJson(result, type);
        if (AreaModal != null) {
            data.setAreaList(AreaModal);
        }

        return data;
    }

    @Override
    protected void onPostExecute(Data result) {
        delegate.processFinish(result);
    }
}
