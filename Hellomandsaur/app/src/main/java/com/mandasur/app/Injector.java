package com.mandasur.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mandasur.app.data.source.CategoryDataRepository;
import com.mandasur.app.data.source.SubCategoryDataRepository;
import com.mandasur.app.data.source.dataxml.CategoriesDataSource;
import com.mandasur.app.data.source.dataxml.SubCategoryDataSource;
import com.mandasur.app.news.usecase.GetCategories;
import com.mandasur.app.news.usecase.GetSubCategory;
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
}
