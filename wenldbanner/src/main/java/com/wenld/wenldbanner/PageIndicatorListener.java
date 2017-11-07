package com.wenld.wenldbanner;

import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * <p/>
 * Author: 温利东 on 2017/11/6 15:03.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public interface PageIndicatorListener<T> extends ViewPager.OnPageChangeListener {
    void setmDatas(List<T> mDatas);

    List<T> getmDatas();
}
