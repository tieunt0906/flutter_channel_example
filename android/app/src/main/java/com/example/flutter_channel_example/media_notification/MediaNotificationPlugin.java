package com.example.flutter_channel_example.media_notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.flutter_channel_example.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class MediaNotificationPlugin {
    public static final String CHANNEL_ID = "CHANNEL_ID";
    public static final int MEDIA_NOTIFICATION_ID = 1;
    private static MethodChannel channel;
    private static Context context;
    private static NotificationManagerCompat notificationManager;
    private static MediaSessionCompat mediaSession;

    public static void registerWith(Context ctx, PluginRegistry registry) {
        context = ctx;
        final String key = "MediaNotification";
        if (registry.hasPlugin(key)) return;
        PluginRegistry.Registrar registrar = registry.registrarFor(key);

        notificationManager = NotificationManagerCompat.from(context);
        mediaSession = new MediaSessionCompat(context, "tag");

        channel = new MethodChannel(registrar.messenger(),
                "com.example.flutter_channel_example/media_notification");
        channel.setMethodCallHandler((call, result) -> {
            switch (call.method) {
                case "show":
                    MediaArguments args = new MediaArguments(call.argument("title"),
                            call.argument("desc"), call.argument("picture"));
                    showNotification(args);
                    result.success(null);
                    break;
                case "hide":
                    hideNotification();
                    break;
                default:
                    result.notImplemented();
            }
        });
    }

    private static void showNotification(MediaArguments args) {
        Bitmap picture = getBitmapFromURL(args.getPicture());

        if (picture == null) {
            picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image);
        }

        Intent prevIntent = new Intent(context, MediaNotificationReceiver.class).setAction("prev");
        PendingIntent prevAction = PendingIntent.getBroadcast(context, 0, prevIntent, 0);

        Intent pauseIntent = new Intent(context, MediaNotificationReceiver.class).setAction("pause");
        PendingIntent pauseAction = PendingIntent.getBroadcast(context, 0, pauseIntent, 0);

        Intent nextIntent = new Intent(context, MediaNotificationReceiver.class).setAction("next");
        PendingIntent nextAction = PendingIntent.getBroadcast(context, 0, nextIntent, 0);

        Intent closeIntent = new Intent(context, MediaNotificationReceiver.class).setAction("close");
        PendingIntent closeAction = PendingIntent.getBroadcast(context, 0, closeIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(args.getTitle())
                .setContentText(args.getDesc())
                .setShowWhen(false)
                .setOngoing(true)
                .setLargeIcon(picture)
                .addAction(R.drawable.ic_prev, "Previous", prevAction)
                .addAction(R.drawable.ic_pause, "Pause", pauseAction)
                .addAction(R.drawable.ic_next, "Next", nextAction)
                .addAction(R.drawable.ic_close, "Close", closeAction)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSession.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(MEDIA_NOTIFICATION_ID, notification);
    }

    public static void hideNotification() {
        notificationManager.cancel(MEDIA_NOTIFICATION_ID);
    }

    public static void callEvent(String event) {
        channel.invokeMethod(event, null);
    }

    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }
}
