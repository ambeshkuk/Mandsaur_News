package com.mandasur.app.news.usecase;

import android.support.annotation.NonNull;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.SubCategoryDataRepository;
import com.mandasur.app.data.source.dao.SubCategories;

import java.util.ArrayList;

/**
 * Created by ambesh on 11-02-2017.
 */
public class GetAdvertisingList extends UseCase<GetAdvertisingList.RequestValues,GetAdvertisingList.ResponseValue>  {


     private SubCategoryDataRepository subCategoryDataRepository;
   public GetAdvertisingList(SubCategoryDataRepository subCategoryDataRepository){

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
        },requestValues.getMainCategoryName());

    }


    public static final class RequestValues implements UseCase.RequestValues {

        public String getMainCategoryName() {
            return mainCategoryName;
        }

        public void setMainCategoryName(String mainCategoryName) {
            this.mainCategoryName = mainCategoryName;
        }

        private String mainCategoryName;


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




