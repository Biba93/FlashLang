package com.github.biba.flashlang.ui.domain;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.HorizontalScrollView;

public class ScrollPageListener implements ViewPager.OnPageChangeListener {

    private final HorizontalScrollView mScrollView;
    private final View mBackground;
    private final ViewPager mViewPager;

    public ScrollPageListener(final ViewPager pViewPager, final HorizontalScrollView pScrollView, final View pBackground) {
        mViewPager = pViewPager;
        mScrollView = pScrollView;
        mBackground = pBackground;
    }

    @Override
    public void onPageScrolled(final int pPosition, final float pPositionOffset, final int pPositionOffsetPixels) {
        final int x = (int) ((mViewPager.getWidth() * pPosition + pPositionOffsetPixels) * computeFactor());
        mScrollView.smoothScrollTo(x, 0);
    }

    @Override
    public void onPageSelected(final int pPosition) {

    }

    @Override
    public void onPageScrollStateChanged(final int pState) {

    }

    private float computeFactor() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        return (mBackground.getWidth() - mViewPager.getWidth()) /
                (float) (mViewPager.getWidth() * (adapter.getCount()));

    }
}
