package com.mandasur.app.data.source.remote;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
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
        FormBody.Builder formBodybuilder=getInstanceOfFormBodyBuilder();
        for (String key:requestParam.keySet()){
            formBodybuilder.add(key,requestParam.get(key));
        }

        FormBody formBody=formBodybuilder.build();
        Request request=getInstanceOfRequestBuilder().url(requestUrl).put(formBody)
                .build();



        try {
            response= getOkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


    private synchronized  static FormBody.Builder getInstanceOfFormBodyBuilder(){

        if (formBody==null){

            formBody=new FormBody.Builder();
        }


        return formBody;
    }

    private synchronized static Request.Builder getInstanceOfRequestBuilder(){
        if (builder==null){

            builder=new Request.Builder();
        }


        return builder;
    }
    private synchronized static OkHttpClient  getOkHttpClient(){





        if (okHttpClient==null){
            okHttpClient=new OkHttpClient();
        }





        return okHttpClient;



    }

}
