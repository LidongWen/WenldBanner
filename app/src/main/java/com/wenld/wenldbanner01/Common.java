package com.wenld.wenldbanner01;

import android.content.Context;
import android.view.ViewGroup;

import com.wenld.wenldbanner.helper.Holder;
import com.wenld.wenldbanner.helper.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Author: 温利东 on 2017/11/7 14:18.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class Common {
    public static List<String> datas;
    public static Holder<String> holder;
    public static int[] indicatorGrouop;
    static {
        datas = new ArrayList<>();
        datas.add("123456");
        datas.add("1234567");
        datas.add("1234568");

        holder = new Holder<String>() {
            @Override
            public ViewHolder createView(Context context, ViewGroup parent) {
                return ViewHolder.createViewHolder(context, parent, R.layout.layout_text);
            }

            @Override
            public void UpdateUI(Context context, ViewHolder viewHolder, int position, String data) {
                viewHolder.setText(R.id.tv, data);
                viewHolder.setBackgroundRes(R.id.tv,R.color.colorAccent);
            }
        };

        indicatorGrouop=new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused};
    }
}
