package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private Timestamp newsDate;
    public Timestamp getNewsDate() {
        return newsDate;
    }
    public void setNewsDate(Timestamp newsDate) {
        this.newsDate = newsDate;
    }

}