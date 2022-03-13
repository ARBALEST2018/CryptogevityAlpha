package com.example.cryptogevityalpha;

import java.util.List;

public class ForumPost {
    private int id;
    private String user_id;
    private String title;
    private String desc;
    private String create_time;
    private List<Comment> comments;
//    public ForumPost(String ttl, int t, int l, int cm, String it, String au, int i) {
//        title = ttl;
//        create_time = t;
//        likes = l;
//        comments = cm;
//        intro = it;
//        author = au;
//        id = i;
//    }
    public ForumPost(String setTitle, String setDesc) {
        title = setTitle;
        desc = setDesc;
    }
    public ForumPost(String setTitle, String setDesc, String setUserId) {
        title = setTitle;
        desc = setDesc;
        user_id = setUserId;
    }
    public ForumPost(int setId, String setUserId, String setTitle, String setDesc, String setCreateTime, List<Comment> setComments) {
        title = setTitle;
        desc = setDesc;
        user_id = setUserId;
        id = setId;
        create_time = setCreateTime;
        comments = setComments;
    }
//    public void setTime(int toSet) {
//        create_time = toSet;
//    }
//    public void setLikes(int toSet) {
//        likes = toSet;
//    }
//    public void setComments(int toSet) {
//        comments = toSet;
//    }
    public int getId() {
        return id;
    }
    public String getUserId() {
        return user_id;
    }
    public String getTitle() {
        return title;
    }
    public String getDesc() {
        return desc;
    }
    public String getCreateTime() {
        return create_time.substring(5, 10)+" "+create_time.substring(11, 16);
    }
    public String getCreateTimeOrigin() {
        return create_time;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public Comment getComment(int index) {
        return comments.get(index);
    }
    public void addComment(Comment toAdd) {
        comments.add(toAdd);
    }
    public void removeAllComments() {
        comments.clear();
    }
}
