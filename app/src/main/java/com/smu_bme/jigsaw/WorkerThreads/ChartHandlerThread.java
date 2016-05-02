package com.smu_bme.jigsaw.WorkerThreads;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
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
class ChartHandlerThread extends HandlerThread implements Handler.Callback {
    //        LayoutInflater inflater;
//        ViewGroup container;
//        Context context;

    ChartView chartView;

    public ChartHandlerThread(String name) {
        super(name);

    }

    @Override
    public boolean handleMessage(Message msg) {
        View view = chartView.getView((Calendar) msg.obj);
        Log.d("DEBUGGING", "handleMessage in Thread -- ChartView in WorkerThreads exists: " + String.valueOf(chartView != null));
        synchronized (view) {
            view.notifyAll();
            view.postInvalidate();
        }
//        Log.d("DEBUGGING", "in thread:");
        return true;
    }
}


