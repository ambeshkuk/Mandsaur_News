package com.mandasur.app.news;

import android.content.Context;

import com.mandasur.app.BasePresenter;
import com.mandasur.app.BaseView;

/**
 * Created by ambesh on 25-02-2017.
 */
public interface NewsDetailContract {


    interface NewsDetailView extends BaseView<NewsDetailsPresenter>{
        void showNewsDetailsToScreen();
        void showProgressBar();
        void showNewsImage();
        void showNetworkNotAvaible();
    }





interface NewsDetailsPresenter extends BasePresenter{
     void fetchNewsDetailsFromServer();
        void fetchNewsAdvertisingFromServer();
     boolean checkIfNetworkIsAvalible(Context context);
}
}
