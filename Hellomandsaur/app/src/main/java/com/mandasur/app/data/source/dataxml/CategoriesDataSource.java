package com.mandasur.app.data.source.dataxml;

import android.content.Context;
import android.content.res.TypedArray;

import com.mandasur.app.R;
import com.mandasur.app.data.Category;

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
        TypedArray iconArray=context.getResources().obtainTypedArray(R.array.categoriesIconImages);

        for (int i=0;i<categoriesTitle.length;i++){

            Category category=new Category();


            category.setImageId(iconArray.getResourceId(i,-1));

            category.setCategoryTitle(categoriesTitle[i]);
            category.setCategoryId(i);
            categories.add(category);
        }




        return categories;
    }




}
