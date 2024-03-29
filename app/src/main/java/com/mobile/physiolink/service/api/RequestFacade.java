package com.mobile.physiolink.service.api;

import android.os.StrictMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*  */
public class RequestFacade
{
    private static StrictMode.ThreadPolicy policy = null; // Singleton

    public static void getRequest(String URL, Callback callback)
    {
        configThreadPolicy();

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(Objects.requireNonNull(HttpUrl.parse(URL)))
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static void postRequest(String URL,
                                   HashMap<String, String> keyValues,
                                   Callback callback)
    {
        configThreadPolicy();

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

        client.newCall(request).enqueue(callback);
    }

    private static void configThreadPolicy()
    {
        if (policy != null)
            return;

        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
