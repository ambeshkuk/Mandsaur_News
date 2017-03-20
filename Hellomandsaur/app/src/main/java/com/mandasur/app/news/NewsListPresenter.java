package com.mandasur.app.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.mandasur.app.R;
import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.NewsDataRepository;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.news.NewsList.NewsListFragment;
import com.mandasur.app.news.usecase.GetNewsListByCategory;
import com.mandasur.app.news.usecase.ShareNewsDetails;
import com.mandasur.app.util.ActivityUtil;

import okhttp3.internal.Util;

/**
 * Created by ambesh on 12-02-2017.
 */
public class NewsListPresenter implements NewsListContract.NewsListPresenter {



    private GetNewsListByCategory getNewsListByCategory;
    private NewsListContract.NewsListView newsListFragment;
    private ShareNewsDetails shareNewsDetails;


    private int pageNumber=1;
    private String categroyName;
    public NewsListPresenter(GetNewsListByCategory getNewsListByCategory,ShareNewsDetails shareNewsDetails
            , NewsListContract.NewsListView newsListFragment,String categroyName) {
        this.getNewsListByCategory = getNewsListByCategory;
        this.newsListFragment = newsListFragment;
        this.categroyName=categroyName;
    this.shareNewsDetails=shareNewsDetails;
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

    public int getPageNumber() {
        return pageNumber;
    }
    @Override
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public void shareNewsOnSocialMedia(News news) {
        ShareNewsDetails.RequestValues requestValues=new ShareNewsDetails.RequestValues();
        requestValues.setNews(news);;
        shareNewsDetails.executeUseCase(requestValues);
    }

    @Override
    public void fetchNewsFromServerBasedOnFiltre(String filterName) {

            if (!checkIfNetworkIsAvalible(newsListFragment.getContext())&&!categroyName.equals(newsListFragment.getContext().getString(R.string.bookMarkedNews))){
             return;
            }



        NewsFromMainCategoryRequest newsFromMainCategoryRequest=new NewsFromMainCategoryRequest();






       if (!TextUtils.isEmpty(filterName)){
           newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.REQUEST_URL
                   ,newsListFragment.getContext().getString(R.string.baseUrl)
                   +newsListFragment.getContext().getString(R.string.newsSingleSubCat));
           newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.SUB_CAT,filterName);
           newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.PAGE_NO,pageNumber+"");

       }
       else if (categroyName.equals("Main News")){
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.REQUEST_URL,newsListFragment.getContext().getString(R.string.baseUrl)
                    +newsListFragment.getContext().getString(R.string.mainNews));
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.CAT,categroyName);
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.SUB_CAT
                    , DatabaseNewsDataSource.getInstance(newsListFragment.getContext())
                    .getSubCategoriesTable().
                            getStringArrayIfSelectedSubCategory(DatabaseNewsDataSource.getInstance(newsListFragment.getContext())
                                    .getSqLiteDatabase()));
        }
        else if (categroyName.equals("bookmarked")){
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.REQUEST_URL, newsListFragment.getContext().getString(R.string.bookMarkedNews));
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.CATEGORY, categroyName);
        }

        else{
            newsFromMainCategoryRequest.put(NewsFromMainCategoryRequest.REQUEST_URL, newsListFragment.getContext().getString(R.string.baseUrl) + newsListFragment.getContext().getString(R.string.anyNews));
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

        fetchNewsFromServerBasedOnFiltre("");

    }
}
