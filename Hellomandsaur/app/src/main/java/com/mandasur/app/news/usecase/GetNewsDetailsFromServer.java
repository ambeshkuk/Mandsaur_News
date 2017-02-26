package com.mandasur.app.news.usecase;

import android.os.AsyncTask;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.NewsAppDataSourceInterface;
import com.mandasur.app.data.source.NewsDataRepository;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailFromIdRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailsFromResponse;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;

/**
 * Created by ambesh on 25-02-2017.
 */
public class GetNewsDetailsFromServer extends UseCase<GetNewsDetailsFromServer.RequestValues,GetNewsDetailsFromServer.ResponseValue> {


    private NewsDataRepository newsDataRepository;
    private GetNewsDetailsAsyncTask getNewsDetailsAsyncTask;
    public GetNewsDetailsFromServer(NewsDataRepository newsDataRepository) {
        this.newsDataRepository = newsDataRepository;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {
            getNewsDetailsAsyncTask=new GetNewsDetailsAsyncTask();
        getNewsDetailsAsyncTask.execute(requestValues);
    }

    public static final class RequestValues implements UseCase.RequestValues {


        private NewsDetailFromIdRequest newsDetailFromIdRequest;


        public void setNewsDetailFromIdRequest(NewsDetailFromIdRequest newsDetailFromIdRequest){
            this.newsDetailFromIdRequest=newsDetailFromIdRequest;
        }

        public NewsDetailFromIdRequest getNewsDetailFromIdRequest(){
            return newsDetailFromIdRequest;
        }




    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private NewsDetailsFromResponse newsDetailsFromResponse;

        public NewsDetailsFromResponse getNewsDetailsFromResponse() {
            return newsDetailsFromResponse;
        }

        public void setNewsDetailsFromResponse(NewsDetailsFromResponse newsDetailsFromResponse) {
            this.newsDetailsFromResponse = newsDetailsFromResponse;
        }
    }


    private class GetNewsDetailsAsyncTask
            extends AsyncTask<GetNewsDetailsFromServer.RequestValues,Void,GetNewsDetailsFromServer.ResponseValue>{

        @Override
        protected ResponseValue doInBackground(RequestValues... params) {

            GetNewsDetailsFromServer.ResponseValue responseValue=new ResponseValue();

            NewsDetailsFromResponse newsDetailsFromResponse=newsDataRepository
                    .getNewsDetailsFromId(params[0].getNewsDetailFromIdRequest());


            responseValue.setNewsDetailsFromResponse(newsDetailsFromResponse);
            return responseValue;


        }


        @Override
        protected void onPostExecute(ResponseValue responseValue) {
            super.onPostExecute(responseValue);

            getUseCaseCallback().onSuccess(responseValue);
        }
    }
}
