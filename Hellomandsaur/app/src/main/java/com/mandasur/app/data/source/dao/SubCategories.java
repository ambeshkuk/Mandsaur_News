package com.mandasur.app.data.source.dao;

/**
 * Created by ambesh on 11-02-2017.
 */
public class SubCategories
{
    private String subCategoryName;
    private String subCategoryId;

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

