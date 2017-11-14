package com.wenld.wenldbanner01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ToxicBakery.viewpager.transforms.ScaleInOutTransformer;
import com.wenld.wenldbanner.DefaultPageIndicator;
import com.wenld.wenldbanner.WenldBanner;
import com.wenld.wenldbanner01.indicator.CustomIndicator;

public class CommonBannerActivity extends AppCompatActivity {
    WenldBanner wenldBanner;
    DefaultPageIndicator defaultPageIndicator;

    CustomIndicator customIndicator;
    CheckBox cb_loop, cb_autoTurn,cb_touchScroll;
    RadioGroup radioGroup;
    EditText etv;
    Button btn;
    private RadioGroup radioGroup5,radioGroup6;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private RadioGroup radioGroup4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_banner);
        wenldBanner = (WenldBanner) findViewById(R.id.commonBanner);
        cb_loop = (CheckBox) findViewById(R.id.cb_loop);
        cb_autoTurn = (CheckBox) findViewById(R.id.cb_autoTurn);
        cb_touchScroll = (CheckBox) findViewById(R.id.cb_touchScroll);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup5 = (RadioGroup) findViewById(R.id.radioGroup5);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
        radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);
        radioGroup6= (RadioGroup) findViewById(R.id.radioGroup6);
        etv= (EditText) findViewById(R.id.etv);
        btn= (Button) findViewById(R.id.btn);

        defaultPageIndicator = new DefaultPageIndicator(this);
        defaultPageIndicator.setPageIndicator(Common.indicatorGrouop);
        customIndicator = new CustomIndicator(this);

        //设置 view 与 数据
        wenldBanner.setPages(Common.holder, Common.datas);
        switchDefaultIndicator();

        cb_loop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wenldBanner.setCanLoop(isChecked);
            }
        });
        cb_autoTurn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wenldBanner.setCanTurn(isChecked);
            }
        });
        cb_touchScroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                wenldBanner.setTouchScroll(isChecked);
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
                        wenldBanner.setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
                        break;
                    case R.id.dicator_gravity_02:
                        wenldBanner.setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        break;
                }
            }
        });
        radioGroup6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.data:
                        wenldBanner.setData(Common.datas);
                        break;
                    case R.id.data2:
                        wenldBanner.setData(Common.datas2);
                        break;
                }
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.transformer_01:
                        wenldBanner.setPageTransformer(new ZoomOutPageTransformer());
                        break;
                    case R.id.transformer_02:
                        wenldBanner.setPageTransformer(new ScaleInOutTransformer());
                        break;
                }
            }
        });
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.time_01:
                        wenldBanner.setAutoTurnTime(5000);
                        break;
                    case R.id.time_02:
                        wenldBanner.setAutoTurnTime(10000);
                        break;
                }
            }
        });
        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.speeed_01:
                        wenldBanner.setScrollDuration(1000);
                        break;
                    case R.id.speeed_02:
                        wenldBanner.setScrollDuration(2000);
                        break;
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setcuurent(v);
            }
        });
    }

    void switchDefaultIndicator() {
        wenldBanner
                .setPageIndicatorListener(defaultPageIndicator)
                .setIndicatorView(defaultPageIndicator)
                .setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
    }

    /**
     * 切换自定义指示器
     */
    void switchCustomIndicator() {
        wenldBanner
                .setPageIndicatorListener(customIndicator)
                .setIndicatorView(customIndicator.getPageIndicatorView())
                .setPageIndicatorAlign(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_HORIZONTAL);
    }

    public void setcuurent(View view) {
        wenldBanner.getViewPager().setCurrentItem(Integer.parseInt(etv.getText().toString()),false);
    }
}
