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

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by wenld on 2017/11/1.
 */

public class WenldBanner extends FrameLayout {
    WenldViewPager viewPager;
    ViewHolder indicatorViewHolder;

    private ViewPagerScroller scroller;

    boolean isRunning; //是否正在执行翻页中   如果是canLoop 到头了 那就不翻页
    boolean canLoop;// 是否循环
    boolean canTurn;   //能否能执行自动翻页

    public int autoTurnTime = 5000;//间隔

    public TurnRunnable turnRunnable;

    /**
     * 设置动画时长
     * 设置翻页动画
     * 控制UI位置
     * 控制触摸
     * <p>
     * 监听ViewPager状态变化
     */


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
        viewPager = (WenldViewPager) hView.findViewById(R.id.vp_wenld_banner);
        indicatorViewHolder = new ViewHolder(getContext(), hView.findViewById(R.id.indicator_wenld_banner));
        initViewPagerScroll();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //指示器

            }

            @Override
            public void onPageSelected(int position) {
                // 指示器
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        isRunning = true; //是否正在执行翻页中   如果是canLoop 到头了 那就不翻页
        canLoop = true;// 是否循环
        canTurn = true;   //能否能执行自动翻页

        turnRunnable = new TurnRunnable(this);
        postDelayed(turnRunnable,autoTurnTime);
    }

    public <T> WenldBanner setPages(Holder<T> holer, List<T> data) {
        WenldPagerAdapter adapter = new WenldPagerAdapter(holer, data);
        adapter.setViewPager(viewPager);
        viewPager.setAdapter(adapter,canLoop);
        return this;
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

    private void initAttr(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.wenldBanner);
        if (a != null) {
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.wenldBanner_canLoop) {
                    canLoop = a.getBoolean(attr, true);
                }
            }
            a.recycle();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            startTurn(autoTurnTime);
        } else if (action == MotionEvent.ACTION_DOWN) {
            stopTurning();
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isCanTurn() {
        return canTurn;
    }

    public WenldBanner startTurn(int autoTurnTime) {
        setRunning(true);
        setAutoTurnTime(autoTurnTime);
        postDelayed(turnRunnable, this.autoTurnTime);
        return this;
    }

    public void stopTurning() {
        //关闭翻页
        setRunning(false);
        removeCallbacks(turnRunnable);
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

    static class TurnRunnable implements Runnable {

        private final WeakReference<WenldBanner> reference;

        TurnRunnable(WenldBanner convenientBanner) {
            this.reference = new WeakReference<WenldBanner>(convenientBanner);
        }

        @Override
        public void run() {
            // 开始翻页
            WenldBanner wenldBanner = reference.get();

            if (wenldBanner != null) {
                if (wenldBanner.viewPager != null && wenldBanner.isRunning()&&wenldBanner.isCanTurn()) {
                    int page = wenldBanner.viewPager.getCurrentItem() + 1;
                    wenldBanner.viewPager.setCurrentItem(page);
                    wenldBanner.postDelayed(wenldBanner.turnRunnable, wenldBanner.autoTurnTime);
                }
            }
        }
    }

    public void setCurrentItem(int page){
        viewPager.setCurrentItem(page);
        stopTurning();
        startTurn(getAutoTurnTime());

    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setCanTurn(boolean canTurn) {
        this.canTurn = canTurn;
    }

    public int getAutoTurnTime() {
        return autoTurnTime;
    }

    public void setAutoTurnTime(int autoTurnTime) {
        this.autoTurnTime = autoTurnTime;
    }
}
