package com.smu_bme.jigsaw;



import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
}

public class MydatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_DATA = "create table DATA ("
            + "id integer primary key autoincrement, "
            + "date text"
            + "duration integer"
            + "time text"
            + "name text"
            + "remark text";

    public static final String CREATE_SUM = "create table SUM("
            + "id integer primary key autoincrement,"
            +"date text"
            +"sum integer)";

    private Context mContext;

    public MydatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_DATA);
        db.execSQL(CREATE_SUM);
        db.execSQL("insert into SUM (date,sum) values(?,?)",new String[] {"all","0"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}


public class event {
    private MydatabaseHelper dbHelper;

    private String name;
    private String date;
    private int duration;
    private String time;
    private String remark;

    public void event(String name,String date,String time,int duration, String remark){
        this.name = name;
        this.duration = duration;
        this.date = date;
        this.time = time;
        this.remark = remark;
    }

    public void getDate(){

        return
    }

    public void addData(String name,String date,String time,int duration, String remark){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //开始组装数据
        values.put("name",name);
        values.put("date",date);
        values.put("time",time);
        values.put("duration",duration);
        values.put("remark",remark);
        db.insert("DATA",null,values);
        values.clear();
    }

    public void deleteData(String name,String date,String time,int duration, String remark){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Book","name=?",new String[]{name,date,time,String.valueOf(duration),remark});

    }
}