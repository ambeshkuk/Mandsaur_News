package com.mandasur.app.aboutus_contact;

import com.mandasur.app.UseCase;
import com.mandasur.app.news.usecase.GetAboutUsContent;

/**
 * Created by ambesh on 23-03-2017.
 */
public class AboutUsPresenter implements AboutUsViewAndPresenterContract.AboutUsPresenter {

    private GetAboutUsContent getAboutUsContent;
    private AboutUsViewAndPresenterContract.AboutUsView aboutUsActivity;

    public AboutUsPresenter(GetAboutUsContent getAboutUsContent
            , AboutUsViewAndPresenterContract.AboutUsView aboutUsActivity) {
        this.getAboutUsContent = getAboutUsContent;
        this.aboutUsActivity = aboutUsActivity;
    }

    @Override
    public void fetchAboutUsContent() {
        getAboutUsContent.setUseCaseCallback(new UseCase.UseCaseCallback<GetAboutUsContent.ResponseValue>() {
            @Override
            public void onSuccess(GetAboutUsContent.ResponseValue response) {

                aboutUsActivity.showAboutUsContentOnScreen(response.getAboutUsContent());
            }

            @Override
            public void onError(String errorString) {

            }
        });
        getAboutUsContent.executeUseCase(null);
    }

    @Override
    public void fetchContactDetails() {

    }

    @Override
    public void start() {

    }
}
