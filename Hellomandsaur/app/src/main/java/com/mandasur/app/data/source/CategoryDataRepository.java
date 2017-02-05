package com.mandasur.app.data.source;

import android.support.annotation.NonNull;

import com.mandasur.app.data.Category;
import com.mandasur.app.data.source.dataxml.CategoriesDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implemtation to load the category from data xml.
 * Created by ambesh on 29-01-2017.
 */
public class CategoryDataRepository  {

    private CategoriesDataSource categoriesDataSource;


   public CategoryDataRepository(CategoriesDataSource categoriesDataSource){
       this.categoriesDataSource=categoriesDataSource;

    }
    public interface LoadCategoriesCallBack{

        void onCategoriesLoaded(ArrayList<Category> categories);
        void onCategoriesNotAvaliable();

    }

    public void getCategories(@NonNull LoadCategoriesCallBack loadCategoriesCallBack){

            ArrayList<Category> categories=categoriesDataSource.getAllCategoriesFromDataXml();

            if (categories!=null&&!categories.isEmpty()){

                 loadCategoriesCallBack.onCategoriesLoaded(categories);
            }
            else {
                    loadCategoriesCallBack.onCategoriesNotAvaliable();
            }



    }

}
