package com.smu_bme.jigsaw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gollyrui on 4/28/16.
 */
public class DbHelper{
    Context context;
    private MyDatabaseHelper dbHelper;
    private String tableName;
    private final String tableSum= "SUM";

    private class DbData {
        int id;
        String date;
        String duration;
        String time;
        String name;
        String remark;
        int sumDate;
        int sumAll;
    }


    public DbHelper(Context context, String tableNameIn){
        this.context = context;
        dbHelper= new MyDatabaseHelper(context);
        tableName = tableNameIn;
    }

    public DbHelper(Context context){
        this.context = context;
        dbHelper= new MyDatabaseHelper(context);
        tableName = "DATA";

    }
    private int findSum(String mode,String date){
        int sum;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if(mode == "date") {
            Cursor cursorSum = db.rawQuery("select * from ? where ?=?", new String[]{tableSum, "date", date});
            sum =  cursorSum.getInt(cursorSum.getColumnIndex("sum"));
        } else if(mode == "all"){
            Cursor cursorSum = db.rawQuery("select * from ? where ?=?", new String[]{tableSum, "id", "1"});
            sum = cursorSum.getInt(cursorSum.getColumnIndex("sum"));
        }
        return sum;
    }

    public List<DbData> findData(String mode, String item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<DbData> list = new ArrayList<DbData>();

            Cursor cursor = db.rawQuery("select * from ? where ?=?", new String[]{tableName, mode, item});
            while (cursor.moveToFirst()) {
                DbData dbData = new DbData();
                dbData.id = cursor.getInt(cursor.getColumnIndex("id"));
                dbData.date = cursor.getString(cursor.getColumnIndex("data"));
                dbData.duration = cursor.getString(cursor.getColumnIndex("duration"));
                dbData.time = cursor.getString(cursor.getColumnIndex("time"));
                dbData.name = cursor.getString(cursor.getColumnIndex("name"));
                dbData.remark = cursor.getString(cursor.getColumnIndex("remark"));
                list.add(dbData);
            }
            cursor.close();
            db.close();
            return list;

    }

    public void addData(String name, String date, String time, String duration, String remark) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        //开始组装数据
        values.put("name", name);
        values.put("date", date);
        values.put("time", time);
        values.put("duration", duration);
        values.put("remark", remark);
        db.insert("DATA", null, values);
        values.clear();
//            this.findData("",);
    }


    public void deleteData(String id) {
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        db.execSQL("delete from DATA where id=? ", new String[]{id});
    }


}
