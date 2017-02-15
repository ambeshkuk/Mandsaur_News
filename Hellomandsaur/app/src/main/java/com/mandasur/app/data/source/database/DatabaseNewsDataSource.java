package com.mandasur.app.data.source.database;

import android.content.Context;

/**
 * Created by ambesh on 09-02-2017.
 */
public class DatabaseNewsDataSource {





    private static MandsaurDataBaseHelper mandsaurDataBaseHelper;

    public static MandsaurDataBaseHelper getInstance(Context context)
    {
        if (mandsaurDataBaseHelper==null){
            mandsaurDataBaseHelper=new MandsaurDataBaseHelper(context,null);
            mandsaurDataBaseHelper.open();
        }

        return mandsaurDataBaseHelper;
    }
}
