package com.chengxusheji.domain;

import java.sql.Timestamp;
public class NewsInfo {
    /*��¼���*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*����*/
    private String newsTitle;
    public String getNewsTitle() {
        return newsTitle;
    }
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    /*��������*/
    private String newsContent;
    public String getNewsContent() {
        return newsContent;
    }
    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    /*��������*/
    private Timestamp newsDate;
    public Timestamp getNewsDate() {
        return newsDate;
    }
    public void setNewsDate(Timestamp newsDate) {
        this.newsDate = newsDate;
    }

}