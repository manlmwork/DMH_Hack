package com.fu.ismb.task;

import android.os.AsyncTask;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.reflect.TypeToken;
import com.fu.ismb.model.BotInfo;
import com.fu.ismb.model.SaveData;
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
 * Created by manlm on 10/6/2016.
 */

public class GetCartTask extends AsyncTask<Object, Void, List<SaveData>> {

    public AsyncResponse delegate = null;

    @Override
    protected List<SaveData> doInBackground(Object... params) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Device-Token", String.valueOf(params[0]));

        BotInfo botInfo = new BotInfo();
        botInfo.setBotFbId(String.valueOf(params[1]));

        HttpEntity<String> request = new HttpEntity<>(new Gson().toJson(botInfo), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String url = Constant.SERVER_URL + "getCart";
        String result = restTemplate.postForObject(url, request, String.class);

        Type type = new TypeToken<List<SaveData>>() {
        }.getType();
        List<SaveData> productIdList = new Gson().fromJson(result, type);
        return productIdList;
    }

    @Override
    protected void onPostExecute(List<SaveData> result) {
        delegate.processFinish(result);
    }
}
