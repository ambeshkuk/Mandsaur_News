package com.mandasur.app.data.source.database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;

import com.google.gson.Gson;
import com.mandasur.app.data.source.dao.requestdao.BaseNews;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetail;
import com.mandasur.app.data.source.dao.requestdao.NewsDetailsFromResponse;
import com.mandasur.app.util.GsonUtil;

import java.util.ArrayList;

/**
 * Created by ambesh on 16-02-2017.
 */
public class SavedNewsTable {




    private String NEWS_TABLE ="news_table";
    private String NEWS_DETAIL_JSON="news_detail_json";
    private String NEWS_ID="news_id";


    public void createUserTableSchema(SQLiteDatabase sqLiteDatabase)
            throws SQLException
    {

        String tableCreationQuery = "create table IF NOT EXISTS "
                + NEWS_TABLE
                + " ("
                + NEWS_DETAIL_JSON + "  varchar "
                + ","+NEWS_ID + "  varchar "

                + ")";


        sqLiteDatabase.execSQL(tableCreationQuery);

    }

    public boolean isSavedNewsEmpty(SQLiteDatabase sqLiteDatabase){

        boolean ifNoNewsIsSaved=false;

        Cursor cursor=sqLiteDatabase.rawQuery("Select COUNT(*) From "+NEWS_TABLE,null);
        if (cursor.moveToFirst()){

            if (cursor.getInt(0)==0){
                ifNoNewsIsSaved=false;
            }

        }
        return ifNoNewsIsSaved;
    }

    public ArrayList<BaseNews> getSavedNewsList(SQLiteDatabase sqLiteDatabase){
        ArrayList<BaseNews> newsArrayList=new ArrayList<>();
        Gson gson= GsonUtil.getGsonInstance();


        Cursor cursor=sqLiteDatabase.query(NEWS_TABLE
                , null, null, null, null, null, null);

        while (cursor.moveToNext()){
            NewsDetailsFromResponse newsDetailsFromResponse=gson.fromJson(cursor.getString(cursor.getColumnIndex(NEWS_DETAIL_JSON))
                    ,NewsDetailsFromResponse.class);

            if (newsDetailsFromResponse.getData()!=null&&!newsDetailsFromResponse.getData().isEmpty()){
                newsArrayList.add(newsDetailsFromResponse.getData().get(0).mapToNews());

            }

        }

        cursor.close();

        return newsArrayList;
    }







    public String getNewsDetailFromDB(SQLiteDatabase sqLiteDatabase,String newsId){

        Gson gson=GsonUtil.getGsonInstance();
            String newsDetailResponseBean="";

        Cursor cursor=sqLiteDatabase.query(NEWS_TABLE
                ,null,NEWS_ID+"='"+newsId+"'",null,null,null,null);

        while (cursor.moveToFirst()){

            return cursor.getString(cursor.getColumnIndex(NEWS_DETAIL_JSON));


        }
        cursor.close();
        return newsDetailResponseBean;
    }




    public void setNewsDetailToDb(SQLiteDatabase sqLiteDatabase,String  newsDetail,String newsId){

        ContentValues contentValues=new ContentValues();
        contentValues.put(NEWS_DETAIL_JSON,newsDetail);
        contentValues.put(NEWS_ID,newsId);
        sqLiteDatabase.insert(NEWS_TABLE, null, contentValues);

    }

    public boolean isNewsAlreadySaved(SQLiteDatabase sqLiteDatabase,String newsId){
        boolean isNewsAlreadySaved=false;
        Cursor cursor=sqLiteDatabase.query(NEWS_TABLE,new String[]{NEWS_ID},NEWS_ID+"='"+newsId+"'",null,null,null,null);

        while (cursor.moveToFirst()){
            isNewsAlreadySaved=true;
            break;
        }

        cursor.close();
        return isNewsAlreadySaved;
    }


    public int deleteNewsDetailFromDb(SQLiteDatabase sqLiteDatabase,String newsId){

       return sqLiteDatabase.delete(NEWS_TABLE,NEWS_ID+"='"+newsId+"'",null);
    }
}


