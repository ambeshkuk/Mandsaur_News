package com.mandasur.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mandasur.app.data.source.CategoryDataRepository;
import com.mandasur.app.data.source.dataxml.CategoriesDataSource;
import com.mandasur.app.news.usecase.GetCategories;

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
}
