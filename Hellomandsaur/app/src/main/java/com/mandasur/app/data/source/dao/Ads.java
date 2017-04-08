package com.mandasur.app.data.source.dao;

import com.mandasur.app.data.source.dao.requestdao.BaseNews;

/**
 * Created by ambesh on 02-04-2017.
 */
public class Ads extends BaseNews{






    public static final String ID="id";
    public static final String TITLE="title";
    public static final String AD_TITLE="ad_title";
    public static final String AD_IMAGE_PREVIEW="ad_image_preview";
    public static final String AD_IMAGE_FULL="ad_image_full";
    public static final String AD_URL="ad_url";
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

    private String id;
    private String title;

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    private String ad_url;
    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public String getAd_image_preview() {
        return ad_image_preview;
    }

    public void setAd_image_preview(String ad_image_preview) {
        this.ad_image_preview = ad_image_preview;
    }

    public String getAd_image_full() {
        return ad_image_full;
    }

    public void setAd_image_full(String ad_image_full) {
        this.ad_image_full = ad_image_full;
    }

    private String ad_title;
    private String ad_image_preview;
    private String ad_image_full;



}
