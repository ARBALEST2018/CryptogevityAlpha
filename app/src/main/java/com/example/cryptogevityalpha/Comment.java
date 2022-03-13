package com.example.cryptogevityalpha;

public class Comment {
    private int id;
    private String user_id;
    private String desc;
    private String create_time;
    public Comment(int setId, String setUserId, String setDesc, String setCreateTime) {
        id = setId;
        user_id = setUserId;
        desc = setDesc;
        create_time = setCreateTime;
    }
    public int getId() {
        return id;
    }
    public String getUserId() {
        return  user_id;
    }
    public String getDesc() {
        return desc;
    }
    public String getCreateTimeOrigin() {
        return create_time;
    }
    public String getCreateTime() {
        return create_time.substring(5, 10)+" "+create_time.substring(11, 16);
    }
}
