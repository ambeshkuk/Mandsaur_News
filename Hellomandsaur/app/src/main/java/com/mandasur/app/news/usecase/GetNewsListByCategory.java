package com.mandasur.app.news.usecase;

import android.support.annotation.NonNull;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.NewsAppDataSourceInterface;
import com.mandasur.app.data.source.NewsDataRepository;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.mandasur.app.data.source.dao.requestdao.Response;

import java.util.ArrayList;

/**
 * Created by ambesh on 10-02-2017.
 */
public class GetNewsListByCategory extends UseCase<GetNewsListByCategory.RequestValues,GetNewsListByCategory.ResponseValue> {

    private NewsDataRepository newsDataRepository;
    public GetNewsListByCategory(NewsDataRepository newsDataRepository){

        this.newsDataRepository=newsDataRepository;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {


        newsDataRepository.getNewsListOnMainTabs(requestValues.getNewsFromMainCategoryRequest(),loadNewsCallBack);

    }

    private NewsDataRepository.LoadNewsCallBack<NewsFromMainCategoryResponse> loadNewsCallBack=new NewsAppDataSourceInterface.LoadNewsCallBack<NewsFromMainCategoryResponse>() {
        @Override
        public void onNewsLoadedSuccesfully(NewsFromMainCategoryResponse response) {

            ResponseValue responseValue=new ResponseValue();
            responseValue.setNewsFromMainCategoryResponse(response);
            getUseCaseCallback().onSuccess(responseValue);

        }

        @Override
        public boolean onDataLoadingUnsuccessful() {
            getUseCaseCallback().onError();
            return false;
        }
    };
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
}
