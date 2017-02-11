package com.mandasur.app.news;

import android.support.annotation.NonNull;

import com.mandasur.app.BasePresenter;
import com.mandasur.app.BaseView;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.News;
import com.mandasur.app.data.source.dao.SubCategories;

import java.util.ArrayList;


/**
 * COntrat between Drawer View in
 * Created by ambesh on 29-01-2017.
 */
public interface SubCategoryContract {



    interface SubCateogryFilterView extends BaseView<SubCateoriesFilterPresenter>{


        void showSubCatergories(ArrayList<SubCategories> subCategories);

    }
    interface SubCateoriesPresenter extends BasePresenter{




        void loadSubCategories();


        void setIfSubCategoryIsSelectedOrDislected(SubCategories subCategories);




    }



}
