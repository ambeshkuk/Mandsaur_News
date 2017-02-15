package com.mandasur.app.data.source.dataxml;

import android.content.Context;
import android.content.res.TypedArray;

import com.mandasur.app.R;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;

import java.util.ArrayList;

/**
 * Created by ambesh on 11-02-2017.
 */
public class SubCategoryDataSource {


    private Context context;

    public SubCategoryDataSource(Context context){
        this.context=context;




    }




    public ArrayList<SubCategories> getAllSubCategoriesFromFromDataXml(){

//        if (DatabaseNewsDataSource.getInstance(context).getSubCategoriesTable().)
        ArrayList<SubCategories> categories=new ArrayList<>();

        String[] categoriesTitle= context.getResources().getStringArray(R.array.sub_categories_name);


        for (int i=0;i<categoriesTitle.length;i++){

            SubCategories subCategories=new SubCategories();

            subCategories.setSubCategoryName(categoriesTitle[i]);
            subCategories.setSubCategoryId(i + "");
            subCategories.setIsItemChecked(true);
            categories.add(subCategories);
        }

        return categories;
    }

}
