package com.smu_bme.jigsaw;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smu_bme.jigsaw.WorkerThreads.ChartThread;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * Created by bme-lab2 on 5/1/16.
 */
public class LogUI extends View {

    private View view;
    private TextView textView;
    private ImageButton imageButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private mAdapter adapter;
    private DbHelper dbHelper ;
    private List<DbData> list;
    private int year;
    private int month;
    private int day;
    private Calendar calendar;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    private int Id;

    public LogUI(final Context context, LayoutInflater inflater, final ViewGroup container, final Calendar ShowedDate){
        super(context);
        this.calendar =ShowedDate;
//        Log.d("DEBUGGING","ShowedDate:" + calendar.get(Calendar.YEAR) +  calendar.get(Calendar.MONTH)+"" +calendar.get(Calendar.DAY_OF_MONTH));
        this.view = inflater.inflate(R.layout.layout_log, container, false);
        this.textView = (TextView) view.findViewById(R.id.date);
        this.imageButton = (ImageButton) view.findViewById(R.id.edit_date);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.setMax(14400);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.event_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        dbHelper = new DbHelper(context);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        calendar.set(Calendar.YEAR, y);
                        calendar.set(Calendar.MONTH, m);
                        calendar.set(Calendar.DAY_OF_MONTH, d);
                        year = y;
                        month = m;
                        day = d;
                        setView(context, calendar);
                        ChartThread chartThread = new ChartThread();
                        chartThread.refreshDate(calendar);
                    }
                }, year, month, day);
                Log.d("DEBUGGING",calendar.get(Calendar.YEAR) +  calendar.get(Calendar.MONTH)+"" +calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }

        });
        setView(context, ShowedDate);
    }

//    public void setView(int y, int m, int d, Context context){
//        Calendar CurrentDate =  Calendar.getInstance();
//        if (calendar.equals(CurrentDate)){
//            progressBar.setSecondaryProgress(CurrentDate.get(Calendar.HOUR) * 60 + CurrentDate.get(Calendar.MINUTE));
//        } else {
//            progressBar.setSecondaryProgress(14400);
//        }
//        progressBar.setProgress(dbHelper.querySum(format.format(calendar.getTime())) + 1);
//        list = dbHelper.queryData("date", format.format(calendar.getTime()));
//        adapter = new mAdapter(list, context);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//    }

    public void setView(final Context context, final Calendar calendar){
        Calendar CurrentDate =  Calendar.getInstance();
        textView.setText(format.format(calendar.getTime()));
        if (calendar.equals(CurrentDate)){
            progressBar.setSecondaryProgress(CurrentDate.get(Calendar.HOUR) * 60 + CurrentDate.get(Calendar.MINUTE));
        } else {
            progressBar.setSecondaryProgress(14400);
        }
        progressBar.setProgress(dbHelper.querySum(format.format(calendar.getTime())) + 1);
        list = dbHelper.queryData("date", format.format(calendar.getTime()));
        adapter = new mAdapter(list, context);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public View getView(Calendar calendar, Context context){
        setView(context, calendar);
        return view;
    }
}
