package com.example.cryptogevityalpha;

import android.graphics.drawable.Drawable;

public class TrendingCourse {
    private String title;
    private String category;
    private Drawable picture;
    private int drawableId;
    private String title1;
    private String title2;
    public TrendingCourse(int id, String t, Drawable p, String c) {
        title = t;
        picture = p;
        drawableId = id;
        category = c;
        int curr = -1;
        for (int i = 0; i < title.length(); i++) {
            if (title.charAt(i) == ' ' && Math.abs(title.length() - 2 * i) < Math.abs(title.length() - 2 * curr)) {
                curr = i;
            }
        }
        title1 = title.substring(0, curr);
        title2 = title.substring(curr+1);
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
    public String getCategory() {
        return category;
    }
    public String getTitle1() {
        return title1;
    }
    public String getTitle2() {
        return title2;
    }
}
