package com.smu_bme.jigsaw;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.support.v4.app.Fragment;
import android.support.v4.hardware.display.DisplayManagerCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by bme-lab2 on 4/30/16.
 */
public class WrapContentHeightPager extends ViewPager {

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
            View chlid = getChildAt(i);
            chlid.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = chlid.getMeasuredHeight();
            if (h > height) {
                height = h;
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return true;
    }
}
