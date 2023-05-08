package com.chengxusheji.domain;

import java.sql.Timestamp;
public class GuestBook {
    /*记录编号*/
    private int guestBookId;
    public int getGuestBookId() {
        return guestBookId;
    }
    public void setGuestBookId(int guestBookId) {
        this.guestBookId = guestBookId;
    }

    /*留言标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*留言内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*留言人*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*留言时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}