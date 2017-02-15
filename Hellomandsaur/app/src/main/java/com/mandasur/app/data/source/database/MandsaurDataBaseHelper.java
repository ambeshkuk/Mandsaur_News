package com.mandasur.app.data.source.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.mandasur.app.R;
import com.mandasur.app.data.source.database.Tables.SubCategoriesTable;




/**
 * Helper class for FAF database manages the creation and updation of    database
 * Created by Ideavate Solution on 5/11/2015.
 */
public class MandsaurDataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH ;
    private static String DB_NAME ;


    private SubCategoriesTable subCategoriesTable;


    private SQLiteDatabase sqLiteDatabase;
    public MandsaurDataBaseHelper(Context context, SQLiteDatabase.CursorFactory factory){




        super(context, context.getString(R.string.db_name), factory,context.getResources().getInteger(R.integer.db_version));




        subCategoriesTable=new SubCategoriesTable();

    }






    public MandsaurDataBaseHelper open() throws SQLException {

        try {
            sqLiteDatabase = getWritableDatabase();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return this;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            subCategoriesTable.createUserTableSchema(db);

        }
        catch(SQLException e){



        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public SQLiteDatabase getSqLiteDatabase(){
        if(sqLiteDatabase!=null&&sqLiteDatabase.isOpen()){
            return sqLiteDatabase;
        }
        else{
            sqLiteDatabase=getWritableDatabase();

            return sqLiteDatabase;
        }

    }




    public SubCategoriesTable getSubCategoriesTable(){


        return subCategoriesTable;
    }


}
