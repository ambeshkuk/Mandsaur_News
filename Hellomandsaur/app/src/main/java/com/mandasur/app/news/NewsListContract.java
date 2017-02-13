package com.mandasur.app.news;

import android.content.Context;

import com.mandasur.app.BasePresenter;
import com.mandasur.app.BaseView;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;

/**
 * Created by ambesh on 12-02-2017.
 */
public interface NewsListContract {



    interface NewsListView extends BaseView<NewsListPresenter>{

        void showNetworkNotAvailbel();
        void showLoadingIndicator();
        void showNewsListingBasedOnFilter(NewsFromMainCategoryResponse newsFromMainCategoryResponse);
        void showErrorOccured(String errorMessage);
    }



    interface NewsListPresenter extends BasePresenter{

        boolean checkIfNetworkIsAvalible(Context context);

        void fetchNewsFromServerBasedOnFiltre(String filterArray);


    }

}
