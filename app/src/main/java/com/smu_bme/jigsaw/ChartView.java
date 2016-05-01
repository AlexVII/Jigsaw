package com.smu_bme.jigsaw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Map;

/**
 * Created by gollyrui on 4/30/16.
 */
public class ChartView extends View{
        private LayoutInflater inflater;
        private ViewGroup container;
        private Context context;
        private View view;
        private SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        private DbHelper dbHelper;
        private BarChart barChart ;
        private PieChart pieChart;
//        private PieDataSet pieDataSet;
        private Calendar ShowedDate;
        private  ArrayList<String> xVals = new ArrayList<>();



        public ChartView(LayoutInflater inflater, ViewGroup container, Context context, final Calendar ShowedDate) {
            super(context);
            {
                xVals.add(context.getString(R.string.sunday));
                xVals.add(context.getString(R.string.monday));
                xVals.add(context.getString(R.string.tuesday));
                xVals.add(context.getString(R.string.wednesday));
                xVals.add(context.getString(R.string.thursday));
                xVals.add(context.getString(R.string.friday));
                xVals.add(context.getString(R.string.saturday));
            }
            this.inflater = inflater;
            this.container = container;
            this.context = context;
            this.ShowedDate = ShowedDate;
            dbHelper = new DbHelper(context);
            view = inflater.inflate(R.layout.layout_data, container, false);
            {
                //Initialize Chart reference
                barChart = (BarChart) view.findViewById(R.id.bar_chart);
                pieChart = (PieChart) view.findViewById(R.id.pie_chart);
            }
            //call pie chart according to selected value
            barChart.setOnChartValueSelectedListener(
                    new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry entry, int i, Highlight highlight) {
//                            Log.d("DEBUGGING","XIndex:"+String.valueOf(1+entry.getXIndex()));
                            setPie(ShowedDate,entry.getXIndex()+1);
//                            Calendar.MONDAY
                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    }
            );

            {
                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                xAxis.setAxisMinValue(-0.5f);
                YAxis yAxisRight = barChart.getAxisRight();
                yAxisRight.setEnabled(false);
                YAxis yAxisLeft = barChart.getAxisLeft();
                yAxisLeft.setAxisMinValue(-1f);
                yAxisLeft.setSpaceTop(20f);
//                yAxisLeft.setAxisMaxValue(60f);
//                yAxisLeft.setSpaceBottom(10f);
                yAxisLeft.setLabelCount(6, false);

                barChart.setScaleEnabled(false);
                barChart.setDrawValueAboveBar(true);
//          barChart.setDrawHighlightArrow(true);
                barChart.setHighlightPerTapEnabled(true);
                {
                    Legend l = barChart.getLegend();
                    l.setEnabled(false);
                }
                barChart.setDescriptionTextSize(10);
                barChart.animateY(1500);
                barChart.animateX(1500);
                barChart.setAlpha(0.9f);
                barChart.setNoDataText(context.getString(R.string.nofoundonweek));
                barChart.setNoDataTextDescription(context.getString(R.string.gettostart));
                barChart.setDescription(null);  // set the description
//              barChart.highlightValues(Highlight[] highs);
            }


            {
                pieChart.setNoDataText(context.getString(R.string.nofoundonday));
                pieChart.setNoDataTextDescription(context.getString(R.string.gettostart));
                pieChart.setDescription(context.getString(R.string.week));
                pieChart.setDescriptionTextSize(15f);
                pieChart.animateY(1500, Easing.EasingOption.EaseInOutQuad);
                pieChart.setUsePercentValues(true);
                pieChart.setExtraOffsets(5, 5, 5, 5);
                pieChart.setDragDecelerationFrictionCoef(0.95f);
                pieChart.setDrawSliceText(false);
                pieChart.setHoleRadius(35);
                pieChart.setTransparentCircleRadius(45f);
                pieChart.setTransparentCircleAlpha(70);
//            pieChart.setCenterText("每日学习");
                pieChart.setAlpha(0.9f);
//                pieChart.setHighlightPerTapEnabled(true);

                // pieChart.setCenterText(false);
                pieChart.setRotationAngle(0);
                // enable rotation of the chart by touch
                pieChart.setRotationEnabled(true);
//
//             pieChart.setBackgroundColor(Color.LTGRAY);
//            pieChart.setBackgroundTintMode();
                pieChart.setHighlightPerTapEnabled(false);

                // add a selection listener
                // mPieChart.setOnChartValueSelectedListener(this);

                Legend l = pieChart.getLegend();
                l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
                l.setTextSize(15);
                l.setFormSize(15f);
                l.setXEntrySpace(7f);
                l.setYEntrySpace(5f);
                l.setYOffset(0f);
            }
            setBar(ShowedDate);


    }
    private void setBar(final Calendar ShowedDate){
        ArrayList<BarEntry> barEntry = new ArrayList<>();
        BarData barData = null;
        BarDataSet barDataSet;
        this.ShowedDate = ShowedDate;
        for (int i = 1; i < 8; i++) {
            ShowedDate.set(Calendar.DAY_OF_WEEK, i);
            String date = f.format(ShowedDate.getTime());
//            Log.d("DEBUGGING",date);
            float sum = (float) dbHelper.querySum(date);
//            Log.d("DEBUGGING","SUM:"+String.valueOf(sum));
            if(sum<0)sum=0;
            BarEntry Chart1Element = new BarEntry(sum, i - 1);
            barEntry.add(Chart1Element);
        }
        barDataSet = new BarDataSet(barEntry, context.getString(R.string.week));// 'barEntry" is address
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setValueTextSize(12f);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setBarSpacePercent(40f);
        barDataSet.setHighLightAlpha(20);
        barData = new BarData(xVals,barDataSet);
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);//JOYFUL_COLORS)VORDIPLOM_COLORS)
        barChart.setData(barData);
        barChart.notifyDataSetChanged();
        barChart.postInvalidate();
        setPie(ShowedDate,-1);
    }
    private void setPie(final Calendar ShowedDate,final int dayOfWeek){
        if(dayOfWeek>0&&dayOfWeek<8) {
            ShowedDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        }

        ArrayList<String> pieLabels = new ArrayList<>();
        ArrayList<Entry> pieEntry = new ArrayList<>();
        String date = f.format(ShowedDate.getTime());
//        Log.d("DEBUGGING",date);
        List<DbData> dbDatas = dbHelper.queryData("date",date);
        DbData dbData;
        Map<String,Integer> activities = new HashMap<>();
        for(int i=0;i<dbDatas.size();i++){
            dbData = dbDatas.get(i);
            try {
                activities.put(  dbData.getName() ,activities.get(dbData.getName())+dbData.getDuration()   );
            } catch (Exception e){
                activities.put(dbData.getName(), dbData.getDuration());
            }

        }
        if(activities.isEmpty()){
        }else {
            int i = 0;
            for (Map.Entry<String, Integer> entry : activities.entrySet()) {
//              pieLabels.add("Math");
//              pieEntry.add(new Entry(4f, 0));
                pieLabels.add(entry.getKey());
//                Log.d("DEBUGGING",entry.getKey());
                pieEntry.add(new Entry((float) entry.getValue(), i));
                i++;
            }


            PieDataSet pieDataSet = new PieDataSet(pieEntry, context.getString(R.string.piechart));
            pieDataSet.setValueTextSize(15);
            pieDataSet.setSliceSpace(5);
            pieDataSet.setHighlightEnabled(true);
            pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            PieData pieData = new PieData(pieLabels, pieDataSet);
            pieChart.setData(pieData);
            pieChart.notifyDataSetChanged();
            pieChart.postInvalidate();
        }
    }

//
    public View getView(Calendar date) {
        this.ShowedDate = date;
        setBar(date);
        return view;
    }
}