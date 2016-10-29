package com.fu.ismb.task;

import android.os.AsyncTask;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.fu.ismb.entity.Cart;
import com.fu.ismb.model.CartInfo;
import com.fu.ismb.util.Constant;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by manlm on 10/2/2016.
 */

public class SyncCartTask extends AsyncTask<Object, Void, List<Cart>> {

    public AsyncResponse delegate = null;

    @Override
    protected List<Cart> doInBackground(Object... params) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Device-Token", String.valueOf(params[1]));

        CartInfo cartInfo = (CartInfo) params[0];

        HttpEntity<String> request = new HttpEntity<>(new Gson().toJson(cartInfo), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String url = Constant.SERVER_URL + "syncCart";

        String result = restTemplate.postForObject(url, request, String.class);
        int statusCode = new Gson().fromJson(result, Integer.class);

        List<Cart> cartList = cartInfo.getCart();

        if (statusCode != HttpStatus.OK.value()) {
            cartList.clear();
        }
        return cartList;
    }

    @Override
    protected void onPostExecute(List<Cart> result) {
        delegate.processFinish(result);
    }
}
