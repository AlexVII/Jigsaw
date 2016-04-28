package com.smu_bme.jigsaw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by gollyrui on 4/28/16.
 */
public class DbHelper{
    Context context;
    private MyDatabaseHelper dbHelper;
    private String tableName;
    private final String tableSum= "SUM";


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

    //To find the sum of duration on a day
    private int findSum(String date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Initialization
        int sum ;
        //get the sum of the very date
        Cursor cursorSum = db.rawQuery("select * from ? where ? = ?", new String[]{tableSum, "date", date});

        if(cursorSum.getCount()>0) { // if found
            sum =  cursorSum.getInt(cursorSum.getColumnIndex("sum"));
        } else {sum = -1;}// if not find, then the sum of duration of the very date is not created yet

        cursorSum.close();
        db.close();
        return sum;
    }

    private int findAll(){// find the sum of all date
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorSum = db.rawQuery("select * from ? where ? = ?", new String[]{tableSum, "id", "1"});
        int sum = cursorSum.getInt(cursorSum.getColumnIndex("sum"));
        cursorSum.close();
        db.close();
        return sum;
    }

    public ArrayList<DbData> findData(String mode, String item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<DbData> list = new ArrayList<>();

            Cursor cursor = db.rawQuery("select * from ? where ? = ?", new String[]{tableName, mode, item});
            if(cursor.getCount()>0) {
                while (cursor.moveToFirst()) {
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    int sumAll = this.findAll();
                    int sumDate = this.findSum(date);
                    DbData dbData = new DbData(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("date")),
                            cursor.getInt(cursor.getColumnIndex("duration")),
                            cursor.getString(cursor.getColumnIndex("time")),
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getString(cursor.getColumnIndex("remark")),
                            sumDate,
                            sumAll);
                    list.add(dbData);
                }
            }//else return null
                cursor.close();
                db.close();

        //TODO what if the list is null
            return list;
    }

    private void addSum(String date,int duration){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("insert into SUM (date,sum) values (?, ?)",new Object[]{date,duration});
    }

    public void addData(DbData dbDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //开始组装数据
        values.put("name", dbDate.getName());
        values.put("date", dbDate.getDate());
        values.put("time", dbDate.getTime());
        values.put("duration",dbDate.getDuration());
        values.put("remark", dbDate.getRemark());
        db.insert("DATA", null, values);
        values.clear();

        // SUM
        int sumSameDate= this.findSum(dbDate.getDate());
        int sumAll = this.findAll();
        if(sumSameDate==-1) {
            db.execSQL("insert into SUM num values ?",new String[]{String.valueOf(dbDate.getDuration())});
            int nSumAll =sumAll+dbDate.getDuration();
            db.execSQL("update SUM set sum = ? where id = 1", new String[]{String.valueOf(nSumAll)});
        }
        else{
            updateSum(dbDate.getDate(),dbDate.getDuration());
        }
        db.close();
    }


    public void deleteData(int id) {
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        db.execSQL("delete from DATA where id = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateData(DbData dbData){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<DbData> previousList = this.findData("id",String.valueOf( dbData.getId() ));
        if(!previousList.isEmpty()) {
            db.execSQL("update DATA set date = ?, duration = ?, time = ?, name = ?, remark = ? where id = ?",
                    new String[]{dbData.getDate(),
                            String.valueOf(dbData.getDuration()),
                            dbData.getTime(),
                            dbData.getName(),
                            dbData.getRemark()
                    });
            DbData previous = previousList.get(0);
            int deltaDuration = dbData.getDuration()-previous.getDuration();
            updateSum(dbData.getDate(), deltaDuration);
        }

        db.close();
    }

    private void updateSum(String date,int deltaDuration){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int nDateSum = findSum(date)+deltaDuration;
        db.execSQL("update SUM set sum = ? where date = ?", new String[]{String.valueOf(nDateSum),date});
        int nDataAll = findAll() + deltaDuration;
        db.execSQL("update SUM set sum = ? where id = 1", new String[]{String.valueOf(nDataAll)});
        }

}



