package com.mobile.physiolink.service.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service
{
    private Timer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        startTimer();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopTimer();
    }

    private void startTimer()
    {
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                sendNotificationRequest();
            }
        }, 0, 30000);
    }


    private void stopTimer()
    {
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
    }

    private void sendNotificationRequest()
    {

    }
}
