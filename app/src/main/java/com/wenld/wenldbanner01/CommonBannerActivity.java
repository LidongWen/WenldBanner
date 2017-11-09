package com.wenld.wenldbanner01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.wenld.wenldbanner.CommonBanner;
import com.wenld.wenldbanner.DefaultPageIndicator;
import com.wenld.wenldbanner01.indicator.CustomIndicator;

public class CommonBannerActivity extends AppCompatActivity {
    CommonBanner commonBanner;
    DefaultPageIndicator defaultPageIndicator;

    CustomIndicator customIndicator;
    CheckBox cb_loop, cb_autoTurn,cb_touchScroll;
    RadioGroup radioGroup;
    private RadioGroup radioGroup5;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private RadioGroup radioGroup4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_banner);
        commonBanner = (CommonBanner) findViewById(R.id.commonBanner);
        cb_loop = (CheckBox) findViewById(R.id.cb_loop);
        cb_autoTurn = (CheckBox) findViewById(R.id.cb_autoTurn);
        cb_touchScroll = (CheckBox) findViewById(R.id.cb_touchScroll);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup5 = (RadioGroup) findViewById(R.id.radioGroup5);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
        radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);

        defaultPageIndicator = new DefaultPageIndicator(this);
        defaultPageIndicator.setPageIndicator(Common.indicatorGrouop);
        customIndicator = new CustomIndicator(this);

        //设置 view 与 数据
        commonBanner.setPages(Common.holder, Common.datas);
        switchDefaultIndicator();

        cb_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                commonBanner.setCanLoop(isChecked);
            }
        });
        cb_autoTurn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                commonBanner.setCanTurn(isChecked);
            }
        });
        cb_touchScroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                commonBanner.setTouchScroll(isChecked);
            }
        });

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
        radioGroup5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.dicator_gravity_01:
                        commonBanner.setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
                        break;
                    case R.id.dicator_gravity_02:
                        commonBanner.setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        break;
                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.transformer_01:
                        commonBanner.setPageTransformer(new RotateDownTransformer());
                        break;
                    case R.id.transformer_02:
                        commonBanner.setPageTransformer(new ScaleInOutTransformer());
                        break;
                }
            }
        });
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.time_01:
                        commonBanner.setAutoTurnTime(5000);
                        break;
                    case R.id.time_02:
                        commonBanner.setAutoTurnTime(10000);
                        break;
                }
            }
        });
        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.speeed_01:
                        commonBanner.setScrollDuration(1000);
                        break;
                    case R.id.speeed_02:
                        commonBanner.setScrollDuration(2000);
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
