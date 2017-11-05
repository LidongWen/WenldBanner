package com.wenld.wenldbanner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <p/>
 * Author: 温利东 on 2017/11/2 16:38.
 * blog: http://blog.csdn.net/sinat_15877283
 * github: https://github.com/LidongWen
 */

public class WenldPagerAdapter<T> extends PagerAdapter {
    protected List<T> mDatas;
    Holder holderCreator;
    private boolean canLoop = true;
    WenldViewPager wenldViewPager;


    @Override
    public int getCount() {
        return canLoop ? (getRealCount() > 1 ? getRealCount() + 2 : getRealCount()) : getRealCount();
    }

    public int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //拿到真正的位置  并创建 view 加入container
        position = toRealDataPosition(position);
        View view = getView(position, null, container);
        container.addView(view);
        return view;
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        //切换完后判断真正的position  并无缝替换
        int position = wenldViewPager.getCurrentItem();

        position = changedAdapterPostiton(position);

        try {
            wenldViewPager.setCurrentItem(position, false);
        } catch (IllegalStateException e) {

        }
    }

    int changedAdapterPostiton(int position) {

        if (canLoop && getRealCount() > 1) {
            if (position == 0) {
                return getRealCount();
            } else if (position == getCount() - 1) {
                return 1;
            }
        }
        return position;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
    public void setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
    }

    public void setViewPager(WenldViewPager viewPager) {
        this.wenldViewPager = viewPager;
    }

    public WenldPagerAdapter(Holder holderCreator, List<T> datas) {
        this.holderCreator = holderCreator;
        this.mDatas = datas;
    }

    /**
     * 返回真实数据的下标
     *
     * @param position
     * @return
     */
    public int toRealDataPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;

        if (canLoop) {
            // 如果只有一个数据
            if (realCount == 1) {
                return 0;
            } else {
                // 第0个即最后一个
                if (position == 0) {
                    return realCount - 1;
                    // 最后一个 即第0个数据
                } else if (position == getCount() - 1) {
                    return 0;
                } else {
                    return position - 1;
                }
            }
        }
        int realPosition = position % realCount;
        return realPosition;
    }
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder = null;
        if (view == null) {
            holder = holderCreator.createView(wenldViewPager.getContext(), container);
            view = holder.getConvertView();
            view.setTag(R.id.tag, holder);
        } else {
            holder = (ViewHolder) view.getTag(R.id.tag);
        }
        if (mDatas != null && !mDatas.isEmpty())
            holderCreator.UpdateUI(container.getContext(), holder, position, mDatas.get(position));
        return view;
    }

}
