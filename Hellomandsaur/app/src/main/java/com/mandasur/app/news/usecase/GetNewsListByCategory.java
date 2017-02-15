package com.mandasur.app.news.usecase;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.NewsAppDataSourceInterface;
import com.mandasur.app.data.source.NewsDataRepository;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.mandasur.app.data.source.dao.requestdao.Response;
import com.mandasur.app.news.NewsListContract;

import java.util.ArrayList;

/**
 * Created by ambesh on 10-02-2017.
 */
public class GetNewsListByCategory extends UseCase<GetNewsListByCategory.RequestValues,GetNewsListByCategory.ResponseValue> {

    private NewsDataRepository newsDataRepository;
    private FetchNewsFromCategory fetchNewsFromCategory;
    public GetNewsListByCategory(NewsDataRepository newsDataRepository){

        this.newsDataRepository=newsDataRepository;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {

        fetchNewsFromCategory=new FetchNewsFromCategory();
        fetchNewsFromCategory.execute(requestValues);


    }


    public static final class RequestValues implements UseCase.RequestValues {


        private NewsFromMainCategoryRequest newsFromMainCategoryRequest;


        public void setNewsFromMainCategoryRequest(NewsFromMainCategoryRequest newsFromMainCategoryRequest){
            this.newsFromMainCategoryRequest=newsFromMainCategoryRequest;
        }

        public NewsFromMainCategoryRequest getNewsFromMainCategoryRequest(){
            return newsFromMainCategoryRequest;
        }




    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private NewsFromMainCategoryResponse newsFromMainCategoryResponse;

        public NewsFromMainCategoryResponse getNewsFromMainCategoryResponse() {
            return newsFromMainCategoryResponse;
        }

        public void setNewsFromMainCategoryResponse(NewsFromMainCategoryResponse newsFromMainCategoryResponse) {
            this.newsFromMainCategoryResponse = newsFromMainCategoryResponse;
        }
    }


    /****
     * Async task to fetch the news by category.
     */

    private class FetchNewsFromCategory extends AsyncTask<GetNewsListByCategory.RequestValues,Void,GetNewsListByCategory.ResponseValue>{




        @Override
        protected GetNewsListByCategory.ResponseValue doInBackground(GetNewsListByCategory.RequestValues... params) {
            Log.i(FetchNewsFromCategory.class.getSimpleName(), "doInBackground");
            GetNewsListByCategory.ResponseValue responseValue=new ResponseValue();





            NewsFromMainCategoryResponse newsFromMainCategoryResponse=newsDataRepository.getNewsListOnMainTabs(params[0].getNewsFromMainCategoryRequest());
            responseValue.setNewsFromMainCategoryResponse(newsFromMainCategoryResponse);
            return responseValue;
        }

        @Override
        protected void onPostExecute(ResponseValue responseValue) {
            super.onPostExecute(responseValue);
            Log.i(FetchNewsFromCategory.class.getSimpleName(), "onPostExecute");
            getUseCaseCallback().onSuccess(responseValue);
        }
    }
}
