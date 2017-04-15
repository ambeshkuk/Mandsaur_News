package com.mandasur.app;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ambesh on 14-03-2017.
 */
public class MandsaurApp extends Application {

    private Picasso picasso;

//    public Picasso getPicassoInstance(){
//        if (picasso==null){
//            OkHttpClient client = new OkHttpClient(); client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
//            picasso = new Picasso.Builder(context) .downloader(new OkHttpDownloader(client)) .build();
//        }
//        return picasso;
//    }
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .setDefaultFontPath("fonts/Roboto-Regular.ttf").build());
    }
}
