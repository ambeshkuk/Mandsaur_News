package com.mandasur.app.data.source.dao.requestdao;

import java.util.ArrayList;

/**
 * Created by ambesh on 25-02-2017.
 */
public class NewsDetailsFromResponse extends Response {

    private ArrayList<NewsDetail> data;

    public ArrayList<NewsDetail> getData() {
        return data;
    }

    public void setData(ArrayList<NewsDetail> data) {
        this.data = data;
    }
}
