package com.mandasur.app.news.usecase;

import android.support.annotation.NonNull;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.CategoryDataRepository;

import java.util.ArrayList;

/**
 * Helper use case to intiate the Get Categories operation from data xml.
 * Created by ambesh on 30-01-2017.
 */
public class GetCategories extends UseCase<GetCategories.RequestValues,GetCategories.ResponseValue> {


    private CategoryDataRepository categoryDataRepository;

    public GetCategories(CategoryDataRepository categoryDataRepository){
        this.categoryDataRepository=categoryDataRepository;
    }
    @Override
    public void executeUseCase(RequestValues requestValues) {
        categoryDataRepository.getCategories(new CategoryDataRepository.LoadCategoriesCallBack() {
            @Override
            public void onCategoriesLoaded(ArrayList<Category> categories) {

                ResponseValue responseValue=new ResponseValue(categories);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onCategoriesNotAvaliable() {

                getUseCaseCallback().onError();

            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {


    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private ArrayList<Category> categories;

        public ResponseValue(@NonNull ArrayList<Category> categories) {
            this.categories = categories;
        }






        public ArrayList<Category> getCategories() {
            return categories;
        }
    }
}
