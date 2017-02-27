/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mandasur.app.util;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtil {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (int containerViewId,@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerViewId,fragment,tag);
        transaction.commit();
    }

    public static Typeface getFontAwesomeTypeFace(Context context){




        return Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
    }

    public static boolean isNetworkAvaliable(Context context){
        boolean isNetwokAvailable = false;
        ConnectivityManager connectionManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectionManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectionManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifiInfo != null && wifiInfo.isConnected()) {
            isNetwokAvailable = true;
        } else if (mobileInfo != null && mobileInfo.isConnected()) {
            isNetwokAvailable = true;
        } else {

            isNetwokAvailable = false;
        }

        return isNetwokAvailable;

    }
    public static void Log(String tag,String test){
        Log.i(tag,test);
    }
}
