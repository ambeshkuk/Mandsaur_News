package com.mandasur.app.data.source.database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.data.source.database.MandsaurDataBaseHelper;
import com.mandasur.app.util.ActivityUtil;

import java.util.ArrayList;

/**
 * Created by ambesh on 16-02-2017.
 */
public class CategoriesTable {




    private String CATEGORY_TABLE ="categroy_table";


    public void createUserTableSchema(SQLiteDatabase sqLiteDatabase)
            throws SQLException
    {


        String tableCreationQuery = "create table IF NOT EXISTS "
                + CATEGORY_TABLE + " (" + Category.CATEGORY_NAME
                + "  varchar " + " , "+ Category.CATEGORY_ICON + " varchar"
                +" , "+ Category.CATEGORY_INDICATOR
                + " varchar"+")";


        sqLiteDatabase.execSQL(tableCreationQuery);

    }



    public long insertCategoryToDb(MandsaurDataBaseHelper mandsaurDataBaseHelper
            ,ArrayList<Category> categories){

        SQLiteDatabase sqLiteDatabase=mandsaurDataBaseHelper.getSqLiteDatabase();
        long noOfRecordsInserted=0;
        sqLiteDatabase.delete(CATEGORY_TABLE,
                null
                , null);
        try{
        sqLiteDatabase.beginTransaction();
        for (Category category:categories){




            ContentValues contentValues=new ContentValues();
            contentValues.put(Category.CATEGORY_ICON, category.getCategory_icon());
            contentValues.put(Category.CATEGORY_INDICATOR, category.getCategory_indicator());
            contentValues.put(Category.CATEGORY_NAME, category.getCategory_name());


            noOfRecordsInserted= sqLiteDatabase.
                    insert(CATEGORY_TABLE, null, contentValues);
            if (category.getSubcategories()!=null&&!category.getSubcategories().isEmpty()){
                mandsaurDataBaseHelper.getSubCategoriesTable().
                        insertSubCateoriesToDb(sqLiteDatabase,category.getSubcategories()
                                ,category.getCategory_indicator());
            }

        }

            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            ActivityUtil.printLogFile(e);
        }finally{
            sqLiteDatabase.endTransaction();
        }


        return noOfRecordsInserted;
    }



    public int getRowCount(SQLiteDatabase sqLiteDatabase){
        int i=0;
        Cursor cursor=sqLiteDatabase.
                rawQuery("Select count(" + Category.CATEGORY_NAME + ") " +
                        "From " + CATEGORY_TABLE, null);

        if (cursor.moveToFirst()){
            i=cursor.getInt(0);
        }
        return i;
    }


    public ArrayList<Category> getCategoriesFromDb(SQLiteDatabase sqLiteDatabase){

        ArrayList<Category>    categories=new ArrayList<>();
        int categroyid=0;
        Cursor cursor=sqLiteDatabase.query(CATEGORY_TABLE,null,null,null,null,null,null);

        while (cursor.moveToNext()){
            Category category=new Category();
            category.setCategory_indicator(cursor.getString(cursor.
                    getColumnIndex(Category.CATEGORY_INDICATOR)));
            category.setCategroyId(categroyid);
            categroyid++;
            category.setCategory_icon(cursor.getString(cursor.getColumnIndex(Category.CATEGORY_ICON)));
            category.setCategory_name(cursor.getString(cursor.getColumnIndex(Category.CATEGORY_NAME)));
            categories.add(category);

        }

        cursor.close();
        return categories;
    }
}


