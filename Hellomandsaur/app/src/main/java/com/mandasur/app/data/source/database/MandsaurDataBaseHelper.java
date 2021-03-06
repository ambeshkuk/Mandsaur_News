package com.mandasur.app.data.source.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.mandasur.app.R;
import com.mandasur.app.data.source.database.Tables.Advertising_Table;
import com.mandasur.app.data.source.database.Tables.CachedNewsTable;
import com.mandasur.app.data.source.database.Tables.CategoriesTable;
import com.mandasur.app.data.source.database.Tables.SavedNewsTable;
import com.mandasur.app.data.source.database.Tables.SubCategoriesTable;
import com.mandasur.app.util.ActivityUtil;


/**
 * Helper class for FAF database manages the creation and updation of    database
 * Created by Ideavate Solution on 5/11/2015.
 */
public class MandsaurDataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH ;
    private static String DB_NAME ;


    private SubCategoriesTable subCategoriesTable;
    private SavedNewsTable savedNewsTable;
    private CachedNewsTable cachedNewsTable;
    private CategoriesTable categoriesTable;
    private Advertising_Table advertising_table;
    private SQLiteDatabase sqLiteDatabase;

    public MandsaurDataBaseHelper(Context context, SQLiteDatabase.CursorFactory factory){




        super(context, context.getString(R.string.db_name), factory,context.getResources().getInteger(R.integer.db_version));




        subCategoriesTable=new SubCategoriesTable();
        savedNewsTable=new SavedNewsTable();
        cachedNewsTable=new CachedNewsTable();
        categoriesTable=new CategoriesTable();
        advertising_table=new Advertising_Table();


    }






    public MandsaurDataBaseHelper open() throws SQLException {

        try {
            sqLiteDatabase = getWritableDatabase();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            ActivityUtil.printException(e);
        }
        return this;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            subCategoriesTable.createUserTableSchema(db);
            savedNewsTable.createUserTableSchema(db);
            cachedNewsTable.createUserTableSchema(db);
            categoriesTable.createUserTableSchema(db);
            advertising_table.createUserTableSchema(db);
        }
        catch(SQLException e){

            ActivityUtil.printException(e);

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



    public Advertising_Table getAdvertising_table(){
        return advertising_table;
    }

    public SubCategoriesTable getSubCategoriesTable(){


        return subCategoriesTable;
    }

    public SavedNewsTable getSavedNewsTable(){
        return savedNewsTable;
    }
    public CachedNewsTable getCachedNewsTable(){
        return cachedNewsTable;
    }
    public CategoriesTable getCategoriesTable(){return  categoriesTable;}
}
