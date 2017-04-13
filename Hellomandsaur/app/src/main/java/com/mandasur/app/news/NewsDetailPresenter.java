package com.mandasur.app.news;

import android.content.Context;

import com.mandasur.app.R;
import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailFromIdRequest;
import com.mandasur.app.news.usecase.GetNewsDetailsFromServer;
import com.mandasur.app.news.usecase.GetNewsListByCategory;
import com.mandasur.app.news.usecase.ShareNewsDetails;
import com.mandasur.app.util.ActivityUtil;

/**
 * Created by ambesh on 25-02-2017.
 */
public class NewsDetailPresenter implements NewsDetailContract.NewsDetailsPresenter{

    private final String NEWS="news";
    private GetNewsDetailsFromServer getNewsDetailsFromServer;
    private ShareNewsDetails shareNewsDetails;
    private NewsDetailsActivity newsDetailsActivity;
    private String newsId;

    public NewsDetailPresenter(GetNewsDetailsFromServer getNewsDetailsFromServer,ShareNewsDetails shareNewsDetails
            ,NewsDetailsActivity newsDetailsActivity,String newsId){
        this.getNewsDetailsFromServer=getNewsDetailsFromServer;
        this.shareNewsDetails=shareNewsDetails;
        this.newsDetailsActivity=newsDetailsActivity;
        this.newsId=newsId;
        newsDetailsActivity.setPresenter(this);

    }
    @Override
    public void fetchNewsDetailsFromServer() {



        if (!checkIfNetworkIsAvalible(newsDetailsActivity)){
            newsDetailsActivity.showNetworkNotAvaible();
            return;
        }


        NewsDetailFromIdRequest newsDetailFromIdRequest=new NewsDetailFromIdRequest();
        newsDetailFromIdRequest.put(NewsDetailFromIdRequest.NEWS_ID,newsId);

        newsDetailFromIdRequest.put(NewsDetailFromIdRequest.REQUEST_URL
                , newsDetailsActivity.getString(R.string.baseUrl)
                + newsDetailsActivity.getString(R.string.newsDetails));
        GetNewsDetailsFromServer.RequestValues requestValues=new GetNewsDetailsFromServer.RequestValues();
        requestValues.setNewsDetailFromIdRequest(newsDetailFromIdRequest);

        getNewsDetailsFromServer.setUseCaseCallback(new UseCase.UseCaseCallback<GetNewsDetailsFromServer.ResponseValue>() {
            @Override
            public void onSuccess(GetNewsDetailsFromServer.ResponseValue response) {

                newsDetailsActivity.showNewsDetailsToScreen(response.getNewsDetailsFromResponse());
            }

            @Override
            public void onError(String erroMessage) {



                newsDetailsActivity.showErrorMessage(erroMessage);
            }
        });
        getNewsDetailsFromServer.executeUseCase(requestValues);
        newsDetailsActivity.showProgressBar();

    }

    @Override
    public void fetchNewsAdvertisingFromServer() {

    }

    @Override
    public boolean checkIfNetworkIsAvalible(Context context) {
        return ActivityUtil.isNetworkAvaliable(context);
    }

    @Override
    public void shareNewsOnSocialMedia(News news) {

        ShareNewsDetails.RequestValues requestValues=new ShareNewsDetails.RequestValues();
        requestValues.setNews(news);;
        shareNewsDetails.executeUseCase(requestValues);


    }

    @Override
    public void start() {

    }

}
