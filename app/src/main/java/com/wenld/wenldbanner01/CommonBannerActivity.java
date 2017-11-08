package com.wenld.wenldbanner01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.wenld.wenldbanner.CommonBanner;
import com.wenld.wenldbanner.DefaultPageIndicator;

public class CommonBannerActivity extends AppCompatActivity {
    CommonBanner commonBanner;
    DefaultPageIndicator defaultPageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_banner);
        commonBanner = (CommonBanner) findViewById(R.id.commonBanner);
        defaultPageIndicator = new DefaultPageIndicator(this);
        defaultPageIndicator.setPageIndicator(Common.indicatorGrouop);


        commonBanner.setPages(Common.holder, Common.datas)
                .setPageIndicatorListener(defaultPageIndicator)
                .setIndicatorView(defaultPageIndicator)
                .setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
    }
}
