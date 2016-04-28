package com.smu_bme.jigsaw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gollyrui on 4/28/16.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    private static final String DB_NAME="Jigsaw.db";

    private Context context;
    public static final String CREATE_DATA = "create table if not exists DATA ("
            + "id integer primary key autoincrement, "
            + "date text"
            + "duration integer"
            + "time text"
            + "name text"
            + "remark text";

    public static final String CREATE_SUM = "create table if not exists SUM("
            + "id integer primary key autoincrement,"
            + "date text"
            + "sum integer)";

//    Constructor
    public MyDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA);
        db.execSQL(CREATE_SUM);
//        TODO initialize "all" in table SUM

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}