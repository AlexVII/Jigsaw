package com.smu_bme.jigsaw;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by bme-lab on 4/28/16.
 */
public class DbData implements Serializable {
    private int id;
    private String date;
    private int duration;
    private String time;
    private String name;
    private String remark;
    private int sumDate;
    private int sumAll;
    private int setID;

    // Full
    public DbData(int id, String date, int duration, String time, String name, String remark, int sumDate, int sumAll) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.time = time;
        this.name = name;
        this.remark = remark;
        this.sumDate = sumDate;
        this.sumAll = sumAll;
    }

    //Without information from SUM
    public DbData(int id, String date, int duration, String time, String name, String remark) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.time = time;
        this.name = name;
        this.remark = remark;
    }

    // when input data
//    For alex
    public DbData(String date, String time, int duration, String name, String remark) {
        this.date = date;
        this.duration = duration;
        this.time = time;
        this.name = name;
        this.remark = remark;
    }

    public DbData(String date, String time, int duration, String name) {
        Log.d ("DEBUGGING", "DbData Constructor Running");
        this.date = date;
        this.duration = duration;
        this.time = time;
        this.name = name;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public int getId() {
        return id;
    }

    public int getSumOfAll() {
        return sumAll;
    }

    public int getSumOnDate() {
        return sumDate;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public String getTime() {
        return time;
    }

    public int getSetID() {
        return setID;
    }

    public void setSetID(int setID) {
        this.setID = setID;
    }

    public boolean validation() {
        return (date != null && duration != 0 && time != null);

//        private int id;
//        private String date;
//        private int duration;
//        private String time;
//        private String name;
//        private String remark;
//        private int sumDate;
//        private int sumAll;
    }
}
