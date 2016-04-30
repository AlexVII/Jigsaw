package com.smu_bme.jigsaw;
    public static final String CREATE_NUM = "create table if not exists NUM("
            + "id integer primary key autoincrement,"
            + "num integer)";
//    Constructor
    public MyDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA);
        db.execSQL(CREATE_SUM);
        db.execSQL(CREATE_NUM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gollyrui on 4/28/16.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    private static final String DB_NAME="Jigsaw.db";

    private Context context;
    public static final String CREATE_DATA = "create table if not exists DATA ("
            + "id integer primary key autoincrement, "
            + "date text not null,"
            + "duration integer not null,"
            + "time text not null,"
            + "name text,"
            + "remark text)";

    public static final String CREATE_SUM = "create table if not exists SUM("
            + "id integer primary key autoincrement,"
            + "date text unique not null,"
            + "sum integer)";

    public static final String CREATE_NUM = "create table if not exists NUM("
            + "id integer primary key autoincrement,"
            + "num integer)";
//    Constructor
    public MyDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA);
        db.execSQL(CREATE_SUM);
        db.execSQL(CREATE_NUM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}