package com.smu_bme.jigsaw;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

/**
 * Created by bme-lab1 on 5/2/16.
 */
public class RefreshTest extends Activity{
    public View onCreateView(
            Bundle savedInstanceState) {


            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                WorkerThreads workerThreads = new WorkerThreads(inflater,container,getContext());
//                workerThreads.refreshDate(ShowedCalendar);
//                view = workerThreads.getView();
//                return initChart(inflater, container);
            ChartView chart = new ChartView(null, null, getApplicationContext(), Calendar.getInstance());
//                ShowedCalendar.setTime(Date.valueOf("1970-1-1"));
        return     chart.getView(Calendar.getInstance());

    }

}
