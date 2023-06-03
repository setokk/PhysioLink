package com.mobile.physiolink.util.image;

import android.net.Uri;

import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;

import java.io.File;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ImageUploader
{
    public static void uploadImage(String name, Callback callback)
    {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        File file = new File(name);
        String extension = name.split("\\.")[1];
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/"+extension), file))
                .build();

        Request request = new Request.Builder()
                .url(API.UPLOAD_PROFILE_IMAGE + UserHolder.psf().getId())
                .post(body)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public static String getAbsolutePathFromUri(Uri uri)
    {
        ProfileImageProvider.userURI = uri;

        return new File(uri.getPath()).getAbsolutePath();
    }
}
