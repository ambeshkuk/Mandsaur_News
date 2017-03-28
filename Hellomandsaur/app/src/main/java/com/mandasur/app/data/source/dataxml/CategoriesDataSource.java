package com.mandasur.app.data.source.dataxml;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.requestdao.CategoryResponseBean;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.data.source.remote.OkHttpClientUtils;
import com.mandasur.app.news.exceptions.ErrorMessageHandler;
import com.mandasur.app.util.GsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Response;


/**
 * Class to manage the generation of categories from data xml stored in values folder.
 * Created by ambesh on 29-01-2017.
 */
public class CategoriesDataSource  {


    private Context context;
    private ErrorMessageHandler errorHandler;
    private DatabaseNewsDataSource databaseNewsDataSource;
    public CategoriesDataSource(Context context
            ,DatabaseNewsDataSource databaseNewsDataSource){
        this.context=context;
        this.errorHandler=ErrorMessageHandler.getInstance(context);
        this.databaseNewsDataSource=databaseNewsDataSource;
    }


    public DatabaseNewsDataSource getDatabaseNewsDataSource() {
        return databaseNewsDataSource;
    }

    public CategoryResponseBean loadAllCategoriesToDb(){


        CategoryResponseBean categoryResponseBean=new CategoryResponseBean();


     Response response= OkHttpClientUtils.
                 perfromHttpRequestForPostRequest(new HashMap<String, String>(),
                         context.getString(R.string.baseUrl) +
                                 context.getString(R.string.categories_news));


        if (response!=null){

            if (response.isSuccessful()){


                try {
                    String responseBody=response.body().string();

                   Gson gson= GsonUtil.getGsonInstance();
                    categoryResponseBean=gson.fromJson(responseBody
                            ,CategoryResponseBean.class);

                    MandsaurDataBaseHelper mandsaurDataBaseHelper=databaseNewsDataSource.getInstance(context);
                    mandsaurDataBaseHelper.
                    getCategoriesTable().
                            insertCategoryToDb(mandsaurDataBaseHelper,
                                    categoryResponseBean.getData());
                categoryResponseBean.setStatus("1");

                } catch (IOException e) {

                    categoryResponseBean.setStatus("0");
                    categoryResponseBean.setMsg(errorHandler.getApiErrorMessage(0));
                }
            }
            else {

                categoryResponseBean.setStatus("0");
                categoryResponseBean.setMsg(errorHandler.getApiErrorMessage(0));
            }

        }

        return categoryResponseBean;
    }



    public ArrayList<Category> getAllCategroiesFromDb(){
            MandsaurDataBaseHelper mandsaurDataBaseHelper=databaseNewsDataSource.getMandsaurDataBaseHelper();
      return   mandsaurDataBaseHelper.getCategoriesTable().getCategoriesFromDb(mandsaurDataBaseHelper.getSqLiteDatabase());
    }

}
