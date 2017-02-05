package com.mandasur.app.news;

import android.support.annotation.NonNull;

import com.mandasur.app.BasePresenter;
import com.mandasur.app.BaseView;
import com.mandasur.app.data.Category;
import com.mandasur.app.data.NewsBean;
import com.mandasur.app.data.source.dao.News;

import java.util.ArrayList;
import java.util.List;



/**
 * COntrat between Drawer View in
 * Created by ambesh on 29-01-2017.
 */
public interface NewsDrawerContract {



    interface NewsView extends BaseView<NewsPresenter>{
        void setLoadingIndicator();
        void showNewsByCategory(ArrayList<NewsBean> newsBeans);
        void showCategories(ArrayList<Category> categories);
        void showNewsDetail(String newsId);
        void showNetworkNotAvaliable();
        void showCommingSoonToast();
        void openCategory(String categoryId);

    }
    interface DrawerView extends BaseView<NewsPresenter>{
        void showCategoriesOnSidePanel(ArrayList<Category> categories);
    }
    interface NewsPresenter extends BasePresenter{



        void result(int requestCode,int resultCode);
        void loadCategories();
        void openNewsDetails(@NonNull News newsId);
        void checkIfCategoryImplmeneted(@NonNull Category category);



    }



}
