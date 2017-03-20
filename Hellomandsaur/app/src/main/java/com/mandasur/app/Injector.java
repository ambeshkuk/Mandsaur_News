package com.mandasur.app;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.mandasur.app.data.source.CategoryDataRepository;
import com.mandasur.app.data.source.NewsDataRepository;
import com.mandasur.app.data.source.SubCategoryDataRepository;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;
import com.mandasur.app.data.source.dataxml.CategoriesDataSource;
import com.mandasur.app.data.source.dataxml.SubCategoryDataSource;
import com.mandasur.app.data.source.remote.RemoteNewsDataSource;
import com.mandasur.app.news.usecase.GetCategories;
import com.mandasur.app.news.usecase.GetNewsDetailsFromServer;
import com.mandasur.app.news.usecase.GetNewsListByCategory;
import com.mandasur.app.news.usecase.GetSubCategory;
import com.mandasur.app.news.usecase.ShareNewsDetails;
import com.mandasur.app.news.usecase.SubCateorySelected;

/**
 * Class that contains helper method to perfrom injection.
 * Created by ambesh on 01-02-2017.
 */






public class Injector {

    public static GetCategories getCategoriesUseCase(@NonNull Context context)
    {
        return  new GetCategories(getCategoryDataReporsitory(context));
    }

    public static CategoryDataRepository getCategoryDataReporsitory(Context context){

        return new CategoryDataRepository(new CategoriesDataSource(context));

    }




    public static SubCategoryDataRepository getSubCategoryDataReporsitory(Context context){

                        return new SubCategoryDataRepository(new SubCategoryDataSource(context));
    }

    public static GetSubCategory getSubCategoryUseCase(@NonNull Context context){
        return new GetSubCategory(getSubCategoryDataReporsitory(context));
    }

    public static SubCateorySelected getSubCategorySelected(@NonNull Context context){
        return new SubCateorySelected(getSubCategoryDataReporsitory(context));
    }

    public static GetNewsListByCategory getNewsListByCategory(@NonNull Context context){


        return new GetNewsListByCategory(getNewsDataRepository(context));
    }

    public static NewsDataRepository getNewsDataRepository(Context context){

      return   new NewsDataRepository(new RemoteNewsDataSource(),new DatabaseNewsDataSource(),context);
    }

    public static GetNewsDetailsFromServer getNewsDetailsFromServer(@NonNull Context context){
        return  new GetNewsDetailsFromServer(getNewsDataRepository(context));
    }


    public static ShareNewsDetails getShareNewsDetailsUseCase(@NonNull Context activity){
        return new ShareNewsDetails(activity);
    }
}

