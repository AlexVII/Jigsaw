package com.smu_bme.jigsaw;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.support.v4.app.Fragment;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by bme-lab2 on 4/30/16.
 */
public class WrapContentHeightPager extends ViewPager {

    private WindowManager manager;
    private int postion = 0;
    private int MaxPos = 0;

    public WrapContentHeightPager(Context context) {
        super(context);
    }

    public WrapContentHeightPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) {
                height = h;
            }
        }
        Display display = manager.getDefaultDisplay();
        Point size= new Point();
        display.getSize(size);
        int h = size.y;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(h - 450, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return true;
    }

    public void setManager(WindowManager manager) {
        this.manager = manager;
    }
}
