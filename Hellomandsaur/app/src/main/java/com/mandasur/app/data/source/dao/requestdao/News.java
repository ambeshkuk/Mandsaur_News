package com.mandasur.app.data.source.dao.requestdao;








/**
 * Created by ambesh on 13-02-2017.
 */
public class News extends BaseNews {

    public static final String TITLE="title";
    public static final String IMAGE="image";
    public static final String DATE="date";
    public static final String ID ="id";
    public static final String SUB_CAT_INDICATOR="sub_cat_indicator";
    public static final String VIDEO_CODE ="video_code";


    private String id;
    private String title;
    private String image;
    private String date;
    private String views;

    public String getVideo_code() {
        return video_code;
    }

    public void setVideo_code(String video_code) {
        this.video_code = video_code;
    }

    private String video_code;
    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    private String newsUrl;

    private String subCatIndicator;
    public String getSubCatIndicator() {
        return subCatIndicator;
    }

    public void setSubCatIndicator(String subCatIndicator) {
        this.subCatIndicator = subCatIndicator;
    }




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
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
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



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}

