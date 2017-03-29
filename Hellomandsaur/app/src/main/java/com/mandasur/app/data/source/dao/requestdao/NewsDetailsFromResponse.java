package com.mandasur.app.data.source.dao.requestdao;

import java.util.ArrayList;

/**
 * Created by ambesh on 25-02-2017.
 */
public class NewsDetailsFromResponse extends Response {

    private ArrayList<NewsDetail> data;

    public String getUnderLineJson() {
        return underLineJson;
    }

    public void setUnderLineJson(String underLineJson) {
        this.underLineJson = underLineJson;
    }

    private String underLineJson;

    public ArrayList<NewsDetail> getRelated_news() {
        return related_news;
    }

    public void setRelated_news(ArrayList<NewsDetail> related_news) {
        this.related_news = related_news;
    }

    private ArrayList<NewsDetail> related_news;


    public ArrayList<NewsDetail> getData() {
        return data;
    }

    public void setData(ArrayList<NewsDetail> data) {
        this.data = data;
    }
}
