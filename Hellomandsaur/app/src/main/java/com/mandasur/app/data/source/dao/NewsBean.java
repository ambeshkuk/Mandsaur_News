package com.mandasur.app.data.source.dao;

/**
 * Created by ambesh on 29-01-2017.
 */
public class NewsBean {





  private   String newsBannerImg;
    private  String newsDate;
    private  String newsTitle;
    private  String newsId;
    private String newsDescription;
    private String longDescription;

    public NewsBean() {

    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }


    public NewsBean(String newsId,String newsBannerImg, String newsDate, String newsTitle, String newsDescription, String longDescription) {
        this.newsBannerImg = newsBannerImg;
        this.newsDate = newsDate;
        this.newsTitle = newsTitle;
        this.newsDescription = newsDescription;
        this.longDescription = longDescription;
        this.newsId = newsId;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getNewsBannerImg() {
        return newsBannerImg;
    }

    public void setNewsBannerImg(String newsBannerImg) {
        this.newsBannerImg = newsBannerImg;
    }

}
