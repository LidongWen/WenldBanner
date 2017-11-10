package com.wenld.wenldbanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Author: 温利东 on 2017/11/6 15:00.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public class DefaultPageIndicator<T> extends LinearLayout implements PageIndicatorListener<T> {

    List<T> mDatas;
    private int[] pageIndicatorRes;
    private ArrayList<ImageView> mPointViews = new ArrayList<>();
    LinearLayout hView;
    int position = -1;

    public DefaultPageIndicator(@NonNull Context context) {
        this(context, null);
    }

    public DefaultPageIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultPageIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        hView = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.default_indicator, this, true);
        hView.setOrientation(LinearLayout.HORIZONTAL);
        pageIndicatorRes=new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused};
//        hView.setGravity(Gravity.CENTER);
    }

    public DefaultPageIndicator setPageIndicator(int[] page_indicatorId) {
        this.pageIndicatorRes = page_indicatorId;
        notifyDataChangedView();
        return this;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataChangedView();
    }

    @Override
    public List<T> getmDatas() {
        return null;
    }

    private void notifyDataChangedView() {
        mPointViews.clear();
        hView.removeAllViews();
        if (pageIndicatorRes == null) return;
        if (mDatas == null) return;
        for (int count = 0; count < mDatas.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            if (mPointViews.isEmpty())
                pointView.setImageResource(pageIndicatorRes[1]);
            else
                pointView.setImageResource(pageIndicatorRes[0]);
            mPointViews.add(pointView);
            hView.addView(pointView);
        }
        onPageSelected(position);
        return;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int index) {
        // 指示器
        this.position = index;
        for (int i = 0; i < mPointViews.size(); i++) {
            mPointViews.get(i).setImageResource(pageIndicatorRes[1]);
            if (index != i) {
                mPointViews.get(i).setImageResource(pageIndicatorRes[0]);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
