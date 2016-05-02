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

    public ChartHandlerThread(String name, final LayoutInflater inflater, final ViewGroup container, final Context context) {
        super(name);
        chartView = new ChartView(inflater, container, context, Calendar.getInstance());
    }

    @Override
    public boolean handleMessage(Message msg) {
        View view = chartView.getView((Calendar) msg.obj);
        synchronized (view) {
            view.notifyAll();
            view.postInvalidate();
        }
        Log.d("DEBUGGING", "in thread:");
        return true;
    }
}

//    public ChartHandlerThread(final LayoutInflater inflater, final ViewGroup container, final Context context) {
//            this.inflater = inflater;
//            this.container = container;
//            this.context = context;
//        chartView = new ChartView(inflater, container, context, Calendar.getInstance());
//    }


//    @Override
//    public void run() {
//        System.out.println("线程开始运行");
//
//        Looper.prepare();// 在线程中必须建立一个自己的looper，不能用ui线程中的
//
//        chartHandler = new Handler(Looper.myLooper()) {// 在新线程中创建Handler时必须创建Looper
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                chartView.getView((Calendar) msg.obj);
//            }
//        };
//        Log.d("DEBUGGING", "Is chartHandler in WorkerThreads null: "+String.valueOf(chartHandler == null));
//        // looper不断的从消息队列中取出消息，如果没有就阻塞
//        Looper.loop();
//// 新思路，通过这套机制传递handler
////        Handler uiHandler;
////        uiHandler = new Handler((Looper.getMainLooper()));
////        Message MessageToMain = new Message();
////        MessageToMain.obj = chartHandler;
////        uiHandler.sendMessage(MessageToMain);
//    }


