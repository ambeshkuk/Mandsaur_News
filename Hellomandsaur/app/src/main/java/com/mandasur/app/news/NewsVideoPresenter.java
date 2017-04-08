package com.mandasur.app.news;

import android.content.Context;

import com.mandasur.app.R;
import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailFromIdRequest;
import com.mandasur.app.news.usecase.GetNewsDetailsFromServer;
import com.mandasur.app.news.usecase.ShareNewsDetails;
import com.mandasur.app.util.ActivityUtil;

/**
 * Created by ambesh on 25-02-2017.
 */
public class NewsVideoPresenter implements NewsDetailContract.NewsDetailsPresenter{

    private final String NEWS="news";
    private GetNewsDetailsFromServer getNewsDetailsFromServer;
    private ShareNewsDetails shareNewsDetails;
    private NewsVideoActivity newsVideoActivity;
    private String newsId;

    public NewsVideoPresenter(GetNewsDetailsFromServer getNewsDetailsFromServer
            , ShareNewsDetails shareNewsDetails
            , NewsVideoActivity newsVideoActivity, String newsId){
        this.getNewsDetailsFromServer=getNewsDetailsFromServer;
        this.shareNewsDetails=shareNewsDetails;
        this.newsVideoActivity=newsVideoActivity;
        this.newsId=newsId;
        newsVideoActivity.setPresenter(this);

    }
    @Override
    public void fetchNewsDetailsFromServer() {



        if (!checkIfNetworkIsAvalible(newsVideoActivity)){
            newsVideoActivity.showNetworkNotAvaible();
            return;
        }


        NewsDetailFromIdRequest newsDetailFromIdRequest=new NewsDetailFromIdRequest();
        newsDetailFromIdRequest.put(NewsDetailFromIdRequest.NEWS_ID,newsId);
        newsDetailFromIdRequest.put(NewsDetailFromIdRequest.NEWS_TYPE,NEWS);
        newsDetailFromIdRequest.put(NewsDetailFromIdRequest.REQUEST_URL
                , newsVideoActivity.getString(R.string.baseUrl)
                + newsVideoActivity.getString(R.string.newsDetails));
        GetNewsDetailsFromServer.RequestValues requestValues=new GetNewsDetailsFromServer.RequestValues();
        requestValues.setNewsDetailFromIdRequest(newsDetailFromIdRequest);

        getNewsDetailsFromServer.setUseCaseCallback(new UseCase.UseCaseCallback<GetNewsDetailsFromServer.ResponseValue>() {
            @Override
            public void onSuccess(GetNewsDetailsFromServer.ResponseValue response) {

                newsVideoActivity.showNewsDetailsToScreen(response.getNewsDetailsFromResponse());
            }

            @Override
            public void onError(String erroMessage) {



                newsVideoActivity.showErrorMessage(erroMessage);
            }
        });
        getNewsDetailsFromServer.executeUseCase(requestValues);
        newsVideoActivity.showProgressBar();

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
