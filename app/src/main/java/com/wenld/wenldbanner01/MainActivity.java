package com.wenld.wenldbanner01;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.wenld.wenldbanner.Holder;
import com.wenld.wenldbanner.ViewHolder;
import com.wenld.wenldbanner.WenldBanner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    WenldBanner wenldBanner;
    List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wenldBanner = (WenldBanner) findViewById(R.id.wenldBanner);
        datas = new ArrayList<>();
        datas.add("123456");
        datas.add("1234567");
        datas.add("1234568");

        wenldBanner.setPages(new Holder<String>() {
            @Override
            public ViewHolder createView(Context context, ViewGroup parent) {
                return ViewHolder.createViewHolder(context, parent, R.layout.layout_text);
            }

            @Override
            public void UpdateUI(Context context, ViewHolder viewHolder, int position, String data) {
                viewHolder.setText(R.id.tv,data);
            }
        }, datas);

        wenldBanner.setCanTurn(true);
        wenldBanner.setScrollDuration(2000);

        wenldBanner.setCurrentItem(4);

    }
}
