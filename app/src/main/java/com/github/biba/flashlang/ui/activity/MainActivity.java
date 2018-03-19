package com.github.biba.flashlang.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.ui.ViewConstants;
import com.github.biba.flashlang.ui.adapter.ScrollPagerAdapter;
import com.github.biba.flashlang.ui.contract.MainContract;
import com.github.biba.flashlang.ui.domain.ScrollPageListener;
import com.github.biba.flashlang.ui.fragment.main.TranslateFragment;
import com.github.biba.flashlang.ui.fragment.main.UserInfoFragment;
import com.github.biba.flashlang.ui.fragment.main.collection.BaseCollectionFragment;
import com.github.biba.flashlang.ui.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mPresenter == null) {
            mPresenter = new MainPresenter();
        }
        mPresenter.loadCurrentUser();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    private void init() {
        mViewPager = findViewById(R.id.activity_main_view_pager);
        initViewPager();
    }

    private void initViewPager() {
        final HorizontalScrollView scrollView = findViewById(R.id.background_scroll_view);
        final ImageView background = findViewById(R.id.background_image_view);

        final ScrollPagerAdapter adapter = new ScrollPagerAdapter(this, getSupportFragmentManager(), getFragmentsNames());
        final ViewPager.OnPageChangeListener scrollPageListener = new ScrollPageListener(mViewPager, scrollView, background);
        mViewPager.addOnPageChangeListener(scrollPageListener);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(ViewConstants.Main.VIEW_PAGER_OFFSCREEN_PAGE_LIMIT);
        mViewPager.setCurrentItem(ViewConstants.Main.TRANSLATE_FRAGMENT_POSITION);
    }

    private List<String> getFragmentsNames() {
        final List<String> names = new ArrayList<>();
        names.add(ViewConstants.Main.COLLECTION_FRAGMENT_POSITION, BaseCollectionFragment.class.getName());
        names.add(ViewConstants.Main.TRANSLATE_FRAGMENT_POSITION, TranslateFragment.class.getName());
        names.add(ViewConstants.Main.USER_INFO_FRAGMENT_POSITION, UserInfoFragment.class.getName());
        return names;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}

