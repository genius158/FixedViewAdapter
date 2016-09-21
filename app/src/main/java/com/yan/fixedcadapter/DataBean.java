package com.yan.fixedcadapter;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/6/14.
 */
public class DataBean {
    String name;
    String title;
    String content;
    String time;
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public DataBean(String name, String title, String content, String time, Bitmap bitmap) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.time = time;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

}
