package com.mobileserver.domain;

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
    private java.sql.Timestamp newsDate;
    public java.sql.Timestamp getNewsDate() {
        return newsDate;
    }
    public void setNewsDate(java.sql.Timestamp newsDate) {
        this.newsDate = newsDate;
    }

}