package com.wenld.wenldbanner;

import android.content.Context;
import android.content.res.TypedArray;
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
 * blog: http://www.jianshu.com/u/99f514ea81b3
 * github: https://github.com/LidongWen
 * <p>
 * describe:
 * 控制是否执行自动翻页
 * 控制开始与结束
 * 翻页时间间隔
 */

public class AutoTurnViewPager<T> extends LoopViewPager {
    boolean isRunning; //是否正在执行翻页中   如果是canLoop 到头了 那就不翻页
    boolean canTurn;   //能否能执行自动翻页
    public int autoTurnTime = 5000;//间隔
    boolean reverse;

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
        initViewPagerScroll();

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

    public AutoTurnViewPager setPages(Holder<T> holer) {
        WenldPagerAdapter adapter = new WenldPagerAdapter(holer);
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
                    int page = autoTurnViewPager.getCurrentItem() + (autoTurnViewPager.isReverse() ? -1 : 1);
                    if (!autoTurnViewPager.getAdapter().isRealCanLoop() && (page >= autoTurnViewPager.getAdapter().getRealCount() || page < 0)) {
                        autoTurnViewPager.setRunning(false);
                        return;
                    }
                    autoTurnViewPager.setCurrentItem(page);
                    autoTurnViewPager.startTurn();
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
    protected void onDetachedFromWindow() {
        stopTurning();
        super.onDetachedFromWindow();
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

    private AutoTurnViewPager startTurn(int autoTurnTime) {
        stopTurning();
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
        if (this.canTurn == canTurn)
            return this;
        this.canTurn = canTurn;
        if (canTurn) {
            startTurn();
        } else {
            stopTurning();
        }
        return this;
    }

    @Override
    public void setCanLoop(boolean canLoop) {
        if (!isRunning()) {
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

    public int getScrollDuration() {
        return scroller.getScrollDuration();
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean isReverse() {
        return reverse;
    }
}
