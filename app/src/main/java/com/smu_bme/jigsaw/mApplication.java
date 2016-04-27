package com.smu_bme.jigsaw;

import android.app.Application;
import android.content.Context;

/**
 * Created by bme-lab2 on 4/28/16.
 */
public class mApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
