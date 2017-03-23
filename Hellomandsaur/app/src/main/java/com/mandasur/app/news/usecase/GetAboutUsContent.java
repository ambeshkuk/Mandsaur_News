package com.mandasur.app.news.usecase;

import android.support.annotation.NonNull;

import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.AboutUsDataRepository;
import com.mandasur.app.data.source.CategoryDataRepository;
import com.mandasur.app.data.source.dao.Category;

import java.util.ArrayList;

/**
 * Helper use case to intiate the Get Categories operation from data xml.
 * Created by ambesh on 30-01-2017.
 */
public class GetAboutUsContent extends UseCase<GetAboutUsContent.RequestValues,GetAboutUsContent.ResponseValue> {


    private AboutUsDataRepository aboutUsDataRepository;

    public GetAboutUsContent(AboutUsDataRepository aboutUsDataRepository){
        this.aboutUsDataRepository=aboutUsDataRepository;
    }
    @Override
    public void executeUseCase(RequestValues requestValues) {
        aboutUsDataRepository.laodAboutUsContent(new AboutUsDataRepository.AboutUsCallbackInterface() {
            @Override
            public void onAboutUsContentPresent(String aboutUsContent) {




                ResponseValue responseValue=new ResponseValue(aboutUsContent);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onAboutUsContentNotPresent(String errorMessage) {

            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {


    }

    public static final class ResponseValue implements UseCase.ResponseValue {


        private String aboutUsContent;

        public ResponseValue(String aboutUsContent) {
            this.aboutUsContent = aboutUsContent;
        }

        public String getAboutUsContent(){
            return aboutUsContent;
        }
    }
}
