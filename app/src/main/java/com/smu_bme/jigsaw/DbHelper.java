package com.smu_bme.jigsaw;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gollyrui on 4/28/16.
 */
public class DbHelper {

    Context context;
    private MyDatabaseHelper dbHelper;
    private final String tableName = "DATA";
    private final String tableSum = "SUM";
    private SQLiteDatabase db;

    public DbHelper(Context context) {
        this.context = context;
        dbHelper = new MyDatabaseHelper(context);
        initialAndCheck();

    }

    private void initialAndCheck() {
        db = dbHelper.getWritableDatabase();
//          Cursor cursor = db.query(tableSum,null,"id=?",new String[]{"1"},null,null,null,null);
        Cursor cursor = db.rawQuery("select * from SUM where id = 1", new String[]{});
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("date", "all");
            values.put("sum", 0);
            db.insert(tableSum, null, values);
            values.clear();

//          db.execSQL("insert into SUM (date, sum) values ('all', 0)");
        }

        cursor = db.query("NUM", null, "id=?", new String[]{"1"}, null, null, null, null);
//          cursor = db.rawQuery("select * from NUM where id = 1",new String[]{});
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("num", 0);
            db.insert("NUM", null, values);
            values.clear();

            cursor.moveToLast();
            cursor.close();

        }
        db.close();
    }


//------------------------------------------------------------------------------------------------------------------------------------

///////////////////// Find //////////////////////////

    //    ------------------------------------------------------------------------------------------------------------------------------------
    //To find the sum of duration on a day
    public int querySum(String date) {
        db = dbHelper.getWritableDatabase();
        // Initialization
        int sum;
        //get the sum of the very date
//        Cursor cursorSum = db.query(tableSum,null,"date=?",new String[]{date},null,null,null,null);
        Cursor cursorSum = db.rawQuery("select * from SUM where date = ?", new String[]{date});


//        if(cursorSum!=null) {
        if (cursorSum.getCount() > 0) {
            // if found, get sum
//            Log.d("DEBUGGING","not null");
            cursorSum.moveToFirst();
            sum = cursorSum.getInt(cursorSum.getColumnIndex("sum"));
        } else {
            sum = -1;
        }// if not find, then the sum of duration of the very date is not created yet

        cursorSum.close();
        db.close();
        return sum;
    }


    public int queryAll() {// find the sum of all date
        db = dbHelper.getWritableDatabase();
        int sum = 0;
//        Cursor cursorSum = db.query(tableSum,null,"id=?",new String[]{"1"},null,null,null,null);
        Cursor cursorSum = db.rawQuery("select sum from SUM where id = 1", new String[]{});
        if (cursorSum.moveToFirst()) {
            sum = cursorSum.getInt(cursorSum.getColumnIndex("sum"));
        }
        cursorSum.close();
        db.close();
        return sum;
    }


    public int queryProgress() {
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select num from NUM where id =1", new String[]{});
        cursor.moveToFirst();
        int out = cursor.getInt(cursor.getColumnIndex("num"));
        cursor.moveToLast();
        cursor.close();
        db.close();
        return out;
    }

    public List<DbData> queryData(String mode, String item) {
//        Log.d("DEBUGGING_MARKER","init");
        db = dbHelper.getWritableDatabase();
        List<DbData> list = new ArrayList<>();
        Cursor cursor = null;
//        Log.d("DEBUGGING_MARKER","1");

        if (mode.equals("id")) {
            cursor = db.rawQuery("select * from DATA where id = ?", new String[]{item});
        } else if (mode.equals("name")) {
            cursor = db.rawQuery("select * from DATA where name = ?", new String[]{item});
        } else if (mode.equals("date")) {
            cursor = db.rawQuery("select * from DATA where date = ?", new String[]{item});
        }
//           Log.d("DEBUGGING_MARKER","count "+String.valueOf( cursor == null );
//            if(cursor!=null) {
        if (cursor.getCount() > 0) {
//            Log.d("DEBUGGING","exists");
            while (cursor.moveToNext()) {

//                    Log.d("DEBUGGING_MARKER","loop");

                String date = cursor.getString(cursor.getColumnIndex("date"));
                int sumAll = this.queryAll();
                int sumDate = this.querySum(date);
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
            cursor.moveToLast();
            cursor.close();
        }//else return null

        //TODO what if the list is null

        db.close();
        return list;
    }

    public List<DbData> queryData(String mode, String date, String name) {
//        Log.d("DEBUGGING_MARKER","init");
        db = dbHelper.getWritableDatabase();
        List<DbData> list = new ArrayList<>();
        Cursor cursor = null;
//        Log.d("DEBUGGING_MARKER","1");

        if (mode.equals("D&N")) {
            cursor = db.rawQuery("select * from DATA where date = ? & name =?", new String[]{date, name});
        }
//           Log.d("DEBUGGING_MARKER","count "+String.valueOf( cursor.getCount()) );
//        if(cursor!=null) {
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
//                    Log.d("DEBUGGING_MARKER","loop");

                int sumAll = this.queryAll();
                int sumDate = this.querySum(date);
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
        cursor.moveToLast();
        cursor.close();

        db.close();
        return list;
    }

    ///////////////////// Add//////////////////////////
    private void addSum(String date, int duration) {
        db = dbHelper.getWritableDatabase();
/////////////////                insert        ///////////////////////////////
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("sum", duration);
        db.insert(tableSum, null, values);
        values.clear();

        values.put("sum", queryAll() + duration);
        try {
            db.getVersion();
        } catch (Exception IllegalStateException) {
            db = dbHelper.getWritableDatabase();
        } finally {
            db.update(tableSum, values, "id=?", new String[]{"1"});
        }
//        db.getVersion();
//        db.update(tableSum,values,"id=?",new String[]{"1"});
        values.clear();
        db.close();
//
//
//        db.execSQL("insert into ? (date,sum) values (?, ?)",new String[]{tableSum,date,String.valueOf(duration)});
//
//        db.execSQL("update ? set sum = ? where id = 1",new String[]{tableSum,String.valueOf( queryAll()+duration )});
    }

    public int addData(DbData dbData) {
//        Log.d("DEBUGGING","Init");
        int out;
        db = dbHelper.getWritableDatabase();
        if (!dbData.validation()) {
//            Log.d("DEBUGGING","Not Valid");
            out = -1;//"Input is not valid. Any of date, time or duration is empty."
        } else {
            //check whether already exists
            Cursor cursor = db.rawQuery("select id from DATA where date = ? and time = ?", new String[]{dbData.getDate(), dbData.getTime()});
            if (cursor.getCount() > 0) {
                out = -2;//"Data with same date and time already exists"
            } else {
                ContentValues values = new ContentValues();
                //开始组装数据
                values.put("name", dbData.getName());
                values.put("date", dbData.getDate());
                values.put("time", dbData.getTime());
                values.put("duration", dbData.getDuration());
                values.put("remark", dbData.getRemark());
                db.insert("DATA", null, values);
                values.clear();

//                db.execSQL("insert into DATA (name, date, time, duration, remark) values (?, ?, ?, ?, ?)",
//                        new String[]{
//                                dbDate.getName(),
//                                dbDate.getDate(),
//                                dbDate.getTime(),
//                                String.valueOf( dbDate.getDuration()),
//                                dbDate.getRemark()
//                        });

                // SUM
                int sumSameDate = this.querySum(dbData.getDate());
                if (sumSameDate == -1) {//if the date is new

                    // Add
                    this.addSum(dbData.getDate(), dbData.getDuration());

                } else {//if the date already exists

                    //update
                    this.updateSum(dbData.getDate(), dbData.getDuration());

                }
                out = 0;
            }
        }
        db.close();

        return out;
    }

    ///////////////////// Delete/////////////////////////
    public void deleteData(int id) {
        db = dbHelper.getWritableDatabase();
        List<DbData> DeleteDatas = this.queryData("id", String.valueOf(id));
        if (!DeleteDatas.isEmpty()) {
            // decrease the sum
            DbData DeleteData = DeleteDatas.get(0);
            String DeleteDate = DeleteData.getDate();
            int DeleteDuration = -(DeleteData.getDuration());
            this.updateSum(DeleteDate, DeleteDuration);
            // delete the item in DATA
            try {
                db.getVersion();
            } catch (Exception IllegalStateException) {
                db = dbHelper.getWritableDatabase();
            } finally {
                db.delete(tableName, "id=?", new String[]{String.valueOf(id)});
            }
//        db.execSQL("delete from DATA where id = ? ", new String[]{String.valueOf(id)});
        }

        db.close();
    }


    ///////////////////// Update//////////////////////////
    public Boolean updateData(DbData dbData) {
        Boolean out;
        if (dbData.getId() == 0) out = false;// Id not Found
        else {
            db = dbHelper.getWritableDatabase();
            List<DbData> previousList = this.queryData("id", String.valueOf(dbData.getId()));
            if (previousList.isEmpty()) {
                out = false;
            } else {
                ContentValues values = new ContentValues();
                values.put("date", dbData.getDate());
                values.put("duration", dbData.getDuration());
                values.put("time", dbData.getTime());
                values.put("name", dbData.getName());
                values.put("remark", dbData.getRemark());
                try {
                    db.getVersion();
                } catch (Exception IllegalStateException) {
                    db = dbHelper.getWritableDatabase();
                } finally {
                    db.update("DATA", values, "id=?", new String[]{String.valueOf(dbData.getId())});
                }
                values.clear();
                DbData previous = previousList.get(0);
                int deltaDuration = dbData.getDuration() - previous.getDuration();
//                db.execSQL("update DATA set date = ?, duration = ?, time = ?, name = ?, remark = ? where id = ?",
//                        new String[]{dbData.getDate(),
//                                String.valueOf(dbData.getDuration()),
//                                dbData.getTime(),
//                                dbData.getName(),
//                                dbData.getRemark(),
//                                String.valueOf(dbData.getId())
//                        });
                updateSum(dbData.getDate(), deltaDuration);
                out = true;//fine
            }

        }
        db.close();
        return out;
    }

    public void changeTheme(int id){
        db = dbHelper.getWritableDatabase();
        int themeId = id;
        ContentValues values = new ContentValues();
        values.put("num", String.valueOf(themeId));
        try {
            db.getVersion();
        } catch (Exception IllegalStateException) {
            db = dbHelper.getWritableDatabase();
        } finally {
//           TODO: STALL EDITING...
            db.update("NUM", values, "id=?", new String[]{"1"});
        }
        values.clear();

        db.close();
    }

    public void updateProgress() {
        db = dbHelper.getWritableDatabase();
        int nProgress = this.queryProgress() + 1;
        ContentValues values = new ContentValues();
        if (nProgress >= 45){
            nProgress =0;
        }
        values.put("num", String.valueOf(nProgress));
        try {
            db.getVersion();
        } catch (Exception IllegalStateException) {
            db = dbHelper.getWritableDatabase();
        } finally {
            db.update("NUM", values, "id=?", new String[]{"1"});
        }
        values.clear();

        db.close();
//        db.execSQL("update NUM set num = ? where id = 1",new String[]{String.valueOf(nProgress)});
    }

    private void updateSum(String date, int deltaDuration) {
        ContentValues values = new ContentValues();
        db = dbHelper.getWritableDatabase();

        int nDateSum = querySum(date) + deltaDuration;
        values.put("sum", String.valueOf(nDateSum));

        try {
            db.getVersion();
        } catch (Exception IllegalStateException) {
            db = dbHelper.getWritableDatabase();
        } finally {
            db.update(tableSum, values, "date=?", new String[]{date});
        }

        values.clear();
//        db.execSQL("update SUM set sum = ? where date = ?", new String[]{String.valueOf(nDateSum),date});

        int nDataAll = queryAll() + deltaDuration;
        values.put("sum", String.valueOf(nDataAll));
        try {
            db.getVersion();
        } catch (Exception IllegalStateException) {
            db = dbHelper.getWritableDatabase();
        } finally {
            db.update(tableSum, values, "id=?", new String[]{"1"});
        }
        values.clear();

        db.close();
//        db.execSQL("update SUM set sum = ? where id = 1", new String[]{String.valueOf(nDataAll)});
    }


}



