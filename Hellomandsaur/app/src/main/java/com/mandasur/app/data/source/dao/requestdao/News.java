package com.mandasur.app.data.source.dao.requestdao;








/**
 * Created by ambesh on 13-02-2017.
 */
public class News {

    public static final String TITLE="title";
    public static final String IMAGE="image";
    public static final String DATE="date";
    public static final String NEWSURL="news_url";

    private String fid,title,image,place,date;

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    private String subCategoryName;
    public boolean isSubcategoryStart() {
        return isSubcategoryStart;
    }

    public void setIsSubcategoryStart(boolean isSubcategoryStart) {
        this.isSubcategoryStart = isSubcategoryStart;
    }

    private boolean isSubcategoryStart;
    public String getFid() {
        return fid;
    }


    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}

