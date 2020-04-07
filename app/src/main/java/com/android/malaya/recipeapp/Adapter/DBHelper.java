package com.android.malaya.recipeapp.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String COLUMN_IMAGE = "image_url";
    public static final String TABLE_NAME = "category_details";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";

    public DBHelper(Context context) {
        super(context, "recipe.db", null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table recipe_details(id INTEGER PRIMARY KEY AUTOINCREMENT,image_url TEXT,name TEXT, description TEXT," +
                "ingredients TEXT,instruction TEXT, type TEXT)");

        // Create table SQL query
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_IMAGE + " TEXT,"
                        +  COLUMN_TYPE+ " TEXT"
                        + ")";

        //we need to display image & title only
        sqLiteDatabase.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists recipe_details");
        sqLiteDatabase.execSQL("drop table if exists category_details");
        onCreate(sqLiteDatabase);
    }
}
