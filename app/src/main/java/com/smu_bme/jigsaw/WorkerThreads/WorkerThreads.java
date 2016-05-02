package com.smu_bme.jigsaw.WorkerThreads;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smu_bme.jigsaw.ChartView;

import java.util.Calendar;


/**
 * Created by bme-lab1 on 5/2/16.
 */
public class WorkerThreads {

    Handler uiHandler;
    //    Handler dbHandler;
//    Handler logHandler;
    Handler chartHandler;
    ChartHandlerThread chartHandlerThread;
    private View view;


    public WorkerThreads(final LayoutInflater inflater, final ViewGroup container, final Context context) {
        uiHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.arg1==1){
                    view = (View) msg.obj;
                }
            }
        };


        chartHandlerThread = new ChartHandlerThread("myHandler");
        //First run worker thread
//        ChartHandlerThread chartHandlerThread = new ChartHandlerThread(inflater, container, context);
        chartHandlerThread.start();
        chartHandler = new Handler(chartHandlerThread.getLooper(), chartHandlerThread);
        chartHandler.post(new Runnable(){
            @Override
            public void run() {
                chartHandlerThread.chartView = new ChartView(inflater, container, context, Calendar.getInstance());
            }
        });
//        Log.d("DEBUGGING", "Constructor: Is chartHandler in WorkerThreads null: " + String.valueOf(chartHandler == null));
    }

    public void refreshDate(Calendar ShowedCalendar) {
        Log.d("DEBUGGING", "refreshDate--ChartView in WorkerThreads exists: " + String.valueOf(chartHandlerThread.chartView != null));
//        Log.d("DEBUGGING", "refreshDate:" + ShowedCalendar.toString());
        Message m = new Message();
        m.obj = ShowedCalendar;
//        Log.d("DEBUGGING", String.valueOf(chartHandler == null));
        chartHandler.sendMessage(m);
    }


    public View getView() {
        return view;
    }
}





