package com.mandasur.app.news;

import com.mandasur.app.BasePresenter;
import com.mandasur.app.BaseView;
import com.mandasur.app.data.source.dao.Category;

import java.util.ArrayList;


/**
 * COntrat between Drawer View in
 * Created by ambesh on 29-01-2017.
 */
public interface DrawerContract {



    interface CategoryView extends BaseView<CategroyPresenter>{

        void showCategories(ArrayList<Category> categories);

        void openCategory(int categoryId);


    }
    interface DrawerView extends BaseView<CategroyPresenter>{
        void showCategoriesOnSidePanel(ArrayList<Category> categories);
        void setTheVisibilityOfFilterActivity(boolean isFilterBeVisible);
        void setItemSelcetionOfDrawerView(int position);

    }
    interface CategroyPresenter extends BasePresenter{

        void updateTheSelectionOfDrawer(int position);
        void loadCategories();
        void openCategory(int categroyId);



    }



}
