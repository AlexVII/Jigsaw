package com.smu_bme.jigsaw;



import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class JigsawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jisaw);
        Button button = (Button) findViewById(R.id.get_started);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JigsawActivity.this, MainActivity.class);
                intent.putExtra("Get Started", 2);
                startActivity(intent);
            }
        });
    }

public class DbData {
    int id;
    String date;
    String duration;
    String time;
    String name;
    String remaek;
}
    public class MydatabaseHelper extends SQLiteOpenHelper {
        private String correntName;
        private Context context;
        public static final String CREATE_DATA = "create table DATA ("
                + "id integer primary key autoincrement, "
                + "date text"
                + "duration integer"
                + "time text"
                + "name text"
                + "remark text";

//        public static final String CREATE_SUM = "create table SUM("
//                + "id integer primary key autoincrement,"
//                + "date text"
//                + "sum integer)";



        public MydatabaseHelper(Context context,String name, SQLiteDatabase.CursorFactory factory,int version){
            super(context,name,factory,version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DATA);
//            db.execSQL(CREATE_SUM);
//            db.execSQL("insert into SUM (date,sum) values(?,?)", new String[]{"all", "0"});
        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }


        public List<DbData> findData(String dataBaseName,String mode,String item) {
            SQLiteDatabase db = this.getWritableDatabase();
            List<DbData> list = new ArrayList<DbData>();

            Cursor cursor = db.rawQuery("select * from DATA where ?=?",new String[]{ mode ,item});
            while (cursor.moveToFirst()) {
                DbData dbData = new DbData();
                dbData.id = cursor.getInt(cursor.getColumnIndex("id"));
                dbData.date = cursor.getString(cursor.getColumnIndex("data"));
                dbData.duration = cursor.getString(cursor.getColumnIndex("duration"));
                dbData.time = cursor.getString(cursor.getColumnIndex("time"));
                dbData.name = cursor.getString(cursor.getColumnIndex("name"));
                dbData.remaek = cursor.getString((cursor.getColumnIndex("remark")));
                list.add(dbData);
            }
            cursor.close();
            db.close();
            return list;
        }

        public void addData(String name, String date, String time, String duration, String remark) {

            SQLiteDatabase db = this.getWritableDatabase();
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
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from DATA where id=? ", new String[]{id});
        }
    }
}






































































































