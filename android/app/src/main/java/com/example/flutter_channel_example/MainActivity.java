package com.example.flutter_channel_example;

import android.os.Bundle;

import com.example.flutter_channel_example.media_notification.MediaNotificationPlugin;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    MediaNotificationPlugin.registerWith(this, this);
  }
}
