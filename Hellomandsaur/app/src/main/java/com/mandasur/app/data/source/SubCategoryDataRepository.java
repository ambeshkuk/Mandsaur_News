package com.mandasur.app.data.source;

import android.database.DatabaseUtils;
import android.support.annotation.NonNull;

import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.dataxml.CategoriesDataSource;
import com.mandasur.app.data.source.dataxml.SubCategoryDataSource;

import java.util.ArrayList;

/**
 * Created by ambesh on 11-02-2017.
 */
public class SubCategoryDataRepository {





    private SubCategoryDataSource subCategoryDataSource;


    public SubCategoryDataRepository(SubCategoryDataSource subCategoryDataSource) {
        this.subCategoryDataSource = subCategoryDataSource;

    }

    public interface LoadSubCategoriesCallBack {

        void onSubCategroiesLoaded(ArrayList<SubCategories> subCategories);

        void onSubCategoriesNotAvaliable();

    }

    public interface SetSelectedunSelectedCategoryToDatabase{

        public void onSubCategroySuccesfullyPersisted();

        public void onDatabaseError();

    }
    public void getCategories(@NonNull LoadSubCategoriesCallBack loadCategoriesCallBack) {

        ArrayList<SubCategories> categories = subCategoryDataSource.getAllSubCategoriesFromFromDataXml();

        if (categories != null && !categories.isEmpty()) {
            loadCategoriesCallBack.onSubCategroiesLoaded(categories);

        } else {
        loadCategoriesCallBack.onSubCategoriesNotAvaliable();
        }


    }

    public void persistUserCategorySettingInDb(SubCategories subCategories,@NonNull SetSelectedunSelectedCategoryToDatabase setSelectedunSelectedCategoryToDatabase){

        /**
         * Implementeation need to be done after completing database.
         */
    }
}
