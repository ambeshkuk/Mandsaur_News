package com.mandasur.app.news;

import android.support.annotation.NonNull;

import com.mandasur.app.UseCase;
import com.mandasur.app.news.usecase.GetCategories;



/**
 * Created by ambesh on 30-01-2017.
 */
public class CategoryTabsAndDrawerPresenter implements DrawerContract.CategroyPresenter {




    private GetCategories getCategories;
    private BaseCategoryFragment baseNewsFragment;
    private NewsBaseActiivty newsBaseActiivty;


    public CategoryTabsAndDrawerPresenter(@NonNull GetCategories getCategories,
                                          @NonNull BaseCategoryFragment baseNewsFragment, NewsBaseActiivty newsBaseActiivty){

        this.getCategories=getCategories;
        this.baseNewsFragment=baseNewsFragment;


        this.newsBaseActiivty=newsBaseActiivty;
        this.baseNewsFragment.setPresenter(this);
    }


    @Override
    public void updateTheSelectionOfDrawer(int position) {
        newsBaseActiivty.setItemSelcetionOfDrawerView(position);
    }

    @Override
    public void loadCategories() {


        getCategories.setUseCaseCallback(new UseCase.UseCaseCallback<GetCategories.ResponseValue>() {
            @Override
            public void onSuccess(GetCategories.ResponseValue response) {
                newsBaseActiivty.showCategoriesOnSidePanel(response.getCategories());
                baseNewsFragment.showCategories(response.getCategories());

            }

            @Override
            public void onError(String errorMessage) {

            }
        });





        getCategories.executeUseCase(new GetCategories.RequestValues());






    }

    @Override
    public void openCategory(int categroyId) {

        baseNewsFragment.openCategory(categroyId);
    }

    @Override
    public void start() {

        loadCategories();
    }
}
