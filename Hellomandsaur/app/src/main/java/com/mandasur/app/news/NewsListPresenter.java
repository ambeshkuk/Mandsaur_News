package com.mandasur.app.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.NewsDataRepository;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.news.NewsList.NewsListFragment;
import com.mandasur.app.news.usecase.GetNewsListByCategory;

import okhttp3.internal.Util;

/**
 * Created by ambesh on 12-02-2017.
 */
public class NewsListPresenter implements NewsListContract.NewsListPresenter {



    private GetNewsListByCategory getNewsListByCategory;
    private NewsListFragment newsListFragment;

    private String categroyName;
    public NewsListPresenter(GetNewsListByCategory getNewsListByCategory, NewsListFragment newsListFragment,String categroyName) {
        this.getNewsListByCategory = getNewsListByCategory;
        this.newsListFragment = newsListFragment;
        this.categroyName=categroyName;
        newsListFragment.setPresenter(this);
    }

    @Override
    public boolean checkIfNetworkIsAvalible(Context context) {
        boolean isNetwokAvailable = false;
        ConnectivityManager connectionManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectionManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectionManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifiInfo != null && wifiInfo.isConnected()) {
            isNetwokAvailable = true;
        } else if (mobileInfo != null && mobileInfo.isConnected()) {
            isNetwokAvailable = true;
        } else {

            isNetwokAvailable = false;
        }

        if(!isNetwokAvailable) {


           newsListFragment.showNetworkNotAvailbel();

        }
        return isNetwokAvailable;


    }

    @Override
    public void fetchNewsFromServerBasedOnFiltre(String filterArray) {


        Log.i(NewsListContract.NewsListPresenter.class.getSimpleName(),"fetchNewsFromServerBasedOnFiltre");


        NewsFromMainCategoryRequest newsFromMainCategoryRequest=new NewsFromMainCategoryRequest();


        newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.REQUEST_URL,"http://www.hellomandsaur.com/webapi/mainnews_Fromall_Cat.php");
        newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.CAT,"Main News");
        newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.SUB_CAT, "sports,socal,govt,political,near,crime,awaz,scheme,programme");

        GetNewsListByCategory.RequestValues requestValues=new GetNewsListByCategory.RequestValues();
        requestValues.setNewsFromMainCategoryRequest(newsFromMainCategoryRequest);
        getNewsListByCategory.setUseCaseCallback(new UseCase.UseCaseCallback<GetNewsListByCategory.ResponseValue>() {
            @Override
            public void onSuccess(GetNewsListByCategory.ResponseValue response) {

                newsListFragment.showNewsListingBasedOnFilter(response.getNewsFromMainCategoryResponse());

            }

            @Override
            public void onError() {

            }
        });
        getNewsListByCategory.executeUseCase(requestValues);

    }

    @Override
    public void start() {

        fetchNewsFromServerBasedOnFiltre("all");

    }
}
