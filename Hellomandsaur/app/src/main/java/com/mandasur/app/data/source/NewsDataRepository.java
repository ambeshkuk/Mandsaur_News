package com.mandasur.app.data.source;

import android.content.Context;
import android.text.Html;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.requestdao.Data;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetail;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailFromIdRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailsFromResponse;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.mandasur.app.data.source.dao.requestdao.Request;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.data.source.remote.OkHttpClientUtils;
import com.mandasur.app.data.source.remote.RemoteNewsDataSource;
import com.mandasur.app.news.exceptions.ErrorMessageHandler;
import com.mandasur.app.news.exceptions.ErrorRequestCode;
import com.mandasur.app.util.ActivityUtil;
import com.mandasur.app.util.GsonUtil;


import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import okhttp3.Response;

/**
 * Concrete implmentation to load news from database to cache.
 * Created by ambesh on 29-01-2017.
 */
public class NewsDataRepository implements NewsAppDataSourceInterface{



    private RemoteNewsDataSource remoteNewsDataSource;

    private static DatabaseNewsDataSource databaseNewsDataSource;

    private ErrorMessageHandler errorHandler;
    private Context context;
    public NewsDataRepository(RemoteNewsDataSource remoteNewsDataSource
            ,DatabaseNewsDataSource databaseNewsDataSource,Context context){

        this.remoteNewsDataSource=remoteNewsDataSource;
        this.databaseNewsDataSource=databaseNewsDataSource;



        this.context=context;
        errorHandler=ErrorMessageHandler.getInstance(context);
    }


    @Override
    public  NewsFromMainCategoryResponse getNewsListOnMainTabs(NewsFromMainCategoryRequest request) {


        NewsFromMainCategoryResponse newsFromMainCategoryResponse = null;

            ActivityUtil.log(NewsDataRepository.class.getSimpleName(),"Request URl>>"+request.get(NewsFromMainCategoryRequest.REQUEST_URL));
        ActivityUtil.log(NewsDataRepository.class.getSimpleName(),"Request Params>>"+request.toString());


        if (request.get(NewsDetailFromIdRequest.REQUEST_URL).equals(context.getString(R.string.bookMarkedNews))){
            MandsaurDataBaseHelper mandsaurDataBaseHelper=databaseNewsDataSource.getMandsaurDataBaseHelper();

            if (mandsaurDataBaseHelper.getSavedNewsTable().isSavedNewsEmpty(mandsaurDataBaseHelper.getSqLiteDatabase())){
            newsFromMainCategoryResponse=new NewsFromMainCategoryResponse();
                newsFromMainCategoryResponse.setStatus("0");
                newsFromMainCategoryResponse.setMsg(errorHandler.getApiErrorMessage(ErrorRequestCode.API_DB_ERROR_REQUEST_CODE.ERROR_CODE_DB_ERROR));
            }
            else{
                newsFromMainCategoryResponse=new NewsFromMainCategoryResponse();
                ArrayList<News> newsArrayList=mandsaurDataBaseHelper.getSavedNewsTable().getSavedNewsList(mandsaurDataBaseHelper.getSqLiteDatabase());

                Data data=new Data();
                data.setNewsList(newsArrayList);
                newsFromMainCategoryResponse.setData(data);
                newsFromMainCategoryResponse.setStatus("1");
            }


            return newsFromMainCategoryResponse;

        }


        if (request.get(NewsFromMainCategoryRequest.IS_NETWORK_AVALIBLE).equals(NewsFromMainCategoryRequest.FALSE)){
            newsFromMainCategoryResponse=new NewsFromMainCategoryResponse();
            MandsaurDataBaseHelper mandsaurDataBaseHelper=databaseNewsDataSource.getMandsaurDataBaseHelper();
            String mainCategory=request.get(NewsFromMainCategoryRequest.MAIN_CAT);
            if (!mandsaurDataBaseHelper.getCachedNewsTable()
                    .isNewsTableEmpty(mandsaurDataBaseHelper.getSqLiteDatabase()
                            , mainCategory)){

                StringBuffer jsonData=mandsaurDataBaseHelper.getCachedNewsTable().
                        getNewsListFromDb(mandsaurDataBaseHelper.getSqLiteDatabase(),
                                mainCategory);


                    newsFromMainCategoryResponse=parseJsonAndRerutrnNewsCategoryResponse(jsonData.toString());


            }
            return newsFromMainCategoryResponse;
        }




       Response response= OkHttpClientUtils.perfromHttpRequestForPostRequest(request, request.get(Request.REQUEST_URL));

        if (response!=null){

            if (response.isSuccessful()){


                try {
                    String responseBody=response.body().string();
                    ActivityUtil.log(NewsDataRepository.class.getSimpleName(),"API Reponse>>"+responseBody);
                    newsFromMainCategoryResponse=
                            parseJsonAndRerutrnNewsCategoryResponse(responseBody);
                    MandsaurDataBaseHelper mandsaurDataBaseHelper=databaseNewsDataSource.getMandsaurDataBaseHelper();
                    if (newsFromMainCategoryResponse.isSuccessful()){
                        String mainCategory=request.get(NewsFromMainCategoryRequest.MAIN_CAT);
                        mandsaurDataBaseHelper.getCachedNewsTable().
                                setNewsListToDb(mandsaurDataBaseHelper.getSqLiteDatabase(),responseBody,mainCategory);
                    }

                } catch (IOException e) {
                    newsFromMainCategoryResponse=new NewsFromMainCategoryResponse();
                    newsFromMainCategoryResponse.setStatus("0");
                    newsFromMainCategoryResponse.setMsg(errorHandler.getApiErrorMessage(0));
                }
            }
            else {
                newsFromMainCategoryResponse=new NewsFromMainCategoryResponse();
                newsFromMainCategoryResponse.setStatus("0");
                newsFromMainCategoryResponse.setMsg(errorHandler.getApiErrorMessage(0));
            }

        }


       return newsFromMainCategoryResponse;
    }

    private NewsFromMainCategoryResponse parseJsonAndRerutrnNewsCategoryResponse(String jsonData){
        NewsFromMainCategoryResponse newsFromMainCategoryResponse=new NewsFromMainCategoryResponse();
        try {


            JsonReader jsonReader=new JsonReader(new StringReader(jsonData));
            jsonReader.setLenient(true);
            newsFromMainCategoryResponse= GsonUtil.getGsonInstance().
                    fromJson(jsonData,
                            com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse.class);


            if (newsFromMainCategoryResponse==null){
                newsFromMainCategoryResponse=new NewsFromMainCategoryResponse();
                newsFromMainCategoryResponse.setStatus("0");
                newsFromMainCategoryResponse.setMsg(errorHandler.getApiErrorMessage(0));
            }
            else if (newsFromMainCategoryResponse.getData()==null){
                newsFromMainCategoryResponse.setStatus("0");
                newsFromMainCategoryResponse.setMsg(errorHandler.getApiErrorMessage(ErrorRequestCode.API_ERROR_REQUEST_CODE.ERRORCODE_NEESLIST_NOT_FOUDN));
            }
            else if (newsFromMainCategoryResponse.getData().getNewsList()==null){

                newsFromMainCategoryResponse.setStatus("0");
                newsFromMainCategoryResponse.setMsg(errorHandler.getApiErrorMessage(ErrorRequestCode.API_ERROR_REQUEST_CODE.ERRORCODE_NEESLIST_NOT_FOUDN));
            }
            else if (newsFromMainCategoryResponse.getData().getNewsList().isEmpty()){

                newsFromMainCategoryResponse.setStatus("0");
                newsFromMainCategoryResponse.setMsg(errorHandler.getApiErrorMessage(ErrorRequestCode.API_ERROR_REQUEST_CODE.ERRORCODE_NEESLIST_NOT_FOUDN));
            }
            else {
                newsFromMainCategoryResponse.setStatus("1");
            }


        }
        catch (JsonSyntaxException e){
            newsFromMainCategoryResponse.setStatus("0");
            newsFromMainCategoryResponse.setMsg(errorHandler.getApiErrorMessage(0));
        }

        return newsFromMainCategoryResponse;
    }
    @Override
    public NewsDetailsFromResponse getNewsListOnSubCategories(NewsDetailFromIdRequest request) {
        return null;
    }

    @Override
    public NewsDetailsFromResponse getNewsDetailsFromId(NewsDetailFromIdRequest request) {
        NewsDetailsFromResponse newsDetailsFromResponse=new NewsDetailsFromResponse();

        MandsaurDataBaseHelper mandsaurDataBaseHelper=databaseNewsDataSource.getMandsaurDataBaseHelper();

        if (mandsaurDataBaseHelper.getSavedNewsTable().isNewsAlreadySaved(mandsaurDataBaseHelper.getSqLiteDatabase(),request.get(NewsDetailFromIdRequest.NEWS_ID))){

            newsDetailsFromResponse=GsonUtil.getGsonInstance().fromJson(mandsaurDataBaseHelper.getSavedNewsTable().getNewsDetailFromDB(mandsaurDataBaseHelper.getSqLiteDatabase()
                    , request.get(NewsDetailFromIdRequest.NEWS_ID)),NewsDetailsFromResponse.class);
            newsDetailsFromResponse.setStatus("1");

            return newsDetailsFromResponse;
        }





        Response response= OkHttpClientUtils.perfromHttpRequestForPostRequest(request, request.get(Request.REQUEST_URL));

        if (response!=null){

            if (response.isSuccessful()){
                try {
                    String jsonData=response.body().string();





                    JsonReader jsonReader=new JsonReader(new StringReader(jsonData));
                    jsonReader.setLenient(true);
                    newsDetailsFromResponse= GsonUtil.getGsonInstance().
                            fromJson(jsonData,
                                    com.mandasur.app.data.source.dao.requestdao.NewsDetailsFromResponse.class);
                    newsDetailsFromResponse.setUnderLineJson(jsonData);

                    if (newsDetailsFromResponse==null){
                        newsDetailsFromResponse.setStatus("0");
                        newsDetailsFromResponse.setMsg(errorHandler.getApiErrorMessage(0));
                    }
                    else if (newsDetailsFromResponse.getData()==null){
                        newsDetailsFromResponse.setStatus("0");
                        newsDetailsFromResponse.setMsg(errorHandler.getApiErrorMessage(ErrorRequestCode.API_ERROR_REQUEST_CODE.ERRORCODE_DETAILS_NOT_FOUDN));
                    }
                    else if (newsDetailsFromResponse.getData().isEmpty()){

                        newsDetailsFromResponse.setStatus("0");
                        newsDetailsFromResponse.setMsg(errorHandler.getApiErrorMessage(ErrorRequestCode.API_ERROR_REQUEST_CODE.ERRORCODE_DETAILS_NOT_FOUDN));
                    }
                    else {
                        newsDetailsFromResponse.setStatus("1");
                    }


                } catch (IOException e) {
                    newsDetailsFromResponse.setStatus("0");
                    newsDetailsFromResponse.setMsg(errorHandler.getApiErrorMessage(0));
                }
                catch (JsonSyntaxException e){
                    newsDetailsFromResponse.setStatus("0");
                    newsDetailsFromResponse.setMsg(errorHandler.getApiErrorMessage(0));
                }

            }
            else {
                newsDetailsFromResponse.setStatus("0");
                newsDetailsFromResponse.setMsg(errorHandler.getApiErrorMessage(0));
            }

        }


        return newsDetailsFromResponse;
    }




    public static class NewsListDesireliser implements JsonDeserializer<NewsFromMainCategoryResponse>{


        @Override
        public NewsFromMainCategoryResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            NewsFromMainCategoryResponse newsFromMainCategoryResponse=new NewsFromMainCategoryResponse();
            MandsaurDataBaseHelper mandsaurDataBaseHelper=databaseNewsDataSource.getMandsaurDataBaseHelper();
            JsonObject jsonObject=json.getAsJsonObject();

            Data data=new Data();
            ArrayList<News> newsArrayList=new ArrayList<>();
             data.setNewsList(newsArrayList);

            if (jsonObject!=null&&jsonObject.isJsonObject()){


                Set<Map.Entry<String,JsonElement>> entrySet=jsonObject.entrySet();

                int currentIindexOfoverallList=0;
                for (Map.Entry<String,JsonElement> elementEntry:entrySet){
                JsonElement  jsonElement=jsonObject.get(elementEntry.getKey());
                    if (jsonElement!=null&&jsonElement.isJsonArray()){

                        JsonArray jsonArray=jsonElement.getAsJsonArray();

                        int i=0;
                        for (JsonElement newsJsonObject:jsonArray){
                            News news=new News();
                            if (newsJsonObject!=null&&newsJsonObject.isJsonObject()){
                                JsonObject newsJson=newsJsonObject.getAsJsonObject();


                                news.setId(newsJson.get("id").getAsString());
                                news.setDate(newsJson.get("date").getAsString());
                                if (i==0){
                                    if (entrySet.size()>1){
                                        news.setIsSubcategoryStart(true);
                                        news.setSubCatIndicator(elementEntry.getKey());
                                        news.setSubCategoryName(mandsaurDataBaseHelper
                                                .getSubCategoriesTable().getSubCategoryNameFromSubCategroyIndicator(mandsaurDataBaseHelper.getSqLiteDatabase()
                                                        , elementEntry.getKey()));

                                    }
                                    else {
                                        news.setIsSubcategoryStart(false);
                                        news.setSubCategoryName(elementEntry.getKey());
                                    }

                                }
                                else {
                                    news.setIsSubcategoryStart(false);
                                }

                                i++;

                                currentIindexOfoverallList++;





                                news.setTitle(Html.fromHtml(newsJson.get("title").getAsString()).toString());
                                if (newsJson.get("image")!=null&&newsJson.get("image").isJsonPrimitive())
                                  news.setImage(newsJson.get("image").getAsString());

                                newsArrayList.add(news);

                                if (currentIindexOfoverallList!=0&&((currentIindexOfoverallList%2)==0)){
                                    News advertisedNews=new News();
                                    advertisedNews.setIsAdvertisedNewsBean(true);
                                    newsArrayList.add(advertisedNews);

                                }
                            }



                        }

                    }
                }

            }

            data.setNewsList(newsArrayList);
                newsFromMainCategoryResponse.setData(data);

            return newsFromMainCategoryResponse;
        }
    }





}
