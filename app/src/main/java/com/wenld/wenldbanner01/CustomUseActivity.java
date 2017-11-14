package com.wenld.wenldbanner01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wenld.wenldbanner.AutoTurnViewPager;
import com.wenld.wenldbanner.DefaultPageIndicator;
import com.wenld.wenldbanner.helper.UIContact;

public class CustomUseActivity extends AppCompatActivity {
    AutoTurnViewPager autoTurnViewPager;
    DefaultPageIndicator defaultPageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_use);
        autoTurnViewPager = (AutoTurnViewPager) findViewById(R.id.autoTurnViewPager);
        defaultPageIndicator = (DefaultPageIndicator) findViewById(R.id.defaultPageIndicator);

        autoTurnViewPager.setPages(Common.holder)
                .setCanTurn(true)
                .setScrollDuration(2000);
        autoTurnViewPager.setPageTransformer(new ZoomOutPageTransformer());

        defaultPageIndicator.setPageIndicator(Common.indicatorGrouop);

        UIContact.with(autoTurnViewPager, defaultPageIndicator)
                .setData(Common.datas);

    }

    public void notifyDataSetChanged(View view) {
        autoTurnViewPager.getAdapter().notifyDataSetChanged();
    }
}
