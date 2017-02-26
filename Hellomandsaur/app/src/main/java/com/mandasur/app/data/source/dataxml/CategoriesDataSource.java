package com.mandasur.app.data.source.dataxml;

import android.content.Context;
import android.content.res.TypedArray;

import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Category;

import java.util.ArrayList;



/**
 * Class to manage the generation of categories from data xml stored in values folder.
 * Created by ambesh on 29-01-2017.
 */
public class CategoriesDataSource  {


    private Context context;

    public CategoriesDataSource(Context context){
        this.context=context;
    }




    public ArrayList<Category> getAllCategoriesFromDataXml(){

        ArrayList<Category> categories=new ArrayList<>();

       String[] categoriesTitle= context.getResources().getStringArray(R.array.categoriesTitle);
        String[] categoryIdentifier=context.getResources().getStringArray(R.array.categoryIdentifier);


        for (int i=0;i<categoriesTitle.length;i++){

            Category category=new Category();



            category.setCategoryTitle(categoriesTitle[i]);
            category.setCategoryIdentifier(categoryIdentifier[i]);
            category.setCategoryId(i);
            categories.add(category);
        }




        return categories;
    }




}
