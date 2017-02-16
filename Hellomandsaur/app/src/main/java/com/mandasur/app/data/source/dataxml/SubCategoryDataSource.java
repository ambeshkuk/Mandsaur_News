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

    public SubCategoryDataSource(Context context) {
        this.context = context;


    }


    public ArrayList<SubCategories> getAllSubCategoriesFromFromDataXml() {

        ArrayList<SubCategories> categories = new ArrayList<>();
        if (DatabaseNewsDataSource.getInstance(context).getSubCategoriesTable().getRowCount(DatabaseNewsDataSource.getInstance(context).getSqLiteDatabase()) != 0) {


            return DatabaseNewsDataSource.getInstance(context).getSubCategoriesTable()
                    .getSubCategoiesFromDb(DatabaseNewsDataSource.getInstance(context).getSqLiteDatabase());

        }


        String[] categoriesTitle = context.getResources().getStringArray(R.array.sub_categories_name);
String[] subCategoriesIndicator=context.getResources().getStringArray(R.array.subcategoires_indicator);

        for (int i = 0; i < categoriesTitle.length; i++) {

            SubCategories subCategories = new SubCategories();

            subCategories.setSubCategoryName(categoriesTitle[i]);
            subCategories.setSubCategoryId(i + "");
            subCategories.setIsItemChecked(true);
            subCategories.setSubCategoryIndicator(subCategoriesIndicator[i]);
            DatabaseNewsDataSource.getInstance(context).getSubCategoriesTable()
                    .insertSubCateoriesToDb(DatabaseNewsDataSource.getInstance(context).getSqLiteDatabase(), subCategories);
            categories.add(subCategories);
        }

        return categories;
    }

}
