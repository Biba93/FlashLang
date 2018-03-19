package com.github.biba.flashlang.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class ScrollPagerAdapter extends FragmentStatePagerAdapter {

    private final List<String> mFragmentsNames;
    private final Fragment[] mFragments;
    private final Context mContext;

    public ScrollPagerAdapter(final Context pContext, final FragmentManager fm, final List<String> pFragmentsNames) {
        super(fm);
        mContext = pContext;
        mFragmentsNames = pFragmentsNames;
        mFragments = new Fragment[pFragmentsNames.size()];
    }

    @Override
    public Fragment getItem(final int position) {
        Fragment fragment = mFragments[position];
        if (fragment == null) {
            fragment = Fragment.instantiate(mContext, mFragmentsNames.get(position));
            mFragments[position] = fragment;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentsNames.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragments[position] = fragment;
        return fragment;
    }
}
