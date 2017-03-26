package com.mandasur.app.data.source.database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetail;
import com.mandasur.app.util.ActivityUtil;

import java.util.ArrayList;

/**
 * Created by ambesh on 16-02-2017.
 */
public class CachedNewsTable {




    private String CACHED_NEWS_TABLE ="cached_news_table";
    private String MAIN_CATEGORY_INDICATOR="main_category_indicator";
    private String JSON_STING="json_string";

    public void createUserTableSchema(SQLiteDatabase sqLiteDatabase)
            throws SQLException
    {

        String tableCreationQuery = "create table IF NOT EXISTS "
                + CACHED_NEWS_TABLE + " (" + JSON_STING + "  varchar "
               + " , "+ MAIN_CATEGORY_INDICATOR +" varchar"+
                ")";


        sqLiteDatabase.execSQL(tableCreationQuery);

    }

    public boolean isNewsTableEmpty(SQLiteDatabase sqLiteDatabase,String mainCategoryName){

        boolean ifNoNewsIsSaved=true;

        Cursor cursor=sqLiteDatabase.rawQuery("Select COUNT(*) From "
                + CACHED_NEWS_TABLE+" WHERE "
                +MAIN_CATEGORY_INDICATOR+"='"+mainCategoryName+"'"
                , null);
        if (cursor.moveToFirst()){

            if (cursor.getInt(0)>0){
                ifNoNewsIsSaved=false;
            }

        }
        return ifNoNewsIsSaved;
    }

    public StringBuffer getNewsListFromDb(SQLiteDatabase sqLiteDatabase,String mainCategoryIndicator){
        StringBuffer stringBuffer=new StringBuffer();
        Cursor cursor=sqLiteDatabase.query(CACHED_NEWS_TABLE
                , null
                , MAIN_CATEGORY_INDICATOR +"='"+mainCategoryIndicator+"'"
                , null, null, null, null);

        while (cursor.moveToNext()){

            stringBuffer.append(cursor.getString(cursor.getColumnIndex(JSON_STING)));

        }

        cursor.close();

        return stringBuffer;
    }











    public long setNewsListToDb(SQLiteDatabase sqLiteDatabase,
                                String jsonString,String mainCategoryIndicator){


        long noOfRecordsInserted=0;
        sqLiteDatabase.delete(CACHED_NEWS_TABLE,
                MAIN_CATEGORY_INDICATOR +"='"+mainCategoryIndicator+"'"
                ,null);

        sqLiteDatabase.beginTransaction();
        try{
                ContentValues contentValues=new ContentValues();
                contentValues.put(JSON_STING,jsonString);

                contentValues.put(MAIN_CATEGORY_INDICATOR, mainCategoryIndicator);
            noOfRecordsInserted= sqLiteDatabase.
            insert(CACHED_NEWS_TABLE, null, contentValues);

            sqLiteDatabase.setTransactionSuccessful();
        }catch(Exception e){
            ActivityUtil.printLogFile(e);
        }finally{
            sqLiteDatabase.endTransaction();
        }


        return noOfRecordsInserted;

    }


}


