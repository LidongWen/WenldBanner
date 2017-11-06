package com.wenld.wenldbanner01;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.wenld.wenldbanner.AutoTurnViewPager;
import com.wenld.wenldbanner.DefaultPageIndicator;
import com.wenld.wenldbanner.Holder;
import com.wenld.wenldbanner.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    AutoTurnViewPager autoTurnViewPager;
    DefaultPageIndicator<String> defaultPageIndicator;
    List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoTurnViewPager = (AutoTurnViewPager) findViewById(R.id.autoTurnViewPager);
        defaultPageIndicator = (DefaultPageIndicator<String>) findViewById(R.id.defaultPageIndicator);

        datas = new ArrayList<>();
        datas.add("123456");
        datas.add("1234567");
        datas.add("1234568");

        autoTurnViewPager.setPages(new Holder<String>() {
            @Override
            public ViewHolder createView(Context context, ViewGroup parent) {
                return ViewHolder.createViewHolder(context, parent, R.layout.layout_text);
            }

            @Override
            public void UpdateUI(Context context, ViewHolder viewHolder, int position, String data) {
                viewHolder.setText(R.id.tv, data);
            }
        }, datas);
        autoTurnViewPager.setCanTurn(true);
        autoTurnViewPager.setScrollDuration(2000);

        defaultPageIndicator.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        defaultPageIndicator.setmDatas(datas);

        autoTurnViewPager.addOnPageChangeListener(defaultPageIndicator);
        defaultPageIndicator.onPageSelected(autoTurnViewPager.getCurrentItem());


//        autoTurnViewPager.setAutoTurnTime(20000);
//        autoTurnViewPager.setCurrentItem(4);

    }
}
