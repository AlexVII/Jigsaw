package com.smu_bme.jigsaw.WorkerThreads;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

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


    public WorkerThreads(final LayoutInflater inflater, final ViewGroup container, final Context context) {
        uiHandler = new Handler(Looper.getMainLooper());

        chartHandlerThread = new ChartHandlerThread("myHandler",inflater,container,context);
        //First run worker thread
//        ChartHandlerThread chartHandlerThread = new ChartHandlerThread(inflater, container, context);
        chartHandlerThread.start();
        chartHandler = new Handler(chartHandlerThread.getLooper(),chartHandlerThread);
        Log.d("DEBUGGING", "Constructor: Is chartHandler in WorkerThreads null: "+String.valueOf(chartHandler == null));
    }

    public void refreshDate(Calendar ShowedCalendar) {
        Log.d("DEBUGGING", "refreshDate :Is chartHandler in WorkerThreads null: "+String.valueOf(chartHandler == null));
        Log.d("DEBUGGING", "refreshDate:" + ShowedCalendar.toString());
        Message m = new Message();
        m.obj = ShowedCalendar;
//        Log.d("DEBUGGING", String.valueOf(chartHandler == null));
        chartHandler.sendMessage(m);
    }

    ;

//    public void WorkerThread_DB_Add() {
//        Thread t = new Thread(new DatabaseThread());
//        t.start();
//    }




}





