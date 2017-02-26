package com.mandasur.app.news.buisness;

import com.mandasur.app.BasePresenter;
import com.mandasur.app.BaseView;

/**
 * Created by ambesh on 26-02-2017.
 */
public interface BuisnessListContract {


    interface BuisnessView extends BaseView<BuisnessPresenter>{
        void showNetworkNotAvailaible();
        void  showBusinsessList();
        void showProgressBar();

    }



    interface BuisnessPresenter extends BasePresenter{

        void fetchBusinessAndSubBuisnessCatergoiesFromServer();
        void checkIfNetwrokIsPresent();

    }
}
