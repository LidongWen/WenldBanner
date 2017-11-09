package com.wenld.wenldbanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.wenld.wenldbanner.adapter.WenldPagerAdapter;
import com.wenld.wenldbanner.helper.Holder;
import com.wenld.wenldbanner.helper.ViewPagerScroller;

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
        setRunning(true);
        setCanTurn(true);
    }

    public AutoTurnViewPager setPages(Holder<T> holer) {
        WenldPagerAdapter adapter = new WenldPagerAdapter(holer);
        initViewPagerScroll();
        adapter.setViewPager(this);
        setAdapter(adapter);
        return this;
    }

    public AutoTurnViewPager setmDatas(List<T> data) {
        getAdapter().setmDatas(data);
        return this;
    }

    protected AutoTurnViewPager setPages(Holder<T> holer, List<T> data) {
        WenldPagerAdapter adapter = new WenldPagerAdapter(holer);
        setmDatas(data);
        initViewPagerScroll();
        adapter.setViewPager(this);
        setAdapter(adapter);
        return this;
    }

    static class TurnRunnable implements Runnable {

        private final WeakReference<AutoTurnViewPager> reference;

        TurnRunnable(AutoTurnViewPager autoTurnViewPager) {
            this.reference = new WeakReference(autoTurnViewPager);
        }

        @Override
        public void run() {
            // 开始翻页
            AutoTurnViewPager autoTurnViewPager = reference.get();

            if (autoTurnViewPager != null) {
                if (autoTurnViewPager.isRunning() && autoTurnViewPager.isCanTurn()) {
                    int page = autoTurnViewPager.getCurrentItem() + 1;
                    if (autoTurnViewPager.getAdapter().getCount() <= page) {
                        autoTurnViewPager.setRunning(false);
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
        startTurn();
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

    public AutoTurnViewPager setRunning(boolean running) {
        isRunning = running;
        return this;
    }


    public AutoTurnViewPager setCanTurn(boolean canTurn) {
        if(this.canTurn==canTurn)
            return this;
        this.canTurn = canTurn;
        if(canTurn){
            startTurn();
        }else{
            stopTurning();
        }
        return this;
    }

    @Override
    public void setCanLoop(boolean canLoop) {
        if(!isRunning()){
            startTurn();
        }
        super.setCanLoop(canLoop);
    }

    public int getAutoTurnTime() {
        return autoTurnTime;
    }

    public AutoTurnViewPager setAutoTurnTime(int autoTurnTime) {
        this.autoTurnTime = autoTurnTime;
        return this;
    }

    public AutoTurnViewPager setScrollDuration(int duration) {
        scroller.setScrollDuration(duration);
        return this;
    }
}
