package com.mandasur.app.data.source;

import android.support.annotation.NonNull;

import com.mandasur.app.data.source.dao.Ads;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.database.DatabaseNewsDataSource;


import java.util.ArrayList;

/**
 * Created by ambesh on 11-02-2017.
 */
public class AdvertisingDataRepository {





    private DatabaseNewsDataSource databaseNewsDataSource;


    public AdvertisingDataRepository(DatabaseNewsDataSource databaseNewsDataSource) {
        this.databaseNewsDataSource = databaseNewsDataSource;

    }

    public interface LoadAdvertisinments {

        void onAdvertismentsLoaded(ArrayList<Ads> adses);

        void onAdvertisiementsNotVaialbel();

    }

    public interface LoadAdvertisemetnToDB{




        public void advertisementLoaded();



    }
    public void loadAdvertiseMentToDb(@NonNull LoadAdvertisinments loadCategoriesCallBack) {








    }



}
