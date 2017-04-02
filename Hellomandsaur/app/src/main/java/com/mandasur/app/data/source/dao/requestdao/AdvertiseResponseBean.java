package com.mandasur.app.data.source.dao.requestdao;

import com.mandasur.app.data.source.dao.Ads;

import java.util.ArrayList;

/**
 * Created by ambesh on 02-04-2017.
 */
public class AdvertiseResponseBean {
    private ArrayList<Ads> ads;

    public ArrayList<Ads> getAds() {
        return ads;
    }

    public void setAds(ArrayList<Ads> ads) {
        this.ads = ads;
    }
}
