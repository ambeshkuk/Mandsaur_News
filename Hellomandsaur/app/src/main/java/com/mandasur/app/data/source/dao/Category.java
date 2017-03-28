package com.mandasur.app.data.source.dao;

import java.util.ArrayList;

/**
 * Created by ambesh on 29-01-2017.
 */
public class Category {





    public static final String CATEGORY_NAME="category_name";
    public static final String CATEGORY_ICON="category_icon";
    public static final String CATEGORY_INDICATOR="category_indicator";


    private String category_name;
    private String category_icon;
    private String category_indicator;

    public int getCategroyId() {
        return categroyId;
    }

    public void setCategroyId(int categroyId) {
        this.categroyId = categroyId;
    }

    private int categroyId;

    public ArrayList<SubCategories> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(ArrayList<SubCategories> subcategories) {
        this.subcategories = subcategories;
    }

    private ArrayList<SubCategories> subcategories;

    public String getCateegoryNameTabs() {
        return cateegoryNameTabs;
    }

    public void setCateegoryNameTabs(String cateegoryNameTabs) {
        this.cateegoryNameTabs = cateegoryNameTabs;
    }

    private String cateegoryNameTabs;
    public String getCategory_indicator() {
        return category_indicator;
    }

    public void setCategory_indicator(String category_indicator) {
        this.category_indicator = category_indicator;
    }






    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

   public void setCategory_icon(String category_icon){

   }
    public String getCategory_icon(){
        return category_icon;
    }
}
