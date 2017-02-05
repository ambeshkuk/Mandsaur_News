package com.mandasur.app.data.source.dao;

/**
 * Created by inno on 12/31/2016.
 */
public class News {
    String img;
    String dt;
    String title;

    public News() {

    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    String nid;
    String desc;
    String longdesv;

    public News(String nid,String img, String dt, String title, String desc, String longdesv) {
        this.img = img;
        this.dt = dt;
        this.title = title;
        this.desc = desc;
        this.longdesv = longdesv;
        this.nid=nid;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLongdesv() {
        return longdesv;
    }

    public void setLongdesv(String longdesv) {
        this.longdesv = longdesv;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }



}
