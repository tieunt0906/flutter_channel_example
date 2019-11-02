package com.example.flutter_channel_example.media_notification;

public class MediaArguments {
    private String title;
    private String desc;
    private String picture;

    public MediaArguments(String title, String desc, String picture) {
        this.title = title;
        this.desc = desc;
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPicture() {
        return picture;
    }
}
