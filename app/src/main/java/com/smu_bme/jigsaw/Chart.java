package com.smu_bme.jigsaw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by gollyrui on 4/30/16.
 */
public class Chart extends View{
        private LayoutInflater inflater;
        private ViewGroup container;
        private Context context;
//        protected Calendar ShowedDate;
        protected View view;
        private SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        private DbHelper dbHelper;
        private BarChart barChart;
        private BarDataSet barDataSet;
        private BarDataSet pieDataSet;
        private ArrayList<String> xVals;
        private PieChart pieChart;
        private ArrayList<BarEntry> valsComp1 = new ArrayList<>();
        private ArrayList<BarEntry> valsComp2 = new ArrayList<>();
        private ArrayList<IBarDataSet> dataSet = new ArrayList<>();


        public Chart(LayoutInflater inflater, ViewGroup container, Context context, Calendar ShowedDate) {
            super(context);
            this.inflater = inflater;
            this.container = container;
            this.context = context;
//            this.ShowedDate = ShowedDate;
            dbHelper = new DbHelper(context);
            //Initialize xVals for BarChart
            {
                ArrayList<String> xVals = new ArrayList<>();
                xVals.add("星期日");
                xVals.add("星期一");
                xVals.add("星期二");
                xVals.add("星期三");
                xVals.add("星期四");
                xVals.add("星期五");
                xVals.add("星期六");
                this.xVals = xVals;
            }


                view = inflater.inflate(R.layout.layout_data, container, false);
            {
                //Initialize Chart reference
                barChart = (BarChart) view.findViewById(R.id.bar_chart);
                pieChart = (PieChart) view.findViewById(R.id.pie_chart);
            }

                barDataSet = new BarDataSet(valsComp1, "星期");// 'valsComp1" is address
                pieDataSet= new BarDataSet(valsComp2, "活动");//same
                dataSet.add(barDataSet);
                dataSet.add(pieDataSet);

                for (int i = 1; i < 8; i++) {
                    ShowedDate.set(Calendar.DAY_OF_WEEK, i);
                    String date = f.format(ShowedDate.getTime());
                    float sum = (float) dbHelper.querySum(date);
                    if (sum < 0) sum = 0;
                    BarEntry Chart1Element = new BarEntry(sum, i - 1);
                    valsComp1.add(Chart1Element);
                }


                barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);



                BarData data = new BarData(xVals, dataSet);
                barChart.setData(data);
                barChart.setScaleEnabled(false);
                barChart.setHighlightPerTapEnabled(true);
                barChart.setDescriptionTextSize(10);
                barChart.animateY(5000);
                barChart.setNoDataTextDescription("这一周都没有记录呢");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                barChart.setDescription("一周学习");  // set the description

//              barChart.highlightValues(Highlight[] highs);

                barChart.invalidate();




                pieDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                ArrayList<String> labels = new ArrayList<String>();
                pieChart.setUsePercentValues(true);
                pieChart.setExtraOffsets(5, 10, 5, 5);
                pieChart.setDragDecelerationFrictionCoef(0.95f);
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
/*
            TreeMap<String, Float> data3 = new TreeMap<>();
            data3.put("data1", 0.5f);
            data3.put("data2", 0.3f);
            data3.put("data3", 0.1f);
            data3.put("data4", 0.1f);
            pieChart.setData(data3);

            // 设置动画
            pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

            // 设置显示的比例
            Legend l = pieChart.getLegend();
            l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

*/
                labels.add("Math");
                labels.add("English");
                labels.add("Physics");
                //    ArrayList<PieEntry> valsComp1 = new ArrayList<>();
                ArrayList<Entry> entries = new ArrayList<>();
                entries.add(new Entry(4f, 0));
                entries.add(new Entry(8f, 1));
                entries.add(new Entry(6f, 2));

                // 设置显示的比例
                Legend l = pieChart.getLegend();
                l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
                l.setXEntrySpace(7f);
                l.setYEntrySpace(0f);
                l.setYOffset(0f);

                pieChart.setCenterText("每日学习");
                PieDataSet dataset2 = new PieDataSet(entries, "项目");
                PieData data2 = new PieData(labels, dataset2);
                pieChart.setData(data2);
                //      PieChart.invalidate();
                pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
                dataset2.setColors(ColorTemplate.COLORFUL_COLORS);
                pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    }
    public void update(Calendar ShowedDate){

    }
    private BarData setBarData(Calendar ShowedDate){

        BarData data = new BarData(xVals, dataSet);
        return data;
    }
    public void pieChart(){

    }



    public View getView() {
        return view;
    }
}