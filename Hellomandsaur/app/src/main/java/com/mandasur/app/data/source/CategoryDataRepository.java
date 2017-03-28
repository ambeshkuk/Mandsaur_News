package com.mandasur.app.data.source;

import android.support.annotation.NonNull;

import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.requestdao.CategoryResponseBean;
import com.mandasur.app.data.source.dataxml.CategoriesDataSource;

import java.util.ArrayList;

/**
 * Concrete implemtation to load the category from data xml.
 * Created by ambesh on 29-01-2017.
 */
public class CategoryDataRepository  {

    private CategoriesDataSource categoriesDataSource;


   public CategoryDataRepository(CategoriesDataSource categoriesDataSource){
       this.categoriesDataSource=categoriesDataSource;

    }

    public CategoriesDataSource getCategoriesDataSource() {
        return categoriesDataSource;
    }

    public interface LoadCategoriesCallBack{

        void onCategoriesLoaded(CategoryResponseBean categories);
        void onCategoriesNotAvaliable();

    }



    public interface LoadCategoriesFromDb{
        void onCategoriesLoaded(ArrayList<Category> categories);
        void onCategoriesNotAvaliable();
    }

    public void loadCategoriesToDb(@NonNull LoadCategoriesCallBack loadCategoriesCallBack){


            CategoryResponseBean categories=categoriesDataSource.loadAllCategoriesToDb();

            if (categories!=null){

                 loadCategoriesCallBack.onCategoriesLoaded(categories);
            }
            else {
                    loadCategoriesCallBack.onCategoriesNotAvaliable();
            }



    }



    public void getCategories(@NonNull LoadCategoriesFromDb loadCategoriesFromDb){



        ArrayList<Category> categories=categoriesDataSource.getAllCategroiesFromDb();
        if (categories!=null){
            loadCategoriesFromDb.onCategoriesLoaded(categories);

        }
        else {
            loadCategoriesFromDb.onCategoriesNotAvaliable();
        }
    }

}
