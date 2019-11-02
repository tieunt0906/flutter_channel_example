package com.example.flutter_channel_example.media_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MediaNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case "prev":
                MediaNotificationPlugin.callEvent("prev");
                break;
            case "pause":
                MediaNotificationPlugin.callEvent("pause");
                break;
            case "next":
                MediaNotificationPlugin.callEvent("next");
                break;
            case "close":
                MediaNotificationPlugin.hideNotification();
                MediaNotificationPlugin.callEvent("close");
                break;
        }
    }
}
