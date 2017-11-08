package com.wenld.wenldbanner01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.wenld.wenldbanner.CommonBanner;
import com.wenld.wenldbanner.DefaultPageIndicator;
import com.wenld.wenldbanner01.indicator.CustomIndicator;

public class CommonBannerActivity extends AppCompatActivity {
    CommonBanner commonBanner;
    DefaultPageIndicator defaultPageIndicator;

    CustomIndicator customIndicator;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_banner);
        commonBanner = (CommonBanner) findViewById(R.id.commonBanner);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        defaultPageIndicator = new DefaultPageIndicator(this);
        defaultPageIndicator.setPageIndicator(Common.indicatorGrouop);
        customIndicator = new CustomIndicator(this);

        commonBanner.setPages(Common.holder, Common.datas);
        switchDefaultIndicator();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.default_dicator:
                        switchDefaultIndicator();
                        break;
                    case R.id.custom_dicator:
                        switchCustomIndicator();
                        break;
                }
            }
        });
    }

    void switchDefaultIndicator() {
        commonBanner
                .setPageIndicatorListener(defaultPageIndicator)
                .setIndicatorView(defaultPageIndicator)
                .setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
    }

    /**
     * 切换自定义指示器
     */
    void switchCustomIndicator() {
        commonBanner
                .setPageIndicatorListener(customIndicator)
                .setIndicatorView(customIndicator.getPageIndicatorView())
                .setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
    }
}
