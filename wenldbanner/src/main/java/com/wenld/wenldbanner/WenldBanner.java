package com.wenld.wenldbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.wenld.wenldbanner.helper.Holder;
import com.wenld.wenldbanner.helper.UIContact;

import java.util.List;

/**
 * Created by wenld on 2017/11/1.
 * * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class WenldBanner<T> extends RelativeLayout {
    AutoTurnViewPager viewPager;
    PageIndicatorListener pageIndicatorListener;
    View indicatorView;
    List<T> mDatas;
    UIContact uiContact;

    public WenldBanner(@NonNull Context context) {
        this(context, null);
    }

    public WenldBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WenldBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttr(attrs);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(
                R.layout.wenld_banner, this, true);
        viewPager = (AutoTurnViewPager) hView.findViewById(R.id.vp_wenld_banner);
    }

    public WenldBanner setPages(Holder<T> holer, List<T> data) {
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
                } else if (attr == R.styleable.wenldBanner_canTurn) {
                    setCanTurn(a.getBoolean(attr, true));
                } else if (attr == R.styleable.wenldBanner_isTouchScroll) {
                    setTouchScroll(a.getBoolean(attr, true));
                } else if (attr == R.styleable.wenldBanner_autoTurnTime) {
                    setAutoTurnTime(a.getInteger(attr, getAutoTurnTime()));
                } else if (attr == R.styleable.wenldBanner_scrollDuration) {
                    setScrollDuration(a.getInteger(attr, getScrollDuration()));
                } else if (attr == R.styleable.wenldBanner_reverse) {
                    setReverse(a.getBoolean(attr, false));
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
    public WenldBanner setInvaildViewVisible(boolean visible) {
        indicatorView.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * @param gravityAlign RelativeLayout
     * @return
     */
    public WenldBanner setPageIndicatorAlign(int... gravityAlign) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) indicatorView.getLayoutParams();
        for (int i = 0; i < gravityAlign.length; i++) {
            layoutParams.addRule(gravityAlign[i]);
        }
        layoutParams.setMargins(5, 5, 5, 5);
        indicatorView.setLayoutParams(layoutParams);
        return this;
    }

    public WenldBanner setIndicatorView(View indicatorView) {
        removeView(this.indicatorView);
        this.indicatorView = indicatorView;
        addView(this.indicatorView);
        return this;
    }

    public WenldBanner setPageIndicatorListener(PageIndicatorListener pageIndicatorListener) {
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

    public WenldBanner startTurn() {
        if (viewPager != null) {
            viewPager.startTurn();
        }
        return this;
    }

    public WenldBanner stopTurning() {
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
    public WenldBanner setPageTransformer(ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(true, transformer);
        return this;
    }

    public WenldBanner setScrollDuration(int duration) {
        viewPager.setScrollDuration(duration);
        return this;
    }

    public int getScrollDuration() {
        return viewPager.getScrollDuration();
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

    public WenldBanner setRunning(boolean running) {
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

    public WenldBanner setCanLoop(boolean canLoop) {
        if (viewPager != null) {
            viewPager.setCanLoop(canLoop);
        }
        return this;
    }

    public WenldBanner setCanTurn(boolean canTurn) {
        if (viewPager != null) {
            viewPager.setCanTurn(canTurn);
        }
        return this;
    }

    public void setTouchScroll(boolean isCanScroll) {
        if (viewPager != null) {
            viewPager.setTouchScroll(isCanScroll);
        }
    }

    public int getAutoTurnTime() {
        if (viewPager != null) {
            return viewPager.getAutoTurnTime();
        }
        return 5000;
    }

    public WenldBanner setAutoTurnTime(int autoTurnTime) {
        if (viewPager != null) {
            viewPager.setAutoTurnTime(autoTurnTime);
        }
        return this;
    }

    public void setReverse(boolean reverse) {
        if (viewPager != null) {
            viewPager.setReverse(reverse);
        }
    }

    public boolean isReverse() {
        if (viewPager != null) {
            return viewPager.isReverse();
        }
        return false;
    }
    public void setOnItemClickListener(OnPageClickListener onItemClickListener){
        if (viewPager != null) {
            viewPager.setOnItemClickListener(onItemClickListener);
        }
    }
}
