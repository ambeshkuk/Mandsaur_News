package com.mandasur.app.news;

import android.content.Context;

/**
 * Created by ambesh on 25-02-2017.
 */
public class NewsDetailPresenter implements NewsDetailContract.NewsDetailsPresenter{
    @Override
    public void fetchNewsDetailsFromServer(String newsId) {

    }

    @Override
    public void fetchNewsAdvertisingFromServer() {

    }

    @Override
    public boolean checkIfNetworkIsAvalible(Context context) {
        return false;
    }

    @Override
    public void start() {

    }
}
