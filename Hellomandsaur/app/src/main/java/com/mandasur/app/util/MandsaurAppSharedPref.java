package com.mandasur.app.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ambesh on 25-02-2017.
 */
public class MandsaurAppSharedPref {







    private static String SHARED_PREF="shared_pref";
    private static String CATEGORY_NAME="category_name";

    public static void setCategoryName(Context context,String categroyName){
        SharedPreferences sharedPreferences=context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(CATEGORY_NAME,categroyName);
        editor.commit();

    }


    public static String getCategoryName(Context context){

       return context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE).getString(CATEGORY_NAME,"");
    }
}
