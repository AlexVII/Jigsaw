package com.smu_bme.jigsaw;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH);
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    public static class PlaceholderFragment extends Fragment {

        private RecyclerView recyclerView;
        private RecyclerView.Adapter adapter;
        private RecyclerView.LayoutManager layoutManager;

//        Get Current Date
        Calendar calendar = Calendar.getInstance();
        private int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        private int currentMouth = calendar.get(Calendar.MONTH);
        private int currentYear = calendar.get(Calendar.YEAR);

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {



            eventTest one = new eventTest("one");
            eventTest two = new eventTest("two");
            int [] idArr = {one.getid(), two.getid()};




            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                View rootView = inflater.inflate(R.layout.log_layout, container, false);
            ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
//            if ( 查看日期 == 现在日期){
            progressBar.setMax(14400);
//            progressBar.setSecondaryProgress(现在时间mins);
//            } else { progressBar.setSecondaryProgress(1440);
//        }
//            progressBar.setProgress( 查看日期总计时间mins );
                recyclerView = (RecyclerView) rootView.findViewById(R.id.event_list);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(mApplication.getContext());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new mAdapter(idArr);
                recyclerView.setAdapter(adapter);
                return rootView;
            } else {
                View rootView = inflater.inflate(R.layout.data_layout, container, false);
                BarChart barChart = (BarChart) rootView.findViewById(R.id.line_chart);
                initChart(barChart, null);
                return rootView;
            }
        }


        public void initChart (BarChart barChart, PieChart pieChart){

            ArrayList<String> xVals = new ArrayList<String>();
            xVals.add(String.valueOf(currentDay));
            for (int i = 0; i <= 5; i++ ){
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                xVals.add(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            }

            ArrayList<BarEntry> valsComp1 = new ArrayList<>();
            ArrayList<BarEntry> valsComp2 = new ArrayList<>();

            BarEntry c1e1 = new BarEntry(233f, 0);
            valsComp1.add(c1e1);
            BarEntry c1e2 = new BarEntry(2333f, 1);
            valsComp1.add(c1e2);
            BarEntry c2e1 = new BarEntry(233f, 0);
            valsComp1.add(c2e1);
            BarEntry c2e2 = new BarEntry(233f, 1);
            valsComp1.add(c2e2);

            BarDataSet setc1 = new BarDataSet(valsComp1, "C1");
            setc1.setAxisDependency(YAxis.AxisDependency.LEFT);
            BarDataSet setc2 = new BarDataSet(valsComp2, "C2");
            setc2.setAxisDependency(YAxis.AxisDependency.LEFT);

            ArrayList<IBarDataSet> dataSet = new ArrayList<IBarDataSet>();
            dataSet.add(setc1);
            dataSet.add(setc2);
            BarData data = new BarData(xVals, dataSet);
            barChart.setData(data);
            barChart.invalidate();

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);

        }

        @Override
        public int getCount() {
            return 2;
        }

            @Override
            public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Log";
                case 1:
                    return "Data";
            }
            return null;
        }
    }

}
