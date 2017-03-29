package com.mandasur.app.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mandasur.app.data.source.NewsDataRepository;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;

/**
 * Created by ambesh on 12-02-2017.
 */
public class GsonUtil {






    private static Gson gson;

    public static Gson getGsonInstance(){

        if (gson==null){
            GsonBuilder gsonBuilder=new GsonBuilder().serializeNulls();



            gsonBuilder.registerTypeAdapter(NewsFromMainCategoryResponse.class,
                    new NewsDataRepository.NewsListDesireliser());

            gson=gsonBuilder.create();

        }
        return gson;
    }








}
