package com.mandasur.app.news.usecase;

import android.support.annotation.NonNull;

import com.mandasur.app.SplashScrees;
import com.mandasur.app.UseCase;
import com.mandasur.app.data.source.AdvertisingDataRepository;
import com.mandasur.app.data.source.SubCategoryDataRepository;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.dao.requestdao.AdvertiseRequest;

import java.util.ArrayList;






/**
 * Created by ambesh on 11-02-2017.
 */
public class GetAdvertisingList extends UseCase<GetAdvertisingList.RequestValues,GetAdvertisingList.ResponseValue>  {


     private AdvertisingDataRepository advertisingDataRepository;
    private SplashScrees splashScrees;
   public GetAdvertisingList(AdvertisingDataRepository
                                     advertisingDataRepository){

       this.advertisingDataRepository=advertisingDataRepository;

   }
    @Override
    public void executeUseCase(final RequestValues requestValues) {

        advertisingDataRepository
                .loadAdvertiseMentToDb(new AdvertisingDataRepository.LoadAdvertisinments() {
                    @Override
                    public void onAdvertismentsLoaded(boolean advertisementsLoaded) {

                        ResponseValue responseValue=new ResponseValue();
                        responseValue.setIsAdvertisementLoaded(advertisementsLoaded);
                        getUseCaseCallback().onSuccess(responseValue);
                    }

                    @Override
                    public void onAdvertisiementsNotVaialbel() {

                    }
                },requestValues.getAdvertiseRequest());

    }


    public static final class RequestValues implements UseCase.RequestValues {

        public AdvertiseRequest getAdvertiseRequest() {
            return advertiseRequest;
        }

        public void setAdvertiseRequest(AdvertiseRequest advertiseRequest) {
            this.advertiseRequest = advertiseRequest;
        }

        AdvertiseRequest advertiseRequest;



    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        public boolean isAdvertisementLoaded() {
            return isAdvertisementLoaded;
        }

        public void setIsAdvertisementLoaded(boolean isAdvertisementLoaded) {
            this.isAdvertisementLoaded = isAdvertisementLoaded;
        }

        boolean isAdvertisementLoaded=false;


    }
}




