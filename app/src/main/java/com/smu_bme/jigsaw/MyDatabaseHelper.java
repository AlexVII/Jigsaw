package com.smu_bme.jigsaw;

import android.content.Context;
import android.database.Cursor;
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
            + "date text"
            + "duration integer"
            + "time text"
            + "name text"
            + "remark text";

    public static final String CREATE_SUM = "create table if not exists SUM("
            + "id integer primary key autoincrement,"
            + "date text"
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
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from ? where ? = ?",new String[]{"SUM","id","1"});
        if(cursor.getCount()==0){
            db.execSQL("insert into SUM (date, sum) values (?, ?)",new String[]{"all","0"});
        };
        cursor.close();


        cursor = database.rawQuery("select * from ? where ? = ?",new String[]{"NUM","id","1"});
        if(cursor.getCount()==0){
            db.execSQL("insert into NUM num values ?",new String[]{"0"});
        };
        cursor.close();

        database.close();
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}