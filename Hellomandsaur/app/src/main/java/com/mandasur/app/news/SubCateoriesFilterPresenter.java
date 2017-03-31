package com.mandasur.app.news;

import android.support.annotation.NonNull;

import com.mandasur.app.UseCase;

import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.news.usecase.GetSubCategory;
import com.mandasur.app.news.usecase.SubCateorySelected;


/**
 * Created by ambesh on 30-01-2017.
 */




public class SubCateoriesFilterPresenter implements SubCategoryContract.SubCateoriesPresenter{


    private GetSubCategory getSubCategory;
    private SubCateorySelected subCateorySelected;
    private SubCategoryContract.SubCateogryFilterView subCateogryFilterView;
    private String mainCategoryName;
    public SubCateoriesFilterPresenter(@NonNull GetSubCategory getSubCategory,@NonNull SubCateorySelected subCateorySelected
            ,@NonNull SubCategoryContract.SubCateogryFilterView subCateogryFilterView) {

        this.getSubCategory=getSubCategory;
        this.subCateogryFilterView=subCateogryFilterView;
        this.subCateorySelected=subCateorySelected;
        subCateogryFilterView.setPresenter(this);
    }



public void setMainCategoryName(String mainCategoryName){
    this.mainCategoryName=mainCategoryName;

}
    @Override
    public void loadSubCategories() {

        GetSubCategory.RequestValues requestValues =new GetSubCategory.RequestValues();
        requestValues.setMainCategoryName(mainCategoryName);
        getSubCategory.setUseCaseCallback(new UseCase.UseCaseCallback<GetSubCategory.ResponseValue>() {
            @Override
            public void onSuccess(GetSubCategory.ResponseValue response) {
                subCateogryFilterView.showSubCatergories(response.getCategories());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
        getSubCategory.executeUseCase(requestValues);
    }

    @Override
    public void setIfSubCategoryIsSelectedOrDislected(SubCategories subCategories) {

        subCateorySelected.setUseCaseCallback(useCaseCallback);
        subCateorySelected.executeUseCase(new SubCateorySelected.RequestValues(subCategories));

    }

    private UseCase.UseCaseCallback<SubCateorySelected.ResponseValue> useCaseCallback=new UseCase.UseCaseCallback<SubCateorySelected.ResponseValue>() {
        @Override
        public void onSuccess(SubCateorySelected.ResponseValue response) {

        }

        @Override
        public void onError(String errorMessage) {

        }
    };

    @Override
    public void start() {
        loadSubCategories();
    }
}
