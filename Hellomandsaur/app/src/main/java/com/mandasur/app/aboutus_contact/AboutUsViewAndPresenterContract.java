package com.mandasur.app.aboutus_contact;

import com.mandasur.app.BasePresenter;
import com.mandasur.app.BaseView;

/**
 * Created by ambesh on 23-03-2017.
 */
public interface AboutUsViewAndPresenterContract {





    interface AboutUsView extends BaseView<AboutUsPresenter>{
        void showAboutUsContentOnScreen(String aboutUsContent);
        void  showContactDetails(String contactUsContent);

    }
    interface  AboutUsPresenter extends BasePresenter{
         void fetchAboutUsContent();
         void fetchContactDetails();
    }

}
