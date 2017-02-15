package com.mandasur.app.data.source.database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mandasur.app.data.source.dao.SubCategories;

import java.util.ArrayList;

/**
 * Created by ambesh on 16-02-2017.
 */
public class SubCategoriesTable {




    private String SUBCATEGORY_TABLE="subcategory_table";


    public void createUserTableSchema(SQLiteDatabase sqLiteDatabase)
            throws SQLException
    {

        String tableCreationQuery = "create table IF NOT EXISTS "
                + SUBCATEGORY_TABLE + " (" + SubCategories.SUBCATEGORY_ID + "  varchar " + " , "+ SubCategories.SUBCATEGORY_NAME + " varchar"
                +" , "+ SubCategories.SUBCATEGORY_INDICATOR + " varchar" +" , "+ SubCategories.IS_ITEM_CHECKED + " INTEGER" +")";


        sqLiteDatabase.execSQL(tableCreationQuery);

    }


    public String getStringArrayIfSelectedSubCategory(SQLiteDatabase sqLiteDatabase){
        StringBuilder subCategoryArray=new StringBuilder();





        String[] columns={SubCategories.SUBCATEGORY_INDICATOR};

        Cursor cursor=sqLiteDatabase.query(SUBCATEGORY_TABLE,columns,SubCategories.IS_ITEM_CHECKED+"=1",null,null,null,null);

        while (cursor.moveToNext()){
            if (!cursor.isLast()){
                subCategoryArray.append(cursor.getString(cursor.getColumnIndex(SubCategories.SUBCATEGORY_INDICATOR))).append(",");
            }
            else {
                subCategoryArray.append(cursor.getString(cursor.getColumnIndex(SubCategories.SUBCATEGORY_INDICATOR)));
            }

        }

        cursor.close();
        return subCategoryArray.toString();
    }


    public int updateIsItemCheckdOrNot(SQLiteDatabase sqLiteDatabase,String itemId,boolean isItemChecked){


        ContentValues contentValues=new ContentValues();
        contentValues.put(SubCategories.IS_ITEM_CHECKED, isItemChecked ? 1 : 0);

      return   sqLiteDatabase.update(SUBCATEGORY_TABLE, contentValues, SubCategories.SUBCATEGORY_ID + "'" + itemId + "'", null);

    }

    public int insertSubCateoriesToDb(SQLiteDatabase sqLiteDatabase,ArrayList<SubCategories>  subCategories){

        int subCategoryCount=0;

        try{
            sqLiteDatabase.beginTransaction();

            String sql = "Insert or Replace into "+SUBCATEGORY_TABLE
                    +" ("+SubCategories.SUBCATEGORY_ID
                    +","+SubCategories.SUBCATEGORY_INDICATOR+","+SubCategories.SUBCATEGORY_NAME
                    +","+SubCategories.IS_ITEM_CHECKED+") values(?,?,?,?)";

            SQLiteStatement sqLiteStatement=sqLiteDatabase.compileStatement(sql);
            for (SubCategories subCategory:subCategories){
                sqLiteStatement.bindString(1,subCategory.getSubCategoryId());
                sqLiteStatement.bindString(2, subCategory.getSubCategoryIndicator());
                sqLiteStatement.bindString(3, subCategory.getSubCategoryName());
                sqLiteStatement.bindLong(4, subCategory.isItemChecked()?1:0);
                sqLiteStatement.execute();


            }
            sqLiteDatabase.setTransactionSuccessful();
        }


        finally {
            sqLiteDatabase.endTransaction();
        }





        return subCategoryCount;

    }

    
}


