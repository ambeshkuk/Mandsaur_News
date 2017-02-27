package com.mandasur.app.news;

import android.content.Context;

import com.mandasur.app.R;
import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailFromIdRequest;
import com.mandasur.app.news.usecase.GetNewsDetailsFromServer;
import com.mandasur.app.news.usecase.GetNewsListByCategory;

/**
 * Created by ambesh on 25-02-2017.
 */
public class NewsDetailPresenter implements NewsDetailContract.NewsDetailsPresenter{

    private final String NEWS="news";
    private GetNewsDetailsFromServer getNewsDetailsFromServer;
    private NewsDetailsActivity newsDetailsActivity;
    private String newsId;

    public NewsDetailPresenter(GetNewsDetailsFromServer getNewsDetailsFromServer
            ,NewsDetailsActivity newsDetailsActivity,String newsId){
        this.getNewsDetailsFromServer=getNewsDetailsFromServer;
        this.newsDetailsActivity=newsDetailsActivity;
        this.newsId=newsId;
        newsDetailsActivity.setPresenter(this);

    }
    @Override
    public void fetchNewsDetailsFromServer() {



        if (!checkIfNetworkIsAvalible(newsDetailsActivity)){
            return;
        }


        NewsDetailFromIdRequest newsDetailFromIdRequest=new NewsDetailFromIdRequest();
        newsDetailFromIdRequest.put(NewsDetailFromIdRequest.NEWS_ID,newsId);
        newsDetailFromIdRequest.put(NewsDetailFromIdRequest.NEWS_TYPE,NEWS);
        newsDetailFromIdRequest.put(NewsDetailFromIdRequest.REQUEST_URL
                , newsDetailsActivity.getString(R.string.baseUrl)
                + newsDetailsActivity.getString(R.string.newsDetails));
        GetNewsDetailsFromServer.RequestValues requestValues=new GetNewsDetailsFromServer.RequestValues();
        requestValues.setNewsDetailFromIdRequest(newsDetailFromIdRequest);

        getNewsDetailsFromServer.setUseCaseCallback(new UseCase.UseCaseCallback<GetNewsDetailsFromServer.ResponseValue>() {
            @Override
            public void onSuccess(GetNewsDetailsFromServer.ResponseValue response) {

                newsDetailsActivity.showNewsDetailsToScreen();
            }

            @Override
            public void onError() {

            }
        });
        getNewsDetailsFromServer.executeUseCase(requestValues);

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
