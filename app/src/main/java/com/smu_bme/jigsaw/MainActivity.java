package com.smu_bme.jigsaw;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
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
                switch (view.getId()){
                    case R.id.fab:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("Create A New Plan");
                        dialog.setMessage("Create");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Take this", Toast.LENGTH_LONG).show();
                            }
                        });
                        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                        break;
                    default:
                        break;
                }
            }
        });

    }
    public static class PlaceholderFragment extends Fragment {

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
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                return initCardAndProgressBar(inflater, container);
            } else {
                return initChart(inflater, container);
            }
        }


        public View initCardAndProgressBar(LayoutInflater inflater, final ViewGroup container){
            eventTest one = new eventTest("one");
            eventTest two = new eventTest("two");
            int [] idArr = {one.getid(), two.getid()};
            View rootView = inflater.inflate(R.layout.log_layout, container, false);
            final TextView textView = (TextView) rootView.findViewById(R.id.date);
            textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
            ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.edit_date);
            imageButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                       @Override
                       public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                           calendar.set(Calendar.YEAR, year);
                           calendar.set(Calendar.MONTH, monthOfYear);
                           calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                           textView.setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
                       }
                   };
                   new DatePickerDialog(getActivity(), dateListener,
                           calendar.get(Calendar.DAY_OF_MONTH),
                           calendar.get(Calendar.MONTH),
                           calendar.get(Calendar.DAY_OF_MONTH)).show();
               }
           });
            ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
            progressBar.setMax(14400);
//            if ( 查看日期 == 现在日期){
//            progressBar.setSecondaryProgress(现在时间mins);
//            } else { progressBar.setSecondaryProgress(1440);
//        }
//            progressBar.setProgress( 查看日期总计时间mins );
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.event_list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new mAdapter(idArr));
            return rootView;

        }

        public View initChart (LayoutInflater inflater, ViewGroup container){

            View rootView = inflater.inflate(R.layout.data_layout, container, false);
            BarChart barChart = (BarChart) rootView.findViewById(R.id.line_chart);
            ArrayList<String> xVals = new ArrayList<String>();
            xVals.add("星期日");xVals.add("星期一");xVals.add("星期二");xVals.add("星期三");xVals.add("星期四");xVals.add("星期五");xVals.add("星期六");

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
            return rootView;

        }
    }

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
