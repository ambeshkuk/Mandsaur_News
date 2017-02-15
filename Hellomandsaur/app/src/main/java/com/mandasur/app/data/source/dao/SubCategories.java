package com.mandasur.app.data.source.dao;

/**
 * Created by ambesh on 11-02-2017.
 */
public class SubCategories
{





    public static final String SUBCATEGORY_NAME ="subCategoryName";
    public static final String SUBCATEGORY_ID ="subCategoryId";
    public static final String SUBCATEGORY_INDICATOR ="subCategoryIndicator";
    public static final String IS_ITEM_CHECKED ="isItemChecked";
    private String subCategoryName;
    private String subCategoryId;

    public String getSubCategoryIndicator() {
        return subCategoryIndicator;
    }

    public void setSubCategoryIndicator(String subCategoryIndicator) {
        this.subCategoryIndicator = subCategoryIndicator;
    }

    private String subCategoryIndicator;

    public boolean isItemChecked() {
        return isItemChecked;
    }

    public void setIsItemChecked(boolean isItemChecked) {
        this.isItemChecked = isItemChecked;
    }

    private boolean isItemChecked;


    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
}

