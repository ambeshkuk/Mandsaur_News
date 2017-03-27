package com.mandasur.app.data.source.dao.requestdao;

import com.mandasur.app.data.source.dao.Category;

import java.util.ArrayList;

/**
 * Created by ambesh on 27-03-2017.
 */
public class CategoryResponseBean extends Response {



    ArrayList<Category> data;

    public ArrayList<Category> getData() {
        return data;
    }

    public void setData(ArrayList<Category> categories) {
        this.data = categories;
    }
}
