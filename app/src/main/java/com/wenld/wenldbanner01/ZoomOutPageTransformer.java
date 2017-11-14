package com.wenld.wenldbanner01;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wenld.wenldbanner.MyLog;

/**
 * <p/>
 * Author: 温利东 on 2017/11/8 9:42.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    @SuppressLint("NewApi")
    public void transformPage(View view, float position) {
        MyLog.e("PageTransformer", " " + position);
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(MIN_ALPHA);
//            view.setTranslationX(horzMargin - vertMargin / 2);
            view.setScaleX(MIN_SCALE);
        } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
        { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;

            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(MIN_ALPHA);
            view.setScaleX(MIN_SCALE);
        }
    }
}