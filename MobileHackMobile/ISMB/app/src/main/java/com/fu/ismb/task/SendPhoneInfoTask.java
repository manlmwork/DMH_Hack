package com.fu.ismb.task;

import android.os.AsyncTask;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.fu.ismb.model.PhoneInfo;
import com.fu.ismb.util.Constant;

import org.springframework.http.HttpEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * Created by manlm on 9/25/2016.
 */
public class SendPhoneInfoTask extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... info) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Device-Token", info[4]);

        PhoneInfo phoneInfo = new PhoneInfo();
        phoneInfo.setFbId(info[0]);
        phoneInfo.setFirstName(info[1]);
        phoneInfo.setLastName(info[2]);
        phoneInfo.setPhone(info[3]);

        HttpEntity<String> request = new HttpEntity<>(new Gson().toJson(phoneInfo), headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String url = Constant.SERVER_URL + "registerPhone";

        return restTemplate.postForObject(url, request, String.class);
//        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
