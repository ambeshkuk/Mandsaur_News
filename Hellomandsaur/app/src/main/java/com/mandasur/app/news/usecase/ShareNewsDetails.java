package com.mandasur.app.news.usecase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetail;

import java.util.ArrayList;

/**
 * Created by ambesh on 18-03-2017.
 */
public class ShareNewsDetails extends UseCase<ShareNewsDetails.RequestValues,ShareNewsDetails.ResponseValue>{


    private Context sharingActivity;
        public ShareNewsDetails(Context sharingActivity) {

            this.sharingActivity=sharingActivity;
        }

    @Override
    public void executeUseCase(RequestValues requestValues) {



        Intent sharingIntent= new Intent();
        sharingIntent.setAction(Intent.ACTION_SEND);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, requestValues.getNews().getTitle() + "\n\n"
                + requestValues.getNews().getNewsUrl()!=null?requestValues.getNews().getNewsUrl():"");
        sharingIntent.setType("text/plain");

        sharingActivity.startActivity(sharingIntent);



    }

    public static final class RequestValues implements UseCase.RequestValues {

        private News news;

        public News getNews() {
            return news;
        }

        public void setNews(News news) {
            this.news = news;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {





    }
}
