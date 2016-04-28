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
    private final String tableName = "DATA";
    private final String tableSum= "SUM";

private void initialAndCheck() {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Cursor cursor = db.query(tableSum,null,"id=?",new String[]{"1"},null,null,null,null);
//    Cursor cursor = db.rawQuery("select * from " + tableSum + " where id = 1");
    if (cursor.getCount() == 0) {

        ContentValues values = new ContentValues();
        values.put("date","all");
        values.put("sum",0);
        db.insert(tableSum,null,values);
        values.clear();
//        db.execSQL("insert into SUM (date, sum) values ('all', 0)");
    }

    cursor.close();
    cursor = db.query("NUM",null,"id=?",new String[]{"1"},null,null,null,null);
//    cursor = db.rawQuery("select * from NUM where id = 1");
    if (cursor.getCount() == 0) {

        ContentValues values = new ContentValues();
        values.put("num",0);
        db.insert("NUM",null,values);
        values.clear();

        cursor.close();

    }
}


    public DbHelper(Context context){
        this.context = context;
        dbHelper= new MyDatabaseHelper(context);
        initialAndCheck();
    }
///////////////////// Find //////////////////////////
    //To find the sum of duration on a day
    private int findSum(String date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Initialization
        int sum ;
        //get the sum of the very date
        Cursor cursorSum = db.query(tableSum,null,"date=?",new String[]{date},null,null,null,null);
//        Cursor cursorSum = db.rawQuery("select * from ? where ? = ?", new String[]{tableSum, "date", date});

        if(cursorSum.getCount()>0) {
            // if found, get sum
            cursorSum.moveToFirst();
            sum =  cursorSum.getInt(cursorSum.getColumnIndex("sum"));
        } else {sum = -1;}// if not find, then the sum of duration of the very date is not created yet

        cursorSum.close();
        return sum;
    }
//TODO
    private int findAll(){// find the sum of all date
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int sum=0;
        Cursor cursorSum = db.query(tableSum,null,"id=?",new String[]{"1"},null,null,null,null);
//        Cursor cursorSum = db.rawQuery("select * from ? where ? = ?", new String[]{tableSum, "id", "1"});
        if(cursorSum.moveToFirst()){
        sum = cursorSum.getInt(cursorSum.getColumnIndex("sum"));}
        cursorSum.close();
        return sum;
    }

    public ArrayList<DbData> findData(String mode, String item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<DbData> list = new ArrayList<>();
        Cursor cursor;
        if (mode.equals("id")){
            cursor = db.query(tableName,null,"id=?",new String[]{item},null,null,null,null);
        }
        else if(mode.equals("date")){
            cursor = db.query(tableName,null,"date=?",new String[]{item},null,null,null,null);
        }
        else if(mode.equals("name")){
            cursor = db.query(tableName,null,"name=?",new String[]{item},null,null,null,null);
        }
        else if(mode.equals("time")){
            cursor = db.query(tableName,null,"time=?",new String[]{item},null,null,null,null);
        }
        else {
            cursor = null;
        }


//           Cursor cursor = db.rawQuery("select * from ? where ? = ?", new String[]{tableName, mode, item});
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

        //TODO what if the list is null
            return list;
    }

    ///////////////////// Add//////////////////////////
    private void addSum(String date,int duration){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//                     insert
        ContentValues values = new ContentValues();
        values.put("date",date);
        values.put("sum",duration);
        db.insert(tableSum,null,values);
        values.clear();

        values.put("sum",findAll()+duration);
        db.update(tableSum,values,"id=?",new String[]{"1"});
        values.clear();

//        db.execSQL("insert into SUM (date,sum) values (?, ?)",new Object[]{date,duration});
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
        if(sumSameDate==-1) {//if the date is new
            // Add
            this.addSum(dbDate.getDate(),dbDate.getDuration());

//            db.execSQL("update SUM set sum = "+ String.valueOf(nSumAll) +"where id = 1", null);

        }
        else{//if the date already exists

            //update
            this.updateSum(dbDate.getDate(),dbDate.getDuration());
        }
    }

    ///////////////////// Delete/////////////////////////
    public void deleteData(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<DbData> DeleteDatas = this.findData("id", String.valueOf(id));
        if (!DeleteDatas.isEmpty()) {
            // decrease the sum
            DbData DeleteData = DeleteDatas.get(0);
            String DeleteDate = DeleteData.getDate();
            int DeleteDuration = -(DeleteData.getDuration());
            this.updateSum(DeleteDate, DeleteDuration);
            // delete the item in DATA
            db.delete(tableName, "id=?", new String[]{String.valueOf(id)});
//        db.execSQL("delete from DATA where id = ? ", new String[]{String.valueOf(id)});
        }
    }


    ///////////////////// Update//////////////////////////
    public Boolean updateData(DbData dbData){
        Boolean out;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (dbData.getId()==0)
        {
            out = false;
        }
        ArrayList<DbData> previousList = this.findData("id",String.valueOf( dbData.getId() ));
        if(!previousList.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put("date",dbData.getDate());
            values.put("duration",dbData.getDuration());
            values.put("time",dbData.getTime());
            values.put("name",dbData.getName());
            values.put("remark",dbData.getRemark());
            db.update("DATA",values,"id=?",new String[]{String.valueOf( dbData.getId() )});
            values.clear();
//            db.execSQL("update DATA set date = ?, duration = ?, time = ?, name = ?, remark = ? where id = ?",
//                    new String[]{dbData.getDate(),
//                            String.valueOf(dbData.getDuration()),
//                            dbData.getTime(),
//                            dbData.getName(),
//                            dbData.getRemark()
//                    });
            DbData previous = previousList.get(0);
            int deltaDuration = dbData.getDuration()-previous.getDuration();
            updateSum(dbData.getDate(), deltaDuration);
        }
        out=true;
        return out;
    }

    private void updateSum(String date,int deltaDuration){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int nDateSum = findSum(date)+deltaDuration;
        values.put("sum",String.valueOf(nDateSum));
        db.update(tableSum,values,"date=?",new String[]{date});
        values.clear();
//        db.execSQL("update SUM set sum = ? where date = ?", new String[]{String.valueOf(nDateSum),date});

        int nDataAll = findAll() + deltaDuration;
        values.put("sum",String.valueOf(nDataAll));
        db.update(tableSum,values,"id=?",new String[]{"1"});
        values.clear();
//        db.execSQL("update SUM set sum = ? where id = 1", new String[]{String.valueOf(nDataAll)});
        }


}



