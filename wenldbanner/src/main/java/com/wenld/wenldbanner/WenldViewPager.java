package com.wenld.wenldbanner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <p/>
 * Author: 温利东 on 2017/11/2 16:34.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class WenldViewPager extends ViewPager {
    OnPageChangeListener mOuterPageChangeListener;
    private OnItemClickListener onItemClickListener;
    private WenldPagerAdapter mAdapter;

    private boolean isCanScroll = true;
    private boolean canLoop = true;

    public void setAdapter(PagerAdapter adapter, boolean canLoop) {
        mAdapter = (WenldPagerAdapter) adapter;
        this.canLoop=canLoop;
        mAdapter.setCanLoop(canLoop);
        mAdapter.setViewPager(this);
        super.setAdapter(mAdapter);

        setCurrentItem(getFristItem(), false);
    }

    public int getFristItem() {
        return canLoop ? 1 : 0;
    }

    public int getLastItem() {
        return mAdapter.getRealCount() - 1;
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    private float oldX = 0, newX = 0;
    private static final float sens = 5;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            if (onItemClickListener != null) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        newX = ev.getX();
                        if (Math.abs(oldX - newX) < sens) {
                            onItemClickListener.onItemClick(getRealItem());
                        }
                        oldX = 0;
                        newX = 0;
                        break;
                }
            }
            return super.onTouchEvent(ev);
        } else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    public WenldPagerAdapter getAdapter() {
        return mAdapter;
    }
    public int getRealItem() {
        return getRealItem(super.getCurrentItem());
    }

    public int getRealItem(int position) {
        return mAdapter != null ? mAdapter.toRealDataPosition(position) : 0;
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOuterPageChangeListener = listener;
    }


    public WenldViewPager(Context context) {
        super(context);
        init();
    }

    public WenldViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnPageChangeListener(onPageChangeListener);
    }

    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = getRealItem(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;

//            if (mOuterPageChangeListener != null) {
//                if (realPosition != mAdapter.getRealCount() - 1) {
//                    mOuterPageChangeListener.onPageScrolled(realPosition,
//                            positionOffset, positionOffsetPixels);
//                } else {
//                    if (positionOffset > .5) {
//                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
//                    } else {
//                        mOuterPageChangeListener.onPageScrolled(realPosition,
//                                0, 0);
//                    }
//                }
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

    public boolean isCanLoop() {
        return canLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        if (canLoop == false) {
            setCurrentItem(getRealItem(), false);
        }
        if (mAdapter == null) return;
        mAdapter.setCanLoop(canLoop);
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }
}
