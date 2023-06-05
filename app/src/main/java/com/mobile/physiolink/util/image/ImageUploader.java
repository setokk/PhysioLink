package com.mobile.physiolink.util.image;

import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Optional;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ImageUploader
{
    public static void uploadImage(FragmentActivity context, String name, Callback callback)
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

        // CLEAR CACHE
        Picasso.with(context)
                .invalidate(Uri.parse(UserHolder.psf().getImageURL()));
    }

    public static String getAbsolutePathFromUri(Uri uri)
    {
        if (uri == null) return "";
        return new File(uri.getPath()).getAbsolutePath();
    }
}
