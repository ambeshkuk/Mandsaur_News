package com.mandasur.app.data.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Ads;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.dao.requestdao.AdvertiseRequest;
import com.mandasur.app.data.source.dao.requestdao.AdvertiseResponseBean;
import com.mandasur.app.data.source.dao.requestdao.Data;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailFromIdRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.mandasur.app.data.source.dao.requestdao.Request;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.data.source.remote.OkHttpClientUtils;
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
 * Created by ambesh on 11-02-2017.
 */
public class AdvertisingDataRepository {





    private static  DatabaseNewsDataSource databaseNewsDataSource;

    private ErrorMessageHandler errorHandler;

    private static AdvertisingDataRepository advertisingDataRepository;

    public static AdvertisingDataRepository
    getAdvertisingDataRepositoryInstance(DatabaseNewsDataSource databaseNewsDataSource,Context context){

        if (advertisingDataRepository==null){
            advertisingDataRepository=new AdvertisingDataRepository(databaseNewsDataSource,context);
        }

        return advertisingDataRepository;
    }

    private Context context;
    private AdvertisingDataRepository(DatabaseNewsDataSource databaseNewsDataSource,Context context) {
        this.databaseNewsDataSource = databaseNewsDataSource;
        errorHandler=ErrorMessageHandler.getInstance(context);
        this.context=context;

    }

    public interface LoadAdvertisinments {

        void onAdvertismentsLoaded(boolean advertisementsLoaded);

        void onAdvertisiementsNotVaialbel();

    }




    public void loadAdvertiseMentToDb(@NonNull LoadAdvertisinments loadCategoriesCallBack
            ,AdvertiseRequest advertiseRequest) {



        AdvertiseResponseBean advertiseResponseBean = null;






//        if (advertiseRequest.get(NewsFromMainCategoryRequest.IS_NETWORK_AVALIBLE)
//                .equals(NewsFromMainCategoryRequest.FALSE)){
//            advertiseResponseBean=new AdvertiseResponseBean();
//            MandsaurDataBaseHelper mandsaurDataBaseHelper=databaseNewsDataSource
//                    .getMandsaurDataBaseHelper();
//
//            if (mandsaurDataBaseHelper.getAdvertising_table()
//                    .getRowCount(mandsaurDataBaseHelper.getSqLiteDatabase())>0){
//
//
//
//
//                loadCategoriesCallBack.onAdvertismentsLoaded(true);
//
//
//            }
//            else {
//                loadCategoriesCallBack.onAdvertismentsLoaded(false);
//            }
//
//        }




        Response response= OkHttpClientUtils.
                perfromHttpRequestForPostRequest(advertiseRequest, advertiseRequest.get(Request.REQUEST_URL));

        if (response!=null){

            if (response.isSuccessful()){


                try {
                    String responseBody=response.body().string();

                    advertiseResponseBean=
                            parseJsonAndRerutrnAdvertiseResponse(responseBody);




                    MandsaurDataBaseHelper mandsaurDataBaseHelper=databaseNewsDataSource.getInstance(context);
                    if (advertiseResponseBean.isSuccessful()){

                        mandsaurDataBaseHelper.
                                getAdvertising_table().
                                insertAdsToDb(mandsaurDataBaseHelper.getSqLiteDatabase(),
                                        advertiseResponseBean.getAds());
                        loadCategoriesCallBack.onAdvertismentsLoaded(true);
                    }

                } catch (IOException e) {
                    advertiseResponseBean=new AdvertiseResponseBean();
                    advertiseResponseBean.setStatus("0");
                    advertiseResponseBean.setMsg(errorHandler.getApiErrorMessage(0));
                    loadCategoriesCallBack.onAdvertismentsLoaded(false);

                }
            }
            else {
                advertiseResponseBean=new AdvertiseResponseBean();
                advertiseResponseBean.setStatus("0");
                advertiseResponseBean.setMsg(errorHandler.getApiErrorMessage(0));
                loadCategoriesCallBack.onAdvertismentsLoaded(false);

            }

        }


        




    }

    private AdvertiseResponseBean parseJsonAndRerutrnAdvertiseResponse(String responseBody) {
        AdvertiseResponseBean advertiseResponseBean=new AdvertiseResponseBean();
        try {


            JsonReader jsonReader = new JsonReader(new StringReader(responseBody));
            jsonReader.setLenient(true);
            advertiseResponseBean = GsonUtil.getGsonInstance().
                    fromJson(responseBody,
                            com.mandasur.app.data.source.dao.requestdao.AdvertiseResponseBean.class);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return advertiseResponseBean;
    }



    public static class AdvertisingSerilizer implements JsonDeserializer<AdvertiseResponseBean> {


        @Override
        public AdvertiseResponseBean deserialize(JsonElement json, Type typeOfT
                , JsonDeserializationContext context) throws JsonParseException {
            AdvertiseResponseBean advertiseResponseBean=new AdvertiseResponseBean();

            ArrayList<Ads> adses=new ArrayList<>();
            JsonObject jsonObject=json.getAsJsonObject();
            if (jsonObject!=null&&jsonObject.isJsonObject()){
                Set<Map.Entry<String,JsonElement>> entrySet=jsonObject.entrySet();
                for (Map.Entry<String,JsonElement> elementEntry:entrySet){
                    JsonElement  jsonElement=jsonObject.get(elementEntry.getKey());
                    if (jsonElement!=null&&jsonElement.isJsonArray()){
                        JsonArray jsonArray=jsonElement.getAsJsonArray();
                        for (JsonElement advertisementJsonElement:jsonArray){
                            Ads ads=new Ads();
                            if (advertisementJsonElement.isJsonObject()){
                                JsonObject advertisemetnJsonObject=advertisementJsonElement
                                        .getAsJsonObject();
                                ads.setId(advertisemetnJsonObject.get("id").getAsString());

                                JsonElement
                                        adContentElement=advertisemetnJsonObject.
                                        get("ad_content");

                                if (adContentElement!=null&&adContentElement.isJsonObject()){

                                    JsonObject adContentObject=adContentElement.getAsJsonObject();

                                    JsonElement adsArrayElement=adContentObject.get("ads");


                                    if (adsArrayElement!=null&&adsArrayElement.isJsonArray()){
                                        JsonArray adsArrayObject=adsArrayElement.getAsJsonArray();

                                        for (JsonElement adsElement:adsArrayObject){
                                            JsonObject adsObject=
                                                    adsElement.getAsJsonObject();

                            ads.setAd_title(!adsObject.has("ad_title")?"":adsObject.get("ad_title").getAsString());
                            ads.setAd_url(!adsObject.has("ad_url") ? "":adsObject.get("ad_url").getAsString());






                            ads.setAd_image_full(!adsObject.has("ad_image_full")?"":adsObject.get("ad_image_full").getAsString());
                            ads.setAd_image_preview(!adsObject.has("ad_image_preview")?"":adsObject.get("ad_image_preview").getAsString());


                                        }


                                    }



                                }
                            }
                            adses.add(ads);
                        }
                    }
                }
            }

            if (adses.isEmpty()){
                advertiseResponseBean.
                        setStatus("0");
            }
            else {
                advertiseResponseBean.setStatus("1");
                advertiseResponseBean.setAds(adses);
            }

            return advertiseResponseBean;
        }
    }


}
