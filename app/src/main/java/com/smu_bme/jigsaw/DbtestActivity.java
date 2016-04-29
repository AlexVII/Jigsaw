package com.smu_bme.jigsaw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class DbtestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        Button buttonAdd = (Button) findViewById(R.id.dbadd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbData dbData = new DbData("2016-3-4", 90, "17:30", "高数");
                DbHelper dbHelper = new DbHelper(DbtestActivity.this);
                dbHelper.addData(dbData);
            }
        });


        Button buttonDel = (Button) findViewById(R.id.dbdelete);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbData dbData = new DbData("2016-3-4", 90, "17:30", "高数");
                DbHelper dbHelper = new DbHelper(DbtestActivity.this);
                dbHelper.queryData("id","1");
                dbHelper.deleteData(1);
            }
        });


        Button buttonUpdate = (Button) findViewById(R.id.dbupdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbData dbData = new DbData(1,"2016-3-4", 80, "17:30", "高2333数",null);
                DbHelper dbHelper = new DbHelper(DbtestActivity.this);
                dbHelper.updateData(dbData);
            }
        });

        Button buttonQuery = (Button) findViewById(R.id.dbquery);
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbHelper dbHelper = new DbHelper(DbtestActivity.this);
                List<DbData> dbDataList = dbHelper.queryData("id","1");
                DbData dbData  = dbDataList.get(0);


                Toast.makeText(DbtestActivity.this, dbData.getDate(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
