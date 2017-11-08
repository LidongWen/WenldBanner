package com.wenld.wenldbanner01.indicator;

import android.content.Context;

import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.wenld.wenldbanner.PageIndicatorListener;
import com.wenld.wenldbanner01.Common;

import java.util.List;

/**
 * <p/>
 * Author: 温利东 on 2017/11/8 16:05.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class CustomIndicator implements PageIndicatorListener {

    private Context context;
    PageIndicatorView pageIndicatorView;

    public CustomIndicator(Context context) {
        this.context = context;
        pageIndicatorView = new PageIndicatorView(this.context);

        pageIndicatorView.setRadius(5);
        pageIndicatorView.setPadding(5);
        pageIndicatorView.setStrokeWidth(5);
        //set color
        pageIndicatorView.setUnselectedColor(Common.indicatorColors[0]);
        pageIndicatorView.setSelectedColor(Common.indicatorColors[2]);
        pageIndicatorView.setAnimationType(AnimationType.THIN_WORM);
    }

    public PageIndicatorView getPageIndicatorView() {
        return pageIndicatorView;
    }

    @Override
    public void setmDatas(List mDatas) {
        pageIndicatorView.setCount(mDatas.size());
    }

    @Override
    public List getmDatas() {
        return null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageIndicatorView.setSelection(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
