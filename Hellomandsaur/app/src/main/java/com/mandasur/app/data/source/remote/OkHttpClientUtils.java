package com.mandasur.app.data.source.remote;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Utils class to handel http request from the server.
 * Created by ambesh on 09-02-2017.
 */
public class OkHttpClientUtils {


    private static OkHttpClient okHttpClient;

    private static Request.Builder builder;
    private static FormBody.Builder formBody;
    public static Response perfromHttpRequestForPostRequest(HashMap<String,String> requestParam,String requestUrl){

        Response response=null;
        String jsonResponse=null;
        MultipartBody.Builder  formBodybuilder=getInstanceOfFormBodyBuilder();





        for (String key:requestParam.keySet()){
            if (!key.equals(com.mandasur.app.data.source.dao.requestdao.Request.REQUEST_URL)||!key.equals(com.mandasur.app.data.source.dao.requestdao.Request.IS_NETWORK_AVALIBLE)){
                formBodybuilder.addFormDataPart(key, requestParam.get(key));
            }

        }



        Request request;
        if (requestParam.size()==0){
            request=getInstanceOfRequestBuilder().url(requestUrl).build();
        }
        else {
            MultipartBody formBody=formBodybuilder.build();
            request=getInstanceOfRequestBuilder().url(requestUrl).post(formBody)
                    .build();
        }




        try {
            response= getOkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


//    private synchronized  static FormBody.Builder getInstanceOfFormBodyBuilder(){
//
//        if (formBody==null){
//
//            formBody=new FormBody.Builder();
//        }
//
//
//        return formBody;
//    }

    private synchronized  static MultipartBody.Builder getInstanceOfFormBodyBuilder(){

        return new MultipartBody.Builder().setType(MultipartBody.FORM);
//        if (formBody==null){
//
//            formBody=new FormBody.Builder();
//        }
//
//
//        return formBody;
    }

    private synchronized static Request.Builder getInstanceOfRequestBuilder(){
        if (builder==null){

            builder=new Request.Builder();

        }


        return builder;
    }
    private synchronized static OkHttpClient  getOkHttpClient(){





        if (okHttpClient==null){
            okHttpClient=new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS).build();

        }





        return okHttpClient;



    }

}
