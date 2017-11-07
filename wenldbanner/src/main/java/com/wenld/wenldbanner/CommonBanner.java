package com.wenld.wenldbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.wenld.wenldbanner.helper.Holder;
import com.wenld.wenldbanner.helper.UIContact;

import java.util.List;

/**
 * Created by wenld on 2017/11/1.
 * * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class CommonBanner<T> extends FrameLayout {
    AutoTurnViewPager viewPager;
    PageIndicatorListener pageIndicatorListener;
    View indicatorView;
    List<T> mDatas;
    UIContact uiContact;

    public CommonBanner(@NonNull Context context) {
        this(context, null);
    }

    public CommonBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(
                R.layout.wenld_banner, this, true);
        viewPager = (AutoTurnViewPager) hView.findViewById(R.id.vp_wenld_banner);

    }

    public CommonBanner setPages(Holder<T> holer, List<T> data) {
        viewPager.setPages(holer);
        setData(data);
        return this;
    }

    private void initAttr(AttributeSet attrs) {
        setRunning(true); //是否正在执行翻页中   如果是canLoop=false  到头了 那就不翻页
        setCanLoop(true);// 是否循环
        setCanTurn(true);   //能否能执行自动翻页

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.wenldBanner);
        if (a != null) {
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.wenldBanner_canLoop) {
                    setCanLoop(a.getBoolean(attr, true));
                }
            }
            a.recycle();
        }
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible
     */
    public CommonBanner setInvaildViewVisible(boolean visible) {
        indicatorView.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * @param gravityAlign Gravity
     * @return
     */
    public CommonBanner setPageIndicatorAlign(int gravityAlign) {
        LayoutParams layoutParams = (LayoutParams) indicatorView.getLayoutParams();
        layoutParams.gravity = gravityAlign;
        indicatorView.setLayoutParams(layoutParams);
        return this;
    }

    public CommonBanner setIndicatorView(View indicatorView) {
        removeView(this.indicatorView);
        this.indicatorView = indicatorView;
        addView(this.indicatorView);
        return this;
    }

    public CommonBanner setPageIndicatorListener(PageIndicatorListener pageIndicatorListener) {
        this.pageIndicatorListener = pageIndicatorListener;
        getUiContact().addListener(pageIndicatorListener);
        return this;
    }

    public UIContact getUiContact() {

        if (uiContact == null) {
            uiContact = UIContact.with(viewPager, pageIndicatorListener);
        }
        return uiContact;
    }

    public boolean isCanTurn() {
        if (viewPager != null) {
            return viewPager.isCanTurn();
        }
        return false;
    }

    public CommonBanner startTurn() {
        if (viewPager != null) {
            viewPager.startTurn();
        }
        return this;
    }

    public CommonBanner stopTurning() {
        if (viewPager != null) {
            viewPager.stopTurning();
        }
        return this;
    }

    public void setData(List<T> mDatas) {
        this.mDatas = mDatas;
        getUiContact().setData(mDatas);
    }

    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public CommonBanner setPageTransformer(ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }

    public CommonBanner setScrollDuration(int duration) {
        viewPager.setScrollDuration(duration);
        return this;
    }


    public void setCurrentItem(int page) {
        viewPager.setCurrentItem(page);
        stopTurning();
        startTurn();
    }

    public boolean isRunning() {
        if (viewPager != null) {
            return viewPager.isRunning();
        }
        return false;
    }

    public CommonBanner setRunning(boolean running) {
        if (viewPager != null) {
            viewPager.setRunning(running);
        }
        return this;
    }

    public boolean isCanLoop() {
        if (viewPager != null) {
            return viewPager.isCanLoop();
        }
        return false;
    }

    public CommonBanner setCanLoop(boolean canLoop) {
        if (viewPager != null) {
            viewPager.setCanLoop(canLoop);
        }
        return this;
    }

    public CommonBanner setCanTurn(boolean canTurn) {
        if (viewPager != null) {
            viewPager.setCanTurn(canTurn);
        }
        return this;
    }

    public int getAutoTurnTime() {
        if (viewPager != null) {
            return viewPager.getAutoTurnTime();
        }
        return 5000;
    }

    public CommonBanner setAutoTurnTime(int autoTurnTime) {
        if (viewPager != null) {
            viewPager.setAutoTurnTime(autoTurnTime);
        }
        return this;
    }
}
