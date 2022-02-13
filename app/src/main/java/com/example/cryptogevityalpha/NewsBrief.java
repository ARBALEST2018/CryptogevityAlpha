package com.example.cryptogevityalpha;

import android.graphics.drawable.Drawable;

public class NewsBrief {
    private String title;
    private Drawable picture;
    private int drawableId;
    public NewsBrief(int id, String t, Drawable p) {
        title = t;
        picture = p;
        drawableId = id;
    }
    public String getTitle() {
        return title;
    }
    public Drawable getPicture() {
        return  picture;
    }
    public int getDrawableId() {
        return drawableId;
    }
}
