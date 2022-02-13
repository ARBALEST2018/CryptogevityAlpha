package com.example.cryptogevityalpha;

public class ForumPost {
    private String title;
    private int time;
    private int likes;
    private int comments;
    private String intro;
    private String author;
    private int id;
    public ForumPost(String ttl, int t, int l, int cm, String it, String au, int i) {
        title = ttl;
        time = t;
        likes = l;
        comments = cm;
        intro = it;
        author = au;
        id = i;
    }
    public void setTime(int toSet) {
        time = toSet;
    }
    public void setLikes(int toSet) {
        likes = toSet;
    }
    public void setComments(int toSet) {
        comments = toSet;
    }
    public String getTitle() {
        return title;
    }
    public int getTime() {
        return time;
    }
    public int getLikes() {
        return likes;
    }
    public int getComments() {
        return  comments;
    }
    public String getIntro() {
        return intro;
    }
    public String getAuthor() {
        return author;
    }
    public int getId() {
        return id;
    }

}
