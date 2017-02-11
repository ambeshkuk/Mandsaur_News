package com.mandasur.app.news.usecase;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.SubCategoryDataRepository;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryRequest;
import com.mandasur.app.data.source.dao.requestdao.NewsFromMainCategoryResponse;

/**
 * Created by ambesh on 11-02-2017.
 */
public class SubCateorySelected extends UseCase<SubCateorySelected.RequestValues,SubCateorySelected.ResponseValue> {



    private SubCategoryDataRepository subCategoryDataRepository;

    public SubCateorySelected(SubCategoryDataRepository subCategoryDataRepository) {
        this.subCategoryDataRepository = subCategoryDataRepository;
    }

    @Override
    public void executeUseCase(RequestValues requestValues) {

        subCategoryDataRepository.persistUserCategorySettingInDb(requestValues.getSubCategories(), new SubCategoryDataRepository.SetSelectedunSelectedCategoryToDatabase() {
            @Override
                public void onSubCategroySuccesfullyPersisted() {
                getUseCaseCallback().onSuccess(new ResponseValue());
            }

            @Override
            public void onDatabaseError() {
                getUseCaseCallback().onError();
            }
        });

    }


    public static final class RequestValues implements UseCase.RequestValues {

        private SubCategories subCategories;

        public RequestValues(SubCategories subCategories) {
            this.subCategories = subCategories;
        }

        public SubCategories getSubCategories() {
            return subCategories;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        boolean isDataSucessfullySaved;

        public boolean isDataSucessfullySaved() {
            return isDataSucessfullySaved;
        }

        public void setIsDataSucessfullySaved(boolean isDataSucessfullySaved) {
            this.isDataSucessfullySaved = isDataSucessfullySaved;
        }
    }
}
