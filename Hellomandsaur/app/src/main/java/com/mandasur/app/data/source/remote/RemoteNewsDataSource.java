package com.mandasur.app.data.source.remote;

import com.mandasur.app.data.source.NewsAppDataSourceInterface;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.dao.requestdao.Request;

import java.util.HashMap;

import okhttp3.Response;

/**
 * Data source Class to fetch the news list from server
 * Created by ambesh on 09-02-2017.
 */
public class RemoteNewsDataSource {



    public Response getNewsListForCategory(NewsFromMainCategoryRequest newsFromMainCategoryRequest,String requestUrl){


                HashMap<String,String> requestParms=new HashMap<>();

               return OkHttpClientUtils.perfromHttpRequestForPostRequest(requestParms,requestUrl);

    }

}
