package com.mobileserver.domain;

public class NewsInfo {
    /*记录编号*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*标题*/
    private String newsTitle;
    public String getNewsTitle() {
        return newsTitle;
    }
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    /*新闻内容*/
    private String newsContent;
    public String getNewsContent() {
        return newsContent;
    }
    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    /*发布日期*/
    private java.sql.Timestamp newsDate;
    public java.sql.Timestamp getNewsDate() {
        return newsDate;
    }
    public void setNewsDate(java.sql.Timestamp newsDate) {
        this.newsDate = newsDate;
    }

}