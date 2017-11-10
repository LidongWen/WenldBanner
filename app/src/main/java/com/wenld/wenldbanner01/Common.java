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
    public static int[] bgColors;
    public static int[] indicatorColors;

    static {
        datas = new ArrayList<>();
        datas.add("第一个");
        datas.add("第二个");
        datas.add("第三个");

        bgColors = new int[]{0xff66cccc, 0xffccff66, 0xffff99cc};
        indicatorColors = new int[]{0xff993366, 0xffffff66, 0xff666633};

        holder = new Holder<String>() {
            @Override
            public ViewHolder createView(Context context, ViewGroup parent, int pos,int viewType) {
                return ViewHolder.createViewHolder(context, parent, R.layout.layout_text, getViewType(pos));
            }

            @Override
            public void UpdateUI(Context context, ViewHolder viewHolder, int position, String data) {
                viewHolder.setText(R.id.tv, data);
                viewHolder.setBackgroundColor(R.id.tv, bgColors[position % bgColors.length]);
            }

            @Override
            public int getViewType(int position) {
                return 0;
            }
        };

        indicatorGrouop = new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused};
    }
}
