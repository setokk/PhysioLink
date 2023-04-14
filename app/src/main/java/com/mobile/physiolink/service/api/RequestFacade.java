package com.mobile.physiolink.service.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestFacade
{
    public static Response getRequest(String URL)
    {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .get()
                .build();

        Response response = null;
        try
        {
            response = client.newCall(request).execute();
        }
        catch (IOException ignored) { }

        return response;
    }

    public static Response postRequest(String URL, HashMap<String, String> keyValues)
    {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        FormBody.Builder formBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : keyValues.entrySet())
        {
            formBuilder.add(entry.getKey(), entry.getValue());
        }
        RequestBody formBody = formBuilder.build();

        Request request = new Request.Builder()
                .url(URL)
                .post(formBody)
                .build();

        Response response = null;
        try
        {
            response = client.newCall(request).execute();
        }
        catch (IOException ignored) { }

        return response;
    }
}
