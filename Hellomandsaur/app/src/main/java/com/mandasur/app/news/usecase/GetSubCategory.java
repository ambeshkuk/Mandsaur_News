package com.mandasur.app.news.usecase;

import android.support.annotation.NonNull;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.SubCategoryDataRepository;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.SubCategories;

import java.util.ArrayList;

/**
 * Created by ambesh on 11-02-2017.
 */
public class GetSubCategory extends UseCase<GetSubCategory.RequestValues,GetSubCategory.ResponseValue>  {


     private SubCategoryDataRepository subCategoryDataRepository;
   public GetSubCategory(SubCategoryDataRepository subCategoryDataRepository){

       this.subCategoryDataRepository=subCategoryDataRepository;
   }
    @Override
    public void executeUseCase(RequestValues requestValues) {

        subCategoryDataRepository.getCategories(new SubCategoryDataRepository.LoadSubCategoriesCallBack() {
            @Override
            public void onSubCategroiesLoaded(ArrayList<SubCategories> subCategories) {
                ResponseValue responseValue=new ResponseValue(subCategories);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onSubCategoriesNotAvaliable() {
                getUseCaseCallback().onError("");;

            }
        });

    }


    public static final class RequestValues implements UseCase.RequestValues {


    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private ArrayList<SubCategories> subCategories;

        public ResponseValue(@NonNull ArrayList<SubCategories> subCategories) {
            this.subCategories = subCategories;
        }

        public ArrayList<SubCategories> getCategories() {
            return subCategories;
        }
    }
}




