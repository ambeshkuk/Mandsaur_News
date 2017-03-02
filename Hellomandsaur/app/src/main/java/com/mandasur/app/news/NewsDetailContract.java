package com.mandasur.app.news;

import android.content.Context;

import com.mandasur.app.BasePresenter;
import com.mandasur.app.BaseView;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailsFromResponse;

/**
 * Created by ambesh on 25-02-2017.
 */
public interface NewsDetailContract {


    interface NewsDetailView extends BaseView<NewsDetailsPresenter>{
        void showNewsDetailsToScreen(NewsDetailsFromResponse showNewsDetailsToScreen);
        void showProgressBar();
        void showNewsImage();
        void showNetworkNotAvaible();
        void showErrorMessage(String errorMessage);
    }





interface NewsDetailsPresenter extends BasePresenter{
     void fetchNewsDetailsFromServer();
        void fetchNewsAdvertisingFromServer();
     boolean checkIfNetworkIsAvalible(Context context);
}
}
