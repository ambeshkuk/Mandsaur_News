package com.mandasur.app.data.source.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ambesh on 09-02-2017.
 */
public class DatabaseNewsDataSource {






    private static  MandsaurDataBaseHelper mandsaurDataBaseHelper=null;

    public static MandsaurDataBaseHelper getInstance(Context context)
    {
        if (mandsaurDataBaseHelper==null){
            mandsaurDataBaseHelper=new MandsaurDataBaseHelper(context,null);
            mandsaurDataBaseHelper.open();
        }

        return mandsaurDataBaseHelper;
    }

    public MandsaurDataBaseHelper getMandsaurDataBaseHelper(){
        return mandsaurDataBaseHelper;
    }

}
