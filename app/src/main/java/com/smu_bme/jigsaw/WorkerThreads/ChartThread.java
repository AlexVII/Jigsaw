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
public class ChartThread {
    static Handler uiHandler;
    static Handler chartHandler;
    static ChartHandlerThread chartHandlerThread;// an extension of chartThread
    static private View view;


    public void init (final LayoutInflater inflater, final ViewGroup container, final Context context) {
        Log.d("DEBUGGING", Thread.currentThread().getName() + "-Constructor- Start!");
        chartHandlerThread = new ChartHandlerThread("Chart");
        chartHandlerThread.chartView = new ChartView(inflater, container, context, Calendar.getInstance());
        view = chartHandlerThread.chartView.getView(Calendar.getInstance());

        //First run worker thread

//        ChartHandlerThreadBak chartHandlerThreadBak = new ChartHandlerThreadBak(inflater, container, context);
//        synchronized (chartHandlerThread) {
        ////////////////////////////////////
        chartHandlerThread.start();
        chartHandler = new Handler(chartHandlerThread.getLooper(), chartHandlerThread);
        chartHandler.post(new Runnable() {
            /*---------------------------------------------------------------

                                        Chart Thread

            ---------------------------------------------------------------*/
            @Override
            public void run() {
                uiHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {

                           /*------------------------------------------------------------

                                          Message from Chart Thread to Main Thread

                           ------------------------------------------------------------*/
                        super.handleMessage(msg);
                        Log.d("DEBUGGING", Thread.currentThread().getName() + "-handleMessage-Start");
//                            synchronized (chartHandlerThread) {
                        if (msg.what == 0) {
                            Calendar ShowedCalendar = (Calendar)msg.obj;
                            view = chartHandlerThread.chartView.getView(ShowedCalendar);
                        }
//                        else if (msg.what == 1) {
//
//                            Log.d("DEBUGGING", Thread.currentThread().getName() + "-handleMessage-valid message for view");
//                            view = (View) msg.obj;
//                            Log.d("DEBUGGING", Thread.currentThread().getName() + "-handleMessage-view exists: " + String.valueOf(view != null));
//                        }
//                                try {
                        Log.d("DEBUGGING", Thread.currentThread().getName() + "-handleMessage- Ended ");
//                                notify();
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                    }
//                        }
                };
//                Log.d("DEBUGGING", "chartHandlerThread == Thread.currentThread():"+String.valueOf(chartHandlerThread == Thread.currentThread()));
//                synchronized (chartHandlerThread) {
//                    chartHandlerThread.chartView = new ChartView(inflater, container, context, Calendar.getInstance());
//
////                    Message initMessage = new Message();
////                    initMessage.what = 1;
////                    initMessage.obj = chartHandlerThread.chartView;
////                    uiHandler.sendMessage(initMessage);
//
//                    notify();
//                }
                Log.d("DEBUGGING", Thread.currentThread().getName() + "-chartView Exists- " + String.valueOf(chartHandlerThread.chartView != null));
//                    Log.d("DEBUGGING", Thread.currentThread().getName() + "-init- chartView exists: " + String.valueOf(chartHandlerThread.chartView != null));
//                synchronized (chartHandlerThread){
//                    Log.d("DEBUGGING", "WorkerThread -synchronized- ");
//                    notify();
//                }

            }
        });//Runnable ended
            /*------------------------------------------------------------


                                    Back to Main Thread

            ------------------------------------------------------------ */
//        synchronized (chartHandlerThread) {
//            try {
//                Log.d("DEBUGGING", Thread.currentThread().getName() + "-Constructor- waiting");
//                chartHandlerThread.notify();
////                wait();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        ///////////////////////////////////////
//        }
        Log.d("DEBUGGING", Thread.currentThread().getName() + "-Constructor- Finished");
    }


    public void refreshDate(Calendar ShowedCalendar) {
//        Log.d("DEBUGGING", Thread.currentThread().getName() + "-refreshDate-chartView exists: " + String.valueOf(chartHandlerThread.chartView != null));
//        Log.d("DEBUGGING", "UIThread refreshDate:" + ShowedCalendar.toString());
        Message m = new Message();
        m.obj = ShowedCalendar;
        m.what = 0;
        uiHandler.sendMessage(m);
        Log.d("DEBUGGING", Thread.currentThread().getName() + "-chartHandler- exists:" + String.valueOf(chartHandler != null));
    }


    public View getView() {
        return view;
    }

    private class ChartHandlerThread extends HandlerThread implements Handler.Callback {

        ChartView chartView;

        public ChartHandlerThread(String name) {
            super(name);
        }

        @Override
        public boolean handleMessage(Message msg) {
            View view = chartView.getView((Calendar) msg.obj);
            Log.d("DEBUGGING", Thread.currentThread().getName() + "-handleMessage- chartView exists: " + String.valueOf(chartView != null));
            synchronized (view) {
                view.notifyAll();
                view.postInvalidate();
            }
            synchronized (Thread.currentThread()) {
                notify();
            }
            Log.d("DEBUGGING", Thread.currentThread().getName() + "-handleMessage- End");
            return true;
        }
    }

}
