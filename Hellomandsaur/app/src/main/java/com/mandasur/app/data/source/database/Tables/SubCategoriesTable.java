package com.mandasur.app.data.source.database.Tables;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.mandasur.app.data.source.dao.Category;
import com.mandasur.app.data.source.dao.SubCategories;
import com.mandasur.app.util.ActivityUtil;

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
                + SUBCATEGORY_TABLE + " ("
                + SubCategories.SUBCATEGORY_NAME + " varchar"+","
                + Category.CATEGORY_INDICATOR + " varchar"
                +" , "+ SubCategories.SUBCATEGORY_INDICATOR
                + " varchar" +" , "+ SubCategories.IS_ITEM_CHECKED
                + " INTEGER" +")";


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
        ActivityUtil.Log(SubCategoriesTable.class.getSimpleName(), subCategoryArray.toString());
        return subCategoryArray.toString();
    }


    public int updateIsItemCheckdOrNot(SQLiteDatabase sqLiteDatabase,String subCategoryIndicator,boolean isItemChecked){


        ContentValues contentValues=new ContentValues();
        int itemCheck=isItemChecked ? 1 : 0;
        contentValues.put(SubCategories.IS_ITEM_CHECKED, itemCheck);

      return   sqLiteDatabase.update(SUBCATEGORY_TABLE, contentValues
              , SubCategories.SUBCATEGORY_INDICATOR + "='" + subCategoryIndicator + "'", null);

    }


    public int insertSubCateoriesToDb(SQLiteDatabase sqLiteDatabase,
                                      ArrayList<SubCategories>
                                              subCategories,String categoryIndicator){

        int subCategoryCount=0;

        try{
            sqLiteDatabase.beginTransaction();

            String sql = "Insert or Replace into "+SUBCATEGORY_TABLE
                    +" ("+Category.CATEGORY_INDICATOR
                    +","+SubCategories.SUBCATEGORY_INDICATOR+","+SubCategories.SUBCATEGORY_NAME
                    +","+SubCategories.IS_ITEM_CHECKED+") values(?,?,?,?)";

            SQLiteStatement sqLiteStatement=sqLiteDatabase.compileStatement(sql);
            for (SubCategories subCategory:subCategories){

                sqLiteStatement.bindString(1,categoryIndicator);
                sqLiteStatement.bindString(2, subCategory.getSubcategory_indicator());
                sqLiteStatement.bindString(3, subCategory.getSubcategory_name());
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

    public int getRowCount(SQLiteDatabase sqLiteDatabase){
        int i=0;
        Cursor cursor=sqLiteDatabase.rawQuery("Select count("+SubCategories.SUBCATEGORY_INDICATOR+") From "+SUBCATEGORY_TABLE,null);

        if (cursor.moveToFirst()){
            i=cursor.getInt(0);
        }
        return i;
    }
    public ArrayList<SubCategories> getSubCategoiesFromDb(SQLiteDatabase sqLiteDatabase){

        ArrayList<SubCategories> subCategories=new ArrayList<>();

        String[] columns={SubCategories.SUBCATEGORY_NAME,SubCategories.SUBCATEGORY_INDICATOR,SubCategories.IS_ITEM_CHECKED};

        Cursor cursor=sqLiteDatabase.query(SUBCATEGORY_TABLE,columns,null,null,null,null,null);
        while (cursor.moveToNext()){
            SubCategories subCategories1=new SubCategories();
            subCategories1.setIsItemChecked(cursor.getInt(cursor.getColumnIndex(SubCategories.IS_ITEM_CHECKED))>0?true:false);

            subCategories1.setSubcategory_name(cursor.getString(cursor.getColumnIndex(SubCategories.SUBCATEGORY_NAME)));
            subCategories1.setSubcategory_indicator(cursor.getString(cursor.getColumnIndex(SubCategories.SUBCATEGORY_INDICATOR)));
            subCategories.add(subCategories1);
        }

        cursor.close();
        return subCategories;
    }

    public String getSubCategoryNameFromSubCategroyIndicator(SQLiteDatabase sqLiteDatabase,String subCateroryName){





        String subCategoryName=null;
        String[] columns={SubCategories.SUBCATEGORY_NAME};

        Cursor cursor=sqLiteDatabase.query(SUBCATEGORY_TABLE,columns,
                SubCategories.SUBCATEGORY_INDICATOR+"='"+subCateroryName+"'",null,null,null,null);







        if (cursor.moveToFirst()){
            subCategoryName=cursor.getString(cursor.getColumnIndex(SubCategories.SUBCATEGORY_NAME));
        }

        return subCategoryName;
    }
}


