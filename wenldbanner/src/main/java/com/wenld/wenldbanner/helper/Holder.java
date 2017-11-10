package com.wenld.wenldbanner.helper;

import android.content.Context;
import android.view.ViewGroup;

/**
 * <p/>
 * Author: 温利东 on 2017/11/2 16:20.
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 */

public interface Holder<T>{
    ViewHolder createView(Context context,
                          ViewGroup parent,int position,int viewType);
    void UpdateUI(Context context, ViewHolder viewHolder, int position, T data);
    int getViewType(int position);
}