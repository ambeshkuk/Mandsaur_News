package com.mandasur.app.data.source.database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mandasur.app.data.source.dao.Ads;
import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.util.ActivityUtil;

import java.util.ArrayList;

/**
 * Created by ambesh on 16-02-2017.
 */
public class Advertising_Table {




    private String ADVERTISING_TABLE ="advertising_table";

    private static int currentIndex=0;
    private String CURRENT_INDEX="current_index";

    public void createUserTableSchema(SQLiteDatabase sqLiteDatabase)
            throws SQLException
    {


        String tableCreationQuery = "create table IF NOT EXISTS "
                + ADVERTISING_TABLE + " (" +CURRENT_INDEX+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+ Ads.ID
                + "  varchar " + " , "+ Ads.AD_IMAGE_FULL + " varchar"
                +" , "+ Ads.AD_IMAGE_PREVIEW
                + " varchar"+","+Ads.AD_TITLE+" varchar"+
                ","+Ads.AD_URL+" varchar"+
                ")";


        sqLiteDatabase.execSQL(tableCreationQuery);

    }



    public long insertAdsToDb(SQLiteDatabase sqLiteDatabase
            ,ArrayList<Ads> adses){

        sqLiteDatabase.delete(ADVERTISING_TABLE,
                null,null);
        int adsCount=0;

        try{
            sqLiteDatabase.beginTransaction();





            String sql = "Insert or Replace into "+ADVERTISING_TABLE
                    +" ("+Ads.ID
                    +","+ Ads.AD_URL+","+Ads.AD_TITLE+","+Ads.AD_IMAGE_PREVIEW+
                    ","+Ads.AD_IMAGE_FULL
                    +") values(?,?,?,?,?)";

            SQLiteStatement sqLiteStatement=sqLiteDatabase.compileStatement(sql);
            for (Ads ads:adses){

                sqLiteStatement.bindString(1,ads.getId());
                sqLiteStatement.bindString(2, ads.getAd_url());
                sqLiteStatement.bindString(3, ads.getAd_title());
                sqLiteStatement.bindString(4, ads.getAd_image_preview());
                sqLiteStatement.bindString(5, ads.getAd_image_full());

                sqLiteStatement.execute();
                adsCount++;

            }
            sqLiteDatabase.setTransactionSuccessful();
        }


        finally {
            sqLiteDatabase.endTransaction();
        }





        return adsCount;
    }



    public int getRowCount(SQLiteDatabase sqLiteDatabase){
        int i=0;
        Cursor cursor=sqLiteDatabase.
                rawQuery("Select count(" + Ads.ID + ") " +
                        "From " + ADVERTISING_TABLE, null);

        if (cursor.moveToFirst()){
            i=cursor.getInt(0);
        }
        return i;
    }


    public Ads  getCurrentPriorityAdd(SQLiteDatabase sqLiteDatabase){

        Ads ads=null;
        Cursor cursor=sqLiteDatabase.query(ADVERTISING_TABLE,
                null,CURRENT_INDEX+"="+currentIndex,null,null,null,null);

        while (cursor.moveToNext()){

            ads=new Ads();
            ads.setAd_title(cursor.getString(cursor.getColumnIndex(Ads.AD_TITLE)));
            ads.setAd_image_full(cursor.getString(cursor.getColumnIndex(Ads.AD_IMAGE_FULL)));
            ads.setAd_image_preview(cursor.getString(cursor.getColumnIndex(Ads.AD_IMAGE_PREVIEW)));

            ads.setAd_url(cursor.getString(cursor.getColumnIndex(Ads.AD_URL)));
        }

        cursor.close();

        return ads;
    }
}


