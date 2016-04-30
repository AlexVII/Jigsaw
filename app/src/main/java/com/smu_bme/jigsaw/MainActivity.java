package com.smu_bme.jigsaw;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    public FloatingActionButton fab;

    public Calendar ShowCalendar = null;


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
//    private CreateEventListener listener;

    public static final String CurrentDateString =  new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    public static final String ShowedDateString = CurrentDateString;
    public static final Calendar ShowedDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View layout = inflater.inflate(R.layout.dialog, null);
                final EditText name = (EditText) layout.findViewById(R.id.create_name);
                final EditText remark = (EditText) layout.findViewById(R.id.create_remark);
                final TextView textView = (TextView) layout.findViewById(R.id.edit_duration);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupMenu(MainActivity.this, v);
                    }
                });
                new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.create)).setView(layout).setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String nameInput = name.getText().toString();
                                String remarkInput = remark.getText().toString();
                                Calendar calendar = Calendar.getInstance();
                                if (nameInput.equals("")) {
                                    Toast.makeText(MainActivity.this, getString(R.string.noName), Toast.LENGTH_SHORT).show();
                                } else {
                                    int duration = 900;
                                    switch (checkedItemId){
                                        case R.id.quart_hour: duration = 900; break;
                                        case R.id.half_hour: duration = 1800; break;
                                        case R.id.one_hour: duration = 3600; break;
                                        case R.id.one_and_half_hour: duration = 5400; break;
                                        case R.id.two_hour: duration = 7200; break;
                                        case R.id.three_hour: duration = 10800; break;
                                    }
                                    Toast.makeText(MainActivity.this, "Duration is" + duration, Toast.LENGTH_SHORT).show();
                                    if (remarkInput.equals("")){
//                                        DbData dbData = new DbData(CurrentDateString, calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE) + "",duration, nameInput);
                                    } else {
//                                        DbData dbData = new DbData(CurrentDateString, calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE) + "",duration, nameInput, remarkInput );
                                    }
                                }
                            }
                        }).setNegativeButton(getString(R.string.cancel), null).show();
            }
        });

}

    private int checkedItemId = R.id.quart_hour;
    private void showPopupMenu(final Context context, View view){
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.duration);
        popupMenu.getMenu().findItem(checkedItemId).setChecked(true);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.quart_hour:
                        checkedItemId = R.id.quart_hour;
                        break;
                    case R.id.half_hour:
                        checkedItemId = R.id.half_hour;
                        break;
                    case R.id.one_hour:
                        checkedItemId = R.id.one_hour;
                        break;
                    case R.id.one_and_half_hour:
                        checkedItemId = R.id.one_and_half_hour;
                        break;
                    case R.id.two_hour:
                        checkedItemId = R.id.two_hour;
                        break;
                    case R.id.three_hour:
                        checkedItemId = R.id.three_hour;
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
    public static class PlaceholderFragment extends Fragment {

        int CurrentMins  = Calendar.getInstance().get(Calendar.MINUTE);
        private List<DbData> list;

        private DbHelper dbHelper = new DbHelper(getActivity());

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {}


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
                return Chart.initChart(inflater, container);
            }
        }

        public View initCardAndProgressBar(LayoutInflater inflater, final ViewGroup container){
            View rootView = inflater.inflate(R.layout.layout_log, container, false);
            final TextView textView = (TextView) rootView.findViewById(R.id.date);
            textView.setText(ShowedCalendar);
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
            if (ShowedDateString.equals(CurrentDateString)){
            progressBar.setSecondaryProgress(CurrentMins);
            } else { progressBar.setSecondaryProgress(1440);
//   TODO         progressBar.setProgress();
            }
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.event_list);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            DbHelper dbHelper = new DbHelper(getActivity());
            list = dbHelper.queryData("date","1970-1-1");
            mAdapter mAdapter = new mAdapter(list, getActivity()) ;
            recyclerView.setAdapter(mAdapter);
            return rootView;

        }



        public void initEvent(){
            DbData dbData1 = new DbData("1970-1-1", "03:33", 200, "Test1");
            DbData dbData2 = new DbData("1970-1-1", "05:33", 200, "Test2");
            DbData dbData3 = new DbData("1970-1-1", "20:33", 200, "Test1");
            DbData dbData4 = new DbData("1970-1-1", "23:33", 200, "Test2");
            DbData dbData5 = new DbData("1970-1-2", "03:33", 200, "Test5");
            DbData dbData6 = new DbData("1970-1-2", "13:33", 200, "Test1");
            DbData dbData7 = new DbData("1970-1-2", "23:33", 200, "Test2");
            DbHelper dbHelper = new DbHelper(getActivity());
            dbHelper.addData(dbData1);
            dbHelper.addData(dbData2);
            dbHelper.addData(dbData3);
            dbHelper.addData(dbData4);
            dbHelper.addData(dbData5);
            dbHelper.addData(dbData6);
            dbHelper.addData(dbData7);
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
