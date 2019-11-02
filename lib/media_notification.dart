import 'package:flutter/services.dart';
import 'package:flutter_channel_example/media_arguments.dart';

class MediaNotification {
  static final MethodChannel _channel =
      MethodChannel('com.example.flutter_channel_example/media_notification')
        ..setMethodCallHandler(_methodCallHandler);

  static Future<void> showNotification() async {
    MediaArguments args = MediaArguments(
      title: "Don't Say a Word",
      desc: 'Ellie Goulding',
    );

    await _channel.invokeMethod('show', args.toJson());
  }

  static Future<void> _methodCallHandler(MethodCall call) async {
    switch (call.method) {
      case 'prev':
        print('previous');
        break;
      case 'pause':
        print('pause');
        break;
      case 'next':
        print('next');
        break;
      case 'close':
        print('close');
        break;
      default:
        print('method not define');
    }
  }
}
