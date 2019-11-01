package com.example.flutter_channel_example;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import io.flutter.app.FlutterApplication;

public class App extends FlutterApplication {
    public static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "CHANNEL",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Notification Channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
