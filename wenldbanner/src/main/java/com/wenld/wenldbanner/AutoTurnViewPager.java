package com.wenld.wenldbanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <p/>
 * Author: 温利东 on 2017/11/6 13:49.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class AutoTurnViewPager<T> extends LoopViewPager {
    boolean isRunning; //是否正在执行翻页中   如果是canLoop 到头了 那就不翻页
    boolean canTurn;   //能否能执行自动翻页
    public int autoTurnTime = 5000;//间隔

    ViewPagerScroller scroller;

    public AutoTurnViewPager.TurnRunnable turnRunnable;

    public AutoTurnViewPager(Context context) {
        this(context, null);
    }

    public AutoTurnViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        turnRunnable = new AutoTurnViewPager.TurnRunnable(this);
    }

    public AutoTurnViewPager setPages(Holder<T> holer, List<T> data) {
        WenldPagerAdapter adapter = new WenldPagerAdapter(holer, data);
        initViewPagerScroll();
        adapter.setViewPager(this);
        setAdapter(adapter);
        return this;
    }

    static class TurnRunnable implements Runnable {

        private final WeakReference<AutoTurnViewPager> reference;

        TurnRunnable(AutoTurnViewPager autoTurnViewPager) {
            this.reference = new WeakReference<AutoTurnViewPager>(autoTurnViewPager);
        }

        @Override
        public void run() {
            // 开始翻页
            AutoTurnViewPager autoTurnViewPager = reference.get();

            if (autoTurnViewPager != null) {
                if (autoTurnViewPager.isRunning() && autoTurnViewPager.isCanTurn()) {
                    int page = autoTurnViewPager.getCurrentItem() + 1;
                    if (autoTurnViewPager.getAdapter().getCount() <= page) {
                        return;
                    }
                    autoTurnViewPager.setCurrentItem(page);
                    autoTurnViewPager.postDelayed(autoTurnViewPager.turnRunnable, autoTurnViewPager.autoTurnTime);
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        postDelayed(turnRunnable, autoTurnTime);
    }

    /**
     * 自定义翻页动画效果
     *
     * @param transformer
     * @return
     */
    public AutoTurnViewPager setPageTransformer(ViewPager.PageTransformer transformer) {
        setPageTransformer(true, transformer);
        return this;
    }

    public boolean isCanTurn() {
        return canTurn;
    }

    public AutoTurnViewPager startTurn(int autoTurnTime) {
        setRunning(true);
        setAutoTurnTime(autoTurnTime);
        postDelayed(turnRunnable, this.autoTurnTime);
        return this;
    }

    public AutoTurnViewPager startTurn() {
        startTurn(autoTurnTime);
        return this;
    }

    public void stopTurning() {
        //关闭翻页
        setRunning(false);
        removeCallbacks(turnRunnable);
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
                    getContext());
            mScroller.set(this, scroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
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

    public AutoTurnViewPager setScrollDuration(int duration) {
        scroller.setScrollDuration(duration);
        return this;
    }
}
