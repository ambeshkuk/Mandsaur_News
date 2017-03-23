package com.mandasur.app.data.source;

import android.support.annotation.NonNull;

import com.mandasur.app.data.source.dataxml.AboutUsDataSource;

/**
 * Created by ambesh on 23-03-2017.
 */
public class AboutUsDataRepository {
    private AboutUsDataSource aboutUsDataSource;

    public AboutUsDataRepository(AboutUsDataSource aboutUsDataSource) {
        this.aboutUsDataSource = aboutUsDataSource;
    }

    public interface AboutUsCallbackInterface{

        void onAboutUsContentPresent(String aboutUsContent);
        void onAboutUsContentNotPresent(String  errorMessage);
    }

    public void laodAboutUsContent(@NonNull AboutUsCallbackInterface aboutUsCallbackInterface){

        String aboutUsContent=aboutUsDataSource.getAboutUsContentFromDataXml();
        aboutUsCallbackInterface.onAboutUsContentNotPresent(aboutUsContent);
    }
}
