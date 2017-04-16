package com.mandasur.app.data.source.dao.requestdao;

/**
 * Created by ambesh on 27-02-2017.
 */
public class NewsDetail {
public static final String FID="id";
    public static final String TITLE="title";
    public static final String DESCR="descr";
    public static final String DESCR2="descr2";

    public static final String IMAGE="image";

    public static final String PLACE="place";
    public static final String DATE="date";

    private String id,title,descr
            ,descr2,place,date;

    public String getNewsurl() {
        return newsurl;
    }

    public void setNewsurl(String newsurl) {
        this.newsurl = newsurl;
    }

    private String newsurl;

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    private String views;
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDescr2() {
        return descr2;
    }

    public void setDescr2(String descr2) {
        this.descr2 = descr2;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;





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

    public News mapToNews(){
        News news=new News();
        news.setImage(image);
        news.setTitle(title);
        news.setDate(date);
        news.setId(id);


        return news;

    }
}
