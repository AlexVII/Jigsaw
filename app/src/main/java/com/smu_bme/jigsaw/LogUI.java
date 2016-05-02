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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private Calendar calendar;
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
//        String CurrentDate  = new SimpleDateFormat("yyyy-MM-dd").format(CurrentCalendar.getTime());
        Calendar CurrentDate = MainActivity.PlaceholderFragment.CurrentCalendar;
        String Date = format.format(calendar.getTime());
        textView.setText(Date);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                    }
                };
                new DatePickerDialog(context, dateListener,
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        if (calendar.equals(CurrentDate)){
            progressBar.setSecondaryProgress(CurrentDate.get(Calendar.HOUR) * 60 + CurrentDate.get(Calendar.MINUTE));
        } else {
            progressBar.setSecondaryProgress(14400);
        }
        progressBar.setProgress(dbHelper.querySum(Date) + 1);
        list = dbHelper.queryData("date", "1970-1-1");
        if (list != null){
//            Log.d("DEBUGGING", "list is null");
        } else {
//            Log.d("DEBUGGING", "list is not null");
        }
        adapter = new mAdapter(list, context);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
