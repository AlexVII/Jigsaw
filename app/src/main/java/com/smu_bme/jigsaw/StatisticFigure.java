package com.smu_bme.jigsaw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;

public class StatisticFigure extends AppCompatActivity {
////////////////////////////////////////////////

    //BarChart
    BarChart week = (BarChart) findViewById(R.id.weekChart);
//    BarChart        new BarChart(StatisticFigure.this);



////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_figure);
    }
}

