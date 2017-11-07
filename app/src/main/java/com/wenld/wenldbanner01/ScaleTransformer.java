package com.wenld.wenldbanner01;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * <p/>
 * Author: 温利东 on 2017/11/7 17:39.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class ScaleTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.5f;

    @Override
    public void transformPage(View page, float position) {
        final int pageWidth = page.getWidth();
        final float scaleFactor = MIN_SCALE + (0.8f - MIN_SCALE) * (1 - Math.abs(position));

        if (position < 0) { // [-1,0]
            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        } else if (position == 0) {
            page.setScaleX(0.8f);
            page.setScaleY(0.8f);

        } else if (position <= 1) { // (0,1]
            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

        }
    }
}
