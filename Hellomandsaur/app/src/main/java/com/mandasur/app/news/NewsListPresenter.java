package com.mandasur.app.news;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.Category;
import com.mandasur.app.data.source.dao.News;
import com.mandasur.app.news.usecase.GetCategories;



/**
 * Created by ambesh on 30-01-2017.
 */
public class NewsListPresenter implements NewsDrawerContract.NewsPresenter {




    private GetCategories getCategories;
    private BaseNewsFragment baseNewsFragment;
    private NewsBaseActiivty newsBaseActiivty;


    public NewsListPresenter(@NonNull GetCategories getCategories,
                             @NonNull BaseNewsFragment baseNewsFragment, NewsBaseActiivty newsBaseActiivty){

        this.getCategories=getCategories;
        this.baseNewsFragment=baseNewsFragment;


        this.newsBaseActiivty=newsBaseActiivty;
        this.baseNewsFragment.setPresenter(this);
    }


    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadCategories() {


        getCategories.setUseCaseCallback(new UseCase.UseCaseCallback<GetCategories.ResponseValue>() {
            @Override
            public void onSuccess(GetCategories.ResponseValue response) {

                baseNewsFragment.showCategories(response.getCategories());
                newsBaseActiivty.showCategoriesOnSidePanel(response.getCategories());
            }

            @Override
            public void onError() {

            }
        });





        getCategories.executeUseCase(new GetCategories.RequestValues());






    }

    @Override
    public void openNewsDetails(@NonNull News newsId) {

    }

    @Override
    public void checkIfCategoryImplmeneted(@NonNull Category category) {

    }

    @Override
    public void start() {

        loadCategories();
    }
}
