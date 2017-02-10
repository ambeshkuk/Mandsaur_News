package com.mandasur.app.data.source;

import com.mandasur.app.data.source.dao.requestdao.Request;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.data.source.remote.OkHttpClientUtils;
import com.mandasur.app.data.source.remote.RemoteNewsDataSource;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * Concrete implmentation to load news from database to cache.
 * Created by ambesh on 29-01-2017.
 */
public class NewsDataRepository implements NewsAppDataSourceInterface{



    private RemoteNewsDataSource remoteNewsDataSource;
    private DatabaseNewsDataSource databaseNewsDataSource;

    public NewsDataRepository(RemoteNewsDataSource remoteNewsDataSource,DatabaseNewsDataSource databaseNewsDataSource){

        this.remoteNewsDataSource=remoteNewsDataSource;
        this.databaseNewsDataSource=databaseNewsDataSource;
    }


    @Override
    public <NewsFromMainCategoryRequest extends Request> void getNewsListOnMainTabs(NewsFromMainCategoryRequest request, LoadNewsCallBack loadNewsCallBack) {

       Response response= OkHttpClientUtils.perfromHttpRequestForPostRequest(request, request.get(Request.REQUEST_URL));

        if (response!=null){

            if (response.isSuccessful()){
                try {
                    String jsonData=response.body().string();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        else{

        }
    }

    @Override
    public <T extends Request> void getNewsListOnSubCategories(T request, LoadNewsCallBack loadNewsCallBack) {

    }

    @Override
    public <T extends Request> void getNewsDetailsFromId(T request, GetNewsDetailsOfNews getNewsDetailsOfNews) {

    }
}
