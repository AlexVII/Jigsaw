package com.smu_bme.jigsaw;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private Date timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent intent = getIntent();
        final DbData dbData = (DbData) intent.getSerializableExtra("Timer");
        a = dbData.getDuration() * 60 * 1000;
        Log.d("DEBUGGING", "a = " + a);
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
//            Calendar calendar = Date(millisUntilFinished).;
//            calendar.setTimeZone();

            Log.d("DEBUGGING", format.format(new Date(millisUntilFinished).getTime()));
            textView.setText(format.format(new Date(millisUntilFinished).getTime()));
        }
        @Override
        public void onFinish() {
//            textView.setText("Get");
//            progressBar.setProgress(a);
//            Intent intent = new Intent(TimerActivity.this, MainActivity.class);
//            intent.putExtra("Action", "dialog_create");
//            startActivity(intent);
//            finish();
        }
    };
        timer.start();
    }
}
