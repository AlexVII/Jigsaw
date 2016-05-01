package com.smu_bme.jigsaw;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.Chart;

import java.sql.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private DbHelper dbHelper;
    public Calendar CurrentCalendar = Calendar.getInstance();
    public Calendar ShowedCalendar = CurrentCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            private int durationInt = 900;
            private String durationString = getString(R.string.quart_hour);
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View layout = inflater.inflate(R.layout.dialog, null);
                final EditText name = (EditText) layout.findViewById(R.id.create_name);
                final EditText remark = (EditText) layout.findViewById(R.id.create_remark);
                TextView textView = (TextView) layout.findViewById(R.id.show_duration);
                textView.setText(durationString);
                ImageButton imageButton = (ImageButton) layout.findViewById(R.id.edit_duration);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                        popupMenu.inflate(R.menu.duration);
                        popupMenu.getMenu().findItem(R.id.quart_hour).setChecked(true);
                        final TextView textView = (TextView) layout.findViewById(R.id.show_duration);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.quart_hour: durationString = getString(R.string.quart_hour);
                                        durationInt = 900; break;
                                    case R.id.half_hour: durationString = getString(R.string.half_hour);
                                        durationInt = 1800; break;
                                    case R.id.one_hour: durationString = getString(R.string.one_hour);
                                        durationInt = 3600; break;
                                    case R.id.one_and_half_hour: durationString = getString(R.string.one_and_half_hour);
                                        durationInt = 5400; break;
                                    case R.id.two_hour: durationString = getString(R.string.two_hour);
                                        durationInt = 7200; break;
                                    case R.id.three_hour: durationString = getString(R.string.three_hour);
                                        durationInt = 14400; break;
                                }
                                textView.setText(durationString);
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                });
                new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.create)).setView(layout).setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            String CurrentDateString = new SimpleDateFormat("yyyy-MM-dd").format(CurrentCalendar.getTime());
                            String CurrentTimeString = new SimpleDateFormat("mm:ss").format(CurrentCalendar.getTime());
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nameInput = name.getText().toString();
                                String remarkInput = remark.getText().toString();
                                if (nameInput.equals("")) {
                                    Toast.makeText(MainActivity.this, getString(R.string.noName), Toast.LENGTH_SHORT).show();
                                } else {
                                    dbHelper = new DbHelper(MainActivity.this);
                                    DbData dbData;
                                    if (remarkInput.equals("")){
                                        dbData = new DbData(CurrentDateString, CurrentTimeString ,durationInt, nameInput);
                                        dbHelper.addData(dbData);
                                    } else {
                                        dbData = new DbData(CurrentDateString, CurrentTimeString ,durationInt, nameInput, remarkInput);
                                        dbHelper.addData(dbData);
                                    }
                                    Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                                    intent.putExtra("Event", dbData);
                                    startActivity(intent);
                                }
                            }
                        }).setNegativeButton(getString(R.string.cancel), null).show();
            }
        });
        Intent intent = getIntent();
        if (intent.getStringExtra("Action").equals("button")){
           fab.callOnClick();
        } else if (intent.getStringExtra("Action").equals("dialog")) {
            new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.create))
                    .setPositiveButton(getString(R.string.yes),null).show();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        public static Calendar CurrentCalendar = Calendar.getInstance();
        public Calendar ShowedCalendar = CurrentCalendar;
        public static LogUI logUI;
        private static ChartView chart;

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
            View view;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                logUI = new LogUI(getContext(), inflater, container);
                view = logUI.getView(getContext(), ShowedCalendar);
            } else {
                chart = new ChartView(inflater,container,getContext(),ShowedCalendar);
                ShowedCalendar.setTime(Date.valueOf("1970-1-1"));
                view = chart.getView(ShowedCalendar);
            }
            return view;
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
                    return getString(R.string.Log);
                case 1:
                    return getString(R.string.Data);
            }
            return null;
        }
    }
}
