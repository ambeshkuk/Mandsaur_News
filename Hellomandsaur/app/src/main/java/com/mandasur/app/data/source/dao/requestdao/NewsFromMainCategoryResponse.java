package com.mandasur.app.data.source.dao.requestdao;

/**
 * Created by ambesh on 10-02-2017.
 */
public class NewsFromMainCategoryResponse extends Response {




    private String  status;
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
