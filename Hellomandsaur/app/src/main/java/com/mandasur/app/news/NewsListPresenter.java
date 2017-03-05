package com.mandasur.app.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mandasur.app.R;
import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.NewsDataRepository;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.news.NewsList.NewsListFragment;
import com.mandasur.app.news.usecase.GetNewsListByCategory;
import com.mandasur.app.util.ActivityUtil;

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


            boolean isNetworkAvalible=ActivityUtil.isNetworkAvaliable(context);

        if (!isNetworkAvalible){
            newsListFragment.showNetworkNotAvailbel();
        }
        return  isNetworkAvalible;
    }

    @Override
    public void fetchNewsFromServerBasedOnFiltre(String filterArray) {

            if (!checkIfNetworkIsAvalible(newsListFragment.getActivity())&&!categroyName.equals(newsListFragment.getString(R.string.bookMarkedNews))){
             return;
            }



        NewsFromMainCategoryRequest newsFromMainCategoryRequest=new NewsFromMainCategoryRequest();


        if (categroyName.equals("Main News")){
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.REQUEST_URL,newsListFragment.getString(R.string.baseUrl)
                    +newsListFragment.getString(R.string.mainNews));
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.CAT,categroyName);
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.SUB_CAT
                    , DatabaseNewsDataSource.getInstance(newsListFragment.getActivity())
                    .getSubCategoriesTable().
                            getStringArrayIfSelectedSubCategory(DatabaseNewsDataSource.getInstance(newsListFragment.getActivity())
                                    .getSqLiteDatabase()));
        }
        else if (categroyName.equals("bookmarked")){
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.REQUEST_URL, newsListFragment.getString(R.string.bookMarkedNews));
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.CATEGORY, categroyName);
        }
        else{
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.REQUEST_URL, newsListFragment.getString(R.string.baseUrl) + newsListFragment.getString(R.string.anyNews));
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.CATEGORY, categroyName);
        }


        GetNewsListByCategory.RequestValues requestValues=new GetNewsListByCategory.RequestValues();
        requestValues.setNewsFromMainCategoryRequest(newsFromMainCategoryRequest);
        getNewsListByCategory.setUseCaseCallback(new UseCase.UseCaseCallback<GetNewsListByCategory.ResponseValue>() {
            @Override
            public void onSuccess(GetNewsListByCategory.ResponseValue response) {
                ActivityUtil.Log("News By Category>>",categroyName);
                newsListFragment.showNewsListingBasedOnFilter(response.getNewsFromMainCategoryResponse());

            }

            @Override
            public void onError(String errorMessage) {
                newsListFragment.showErrorOccured(errorMessage);
            }
        });
        getNewsListByCategory.executeUseCase(requestValues);
        newsListFragment.showLoadingIndicator();
    }

    @Override
    public void start() {

        fetchNewsFromServerBasedOnFiltre("all");

    }
}
