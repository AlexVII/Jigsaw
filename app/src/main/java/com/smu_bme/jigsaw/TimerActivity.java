package com.smu_bme.jigsaw;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.crypto.interfaces.PBEKey;

public class TimerActivity extends AppCompatActivity implements Serializable {

    private TextView textView;
    private ProgressBar progressBar;
    private TextView name;
    private TextView remark;
    private TextView date;
    private TextView time;
    private int a;
    private SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
    private int Id;
    private int duration;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent intent = getIntent();
        final DbData dbData = (DbData) intent.getSerializableExtra("Timer");
        Id = dbData.getId();
        a = dbData.getDuration() * 60 * 1000;
        Log.d("DEBUGGING", "ID = " + Id);
        textView = (TextView) findViewById(R.id.timer);
        progressBar = (ProgressBar) findViewById(R.id.timer_progress_bar);
        progressBar.setMax(a * 60);
        name = (TextView) findViewById(R.id.timer_name);
        name.setText(dbData.getName());
        date = (TextView) findViewById(R.id.timer_date);
        date.setText(dbData.getDate());
        time = (TextView) findViewById(R.id.timer_time);
        time.setText(dbData.getTime());
        if (dbData.getRemark() != null){
            remark = (TextView) findViewById(R.id.timer_remark);
            remark.setText(dbData.getRemark());
        }
        final CountDownTimer timer = new CountDownTimer(a, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            progressBar.setProgress(a -  (int) millisUntilFinished);
            duration = (int) millisUntilFinished;
            Log.d("DEBUGGING", format.format(new Date(millisUntilFinished).getTime()));
            textView.setText(format.format(new Date(millisUntilFinished).getTime()));
        }
        @Override
        public void onFinish() {
            textView.setText("Get");
            progressBar.setProgress(a);
            Intent intent = new Intent(TimerActivity.this, MainActivity.class);
            intent.putExtra("Action", "dialog_create");
            startActivity(intent);
            finish();
        }
    };
        timer.start();
        button = (Button) findViewById(R.id.exit_timer);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(TimerActivity.this).setTitle(getString(R.string.create)).setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                update();
                            }
                        }).setNegativeButton(getString(R.string.cancel), null).show();
            }
        });
    }

    public void update(){
        DbHelper dbHelper = new DbHelper(TimerActivity.this);
        DbData dbData = dbHelper.queryData("id", String.valueOf(Id)).get(0);
        dbData.setDuration(duration);
        dbHelper.updateData(dbData);
    }


    @Override
    public void onBackPressed() {
        button.callOnClick();
    }

    @Override
    protected void onPause() {
        super.onPause();
        update();
        Toast.makeText(TimerActivity.this, "Not That Way...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        update();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        update();
    }
}
