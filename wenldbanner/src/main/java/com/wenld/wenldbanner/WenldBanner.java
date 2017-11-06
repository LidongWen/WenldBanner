package com.wenld.wenldbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by wenld on 2017/11/1.
 * * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class WenldBanner<T> extends FrameLayout {
    AutoTurnViewPager viewPager;
    ViewHolder indicatorViewHolder;
    PageIndicatorListener pageIndicatorListener;
    List<T> mDatas;

    private ViewPagerScroller scroller;

    public WenldBanner(@NonNull Context context) {
        this(context, null);
    }

    public WenldBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WenldBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(
                R.layout.wenld_banner, this, true);
        viewPager = (AutoTurnViewPager) hView.findViewById(R.id.vp_wenld_banner);
        indicatorViewHolder = new ViewHolder(getContext(), hView.findViewById(R.id.indicator_wenld_banner));
        initViewPagerScroll();
    }

    public WenldBanner setPages(Holder<T> holer, List<T> data) {
        this.mDatas = data;
        WenldPagerAdapter adapter = new WenldPagerAdapter(holer, mDatas);
        adapter.setViewPager(viewPager);
        viewPager.setAdapter(adapter);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            startTurn();
        } else if (action == MotionEvent.ACTION_DOWN) {
            stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible
     */
    public WenldBanner setInvaildViewVisible(boolean visible) {
        indicatorViewHolder.getConvertView().setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public void setPageIndicatorListener(PageIndicatorListener pageIndicatorListener) {
        this.pageIndicatorListener = pageIndicatorListener;

    }

    /**
     * 底部指示器资源图片
     *
     * @param page_indicatorId
     */
    public WenldBanner setPageIndicatorListener(int[] page_indicatorId) {
//        ViewGroup indicatorParent = ((ViewGroup) indicatorViewHolder.getConvertView());
//        indicatorParent.removeAllViews();
//        mPointViews.clear();
//        this.page_indicatorId = page_indicatorId;
//        if (mDatas == null) return this;
//        for (int count = 0; count < mDatas.size(); count++) {
//            // 翻页指示的点
//            ImageView pointView = new ImageView(getContext());
//            pointView.setPadding(5, 0, 5, 0);
//            if (mPointViews.isEmpty())
//                pointView.setImageResource(page_indicatorId[1]);
//            else
//                pointView.setImageResource(page_indicatorId[0]);
//            mPointViews.add(pointView);
//            indicatorParent.addView(pointView);
//        }
//
//        PageIndicatorListener pageIndicatorListener = new DefaultPageIndicator(mPointViews, page_indicatorId);
//        pageIndicatorListener.onPageSelected(viewPager.getRealItem());
//
//        viewPager.addOnPageChangeListener(pageIndicatorListener);
        return this;
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
        scroller.setScrollDuration(duration);
        return this;
    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            scroller = new ViewPagerScroller(
                    viewPager.getContext());
            mScroller.set(viewPager, scroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
}
