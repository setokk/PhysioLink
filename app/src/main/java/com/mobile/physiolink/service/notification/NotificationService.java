package com.mobile.physiolink.service.notification;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mobile.physiolink.R;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.ui.DoctorActivity;
import com.mobile.physiolink.ui.PatientActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NotificationService extends Service {
    public static final String CHANNEL_ID = "notification29302";
    private static int notificationCounter = 0;

    private Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // DO NOT CHANGE !!!
                // .psf() RETURNS THE "USER" SUPERCLASS
                sendNotificationRequest(UserHolder.psf().getId());
            }
        }, 0, 30000);
    }


    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void sendNotificationRequest(long userId) {
        RequestFacade.getRequest(API.GET_NOTIFICATIONS + userId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                if (res.contains(Error.RESOURCE_NOT_FOUND))
                    return;

                try {
                    JSONArray jsonNotifications = new JSONObject(res).getJSONArray("notifications");
                    if (jsonNotifications.length() == 0)
                        return;

                    /* Set notification click listener intent */
                    Intent intent;
                    if (UserHolder.psf().isDoctor())
                        intent = new Intent(getApplicationContext(), DoctorActivity.class);
                    else
                        intent = new Intent(getApplicationContext(), PatientActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                            0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    for (int i = 0; i < jsonNotifications.length(); ++i) {
                        JSONObject element = jsonNotifications.getJSONObject(i);

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.drawable.notification_icon)
                                .setContentTitle(element.getString("title"))
                                .setContentText(element.getString("message"))
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setPriority(NotificationCompat.PRIORITY_HIGH);
                        builder.setContentIntent(pendingIntent);
                        checkPermissionsAndNotify(builder);
                    }
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void checkPermissionsAndNotify(NotificationCompat.Builder builder)
    {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
        {
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(notificationCounter++, builder.build());
        }
    }
}
