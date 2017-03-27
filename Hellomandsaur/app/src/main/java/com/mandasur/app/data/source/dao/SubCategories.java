package com.mandasur.app.data.source.dao;

/**
 * Created by ambesh on 11-02-2017.
 */
public class SubCategories
{





    public static final String SUBCATEGORY_NAME ="subcategory_name";
    public static final String SUBCATEGORY_ID ="subCategoryId";
    public static final String SUBCATEGORY_INDICATOR ="subcategory_indicator";
    public static final String IS_ITEM_CHECKED ="isItemChecked";
    private String subcategory_name;
    private String subCategoryId;


    public String getSubcategory_indicator() {
        return subcategory_indicator;
    }

    public void setSubcategory_indicator(String subcategory_indicator) {
        this.subcategory_indicator = subcategory_indicator;
    }

    private String subcategory_indicator;

    public boolean isItemChecked() {
        return isItemChecked;
    }

    public void setIsItemChecked(boolean isItemChecked) {
        this.isItemChecked = isItemChecked;
    }

    private boolean isItemChecked;


    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
}

