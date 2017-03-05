package com.mandasur.app.data.source.database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;

import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.dao.requestdao.News;
import com.mandasur.app.data.source.dao.requestdao.NewsDetail;
import com.mandasur.app.util.ActivityUtil;

import java.util.ArrayList;

/**
 * Created by ambesh on 16-02-2017.
 */
public class SavedNewsTable {




    private String NEWS_TABLE ="news_table";


    public void createUserTableSchema(SQLiteDatabase sqLiteDatabase)
            throws SQLException
    {

        String tableCreationQuery = "create table IF NOT EXISTS "
                + NEWS_TABLE + " (" + NewsDetail.FID + "  varchar "
                + " , "+ NewsDetail.TITLE + " varchar"
                +" , "+ NewsDetail.DESCR + " varchar"
                +" , "+ NewsDetail.PLACE + " varchar"
                +" , "+ NewsDetail.DATE + " varchar"
                +" , "+ NewsDetail.IMAGE1 + " varchar"
                +" , "+ NewsDetail.IMAGE2 + " varchar"
                +" , "+ NewsDetail.DESCR2 + " varchar" +")";


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

    public ArrayList<News> getSavedNewsList(SQLiteDatabase sqLiteDatabase){
        ArrayList<News> newsDetails=new ArrayList<>();

        Cursor cursor=sqLiteDatabase.query(NEWS_TABLE
                , null, null, null, null, null, null);

        while (cursor.moveToNext()){

            News newsDetail=new News();
            newsDetail.setFid(cursor.getString(cursor.getColumnIndex(NewsDetail.FID)));
            newsDetail.setDate(cursor.getString(cursor.getColumnIndex(NewsDetail.DATE)));
            newsDetail.setTitle(Html.fromHtml(cursor.getString(cursor.getColumnIndex(NewsDetail.TITLE))).toString());
            newsDetail.setImage(cursor.getString(cursor.getColumnIndex(NewsDetail.IMAGE1)));

            newsDetails.add(newsDetail);

        }

        cursor.close();

        return newsDetails;
    }







    public NewsDetail getNewsDetailFromDB(SQLiteDatabase sqLiteDatabase,String newsId){

        NewsDetail newsDetail=new NewsDetail();

        Cursor cursor=sqLiteDatabase.query(NEWS_TABLE
                ,null,NewsDetail.FID+"='"+newsId+"'",null,null,null,null);

        while (cursor.moveToFirst()){
            newsDetail.setFid(cursor.getString(cursor.getColumnIndex(NewsDetail.FID)));
            newsDetail.setDate(cursor.getString(cursor.getColumnIndex(NewsDetail.DATE)));
            newsDetail.setTitle(Html.fromHtml(cursor.getString(cursor.getColumnIndex(NewsDetail.TITLE))).toString());
            newsDetail.setImage1(cursor.getString(cursor.getColumnIndex(NewsDetail.IMAGE1)));
            newsDetail.setImage2(cursor.getString(cursor.getColumnIndex(NewsDetail.IMAGE2)));
            newsDetail.setDescr(Html.fromHtml(cursor.getString(cursor.getColumnIndex(NewsDetail.DESCR))).toString());
            newsDetail.setDescr2(Html.fromHtml(cursor.getString(cursor.getColumnIndex(NewsDetail.DESCR2))).toString());
            newsDetail.setPlace(cursor.getString(cursor.getColumnIndex(NewsDetail.PLACE)));
            break;
        }
        cursor.close();
        return newsDetail;
    }




    public void setNewsDetailToDb(SQLiteDatabase sqLiteDatabase,NewsDetail newsDetail){

        ContentValues contentValues=new ContentValues();
        contentValues.put(NewsDetail.FID,newsDetail.getFid());
        contentValues.put(NewsDetail.DATE,newsDetail.getDate());
        contentValues.put(NewsDetail.DESCR,newsDetail.getDescr());
        contentValues.put(NewsDetail.DESCR2,newsDetail.getDescr2());
        contentValues.put(NewsDetail.IMAGE1,newsDetail.getImage1());
        contentValues.put(NewsDetail.IMAGE2,newsDetail.getImage2());
        contentValues.put(NewsDetail.PLACE,newsDetail.getPlace());
        contentValues.put(NewsDetail.TITLE,newsDetail.getTitle());
        sqLiteDatabase.insert(NEWS_TABLE, null, contentValues);

    }

    public boolean isNewsAlreadySaved(SQLiteDatabase sqLiteDatabase,String newsId){
        boolean isNewsAlreadySaved=false;
        Cursor cursor=sqLiteDatabase.query(NEWS_TABLE,new String[]{NewsDetail.FID},NewsDetail.FID+"='"+newsId+"'",null,null,null,null);

        while (cursor.moveToFirst()){
            isNewsAlreadySaved=true;
            break;
        }

        cursor.close();
        return isNewsAlreadySaved;
    }


    public int deleteNewsDetailFromDb(SQLiteDatabase sqLiteDatabase,String newsId){

       return sqLiteDatabase.delete(NEWS_TABLE,NewsDetail.FID+"='"+newsId+"'",null);
    }
}


