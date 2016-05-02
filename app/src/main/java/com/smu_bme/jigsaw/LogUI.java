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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public LogUI(Context context, LayoutInflater inflater, final ViewGroup container){
        super(context);
        this.view = inflater.inflate(R.layout.layout_log, container, false);
        this.textView = (TextView) view.findViewById(R.id.date);
        this.imageButton = (ImageButton) view.findViewById(R.id.edit_date);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progress);
        progressBar.setMax(14400);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.event_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        dbHelper = new DbHelper(context);
    }

    public View getView(final Context context, final Calendar calendar){

        Calendar CurrentDate = MainActivity.PlaceholderFragment.CurrentCalendar;

       final Date date = new Date();
        calendar.setTime(date);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day  =calendar.get(Calendar.DAY_OF_MONTH);
        textView.setText(format.format(calendar.getTime()));
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        calendar.set(Calendar.YEAR, y);
                        calendar.set(Calendar.MONTH, m);
                        calendar.set(Calendar.DAY_OF_MONTH, d);
                        textView.setText(format.format(calendar.getTime()));
                    }
                }, year, month, day);
                dialog.show();
            }
        });
        if (calendar.equals(CurrentDate)){
            progressBar.setSecondaryProgress(CurrentDate.get(Calendar.HOUR) * 60 + CurrentDate.get(Calendar.MINUTE));
        } else {
            progressBar.setSecondaryProgress(14400);
        }
        progressBar.setProgress(dbHelper.querySum(format.format(calendar.getTime())) + 1);
        list = dbHelper.queryData("date", format.format(calendar.getTime()));
        adapter = new mAdapter(list, context);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
