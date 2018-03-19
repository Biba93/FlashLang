package com.github.biba.flashlang.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.ui.ViewConstants;
import com.github.biba.flashlang.ui.adapter.ScrollPagerAdapter;
import com.github.biba.flashlang.ui.animator.AnimatorFactory;
import com.github.biba.flashlang.ui.contract.AuthContract;
import com.github.biba.flashlang.ui.domain.ScrollPageListener;
import com.github.biba.flashlang.ui.fragment.auth.SignInFragment;
import com.github.biba.flashlang.ui.fragment.auth.SignUpFragment;
import com.github.biba.flashlang.ui.presenter.AuthPresenter;

import java.util.ArrayList;
import java.util.List;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener, com.github.biba.flashlang.ui.contract.AuthContract.View {

    public static final int FLY_AWAY_ANIM_DURATION = 3000;
    public static final int ERROR_ANIM_DURATION = 1000;
    private AuthContract.Presenter mPresenter;
    private View mSpaceShipView;
    private ViewPager mViewPager;
    private ScrollPagerAdapter mAdapter;
    private Animator mBaseAnimator;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
        attachMainPresenter();
        if (savedInstanceState == null) {
            attachFragmentPresenters();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        detachMainPresenter();
        super.onDestroy();
    }

    @Override
    public void onClick(final View pView) {
        final int id = pView.getId();
        switch (id) {
            case R.id.textView_signin:
                mViewPager.setCurrentItem(ViewConstants.Auth.SIGN_IN_POSITION);
                break;
            case R.id.textView_signup:
                mViewPager.setCurrentItem(ViewConstants.Auth.SIGN_UP_POSITION);
                break;
        }

    }

    private void init() {
        mSpaceShipView = findViewById(R.id.spaceship_image_view);
        mViewPager = findViewById(R.id.activity_auth_view_pager);

        initViewPager();
        initAnimation();
    }

    private void initViewPager() {

        final HorizontalScrollView scrollView = findViewById(R.id.background_scroll_view);
        final ImageView background = findViewById(R.id.background_image_view);

        mAdapter = new ScrollPagerAdapter(this, getSupportFragmentManager(), getFragmentsNames());
        final ViewPager.OnPageChangeListener scrollPageListener = new ScrollPageListener(mViewPager, scrollView, background);
        mViewPager.addOnPageChangeListener(scrollPageListener);
        mViewPager.setAdapter(mAdapter);
    }

    private void initAnimation() {
        startBaseAnimation();
    }

    private List<String> getFragmentsNames() {
        final List<String> names = new ArrayList<>(ViewConstants.Auth.FRAGMENT_COUNT);
        names.add(ViewConstants.Auth.SIGN_IN_POSITION, SignInFragment.class.getName());
        names.add(ViewConstants.Auth.SIGN_UP_POSITION, SignUpFragment.class.getName());
        return names;
    }

    private void attachMainPresenter() {
        mPresenter = (AuthContract.Presenter) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new AuthPresenter();
        }
        mPresenter.attachView(this);
    }

    private void attachFragmentPresenters() {
        attachSignInPresenter();
        attachSignUpPresenter();
    }

    private void attachSignInPresenter() {
        final SignInFragment signInFragment = (SignInFragment) mAdapter.getItem(ViewConstants.Auth.SIGN_IN_POSITION);
        final AuthContract.Presenter.ISignInPresenter signInPresenter = mPresenter.getSignInPresenter();
        signInFragment.attachPresenter(signInPresenter);
        signInPresenter.attachView(signInFragment);
    }

    private void attachSignUpPresenter() {
        final SignUpFragment signUpFragment = (SignUpFragment) mAdapter.getItem(ViewConstants.Auth.SIGN_UP_POSITION);
        final AuthContract.Presenter.ISignUpPresenter signUpPresenter = mPresenter.getSignUpPresenter();
        signUpFragment.attachPresenter(signUpPresenter);
        signUpPresenter.attachView(signUpFragment);
    }

    private void detachMainPresenter() {
        mPresenter.detachView();
    }

    @Override
    public void onAuthSuccess() {
        applySuccessAnimation();
    }

    @Override
    public void onAuthError(final String pErrorMessage) {
        applyErrorAnimation();
        Toast.makeText(this, pErrorMessage, Toast.LENGTH_LONG).show();
    }

    private void startMainActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void applyErrorAnimation() {
        pauseBaseAnimation();
        final Animator errorAnimator = AnimatorInflater.loadAnimator(this, R.animator.jump_and_blink);
        errorAnimator.setTarget(mSpaceShipView);
        errorAnimator.setDuration(ERROR_ANIM_DURATION);
        errorAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                startBaseAnimation();
            }

            @Override
            public void onAnimationCancel(final Animator animation) {

            }

            @Override
            public void onAnimationRepeat(final Animator animation) {

            }
        });
        errorAnimator.start();
    }

    private void applySuccessAnimation() {
        final Animator animator = AnimatorFactory.createFlyAwayAnimator(mSpaceShipView);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(final Animator animation) {
                pauseBaseAnimation();
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                startMainActivity();
            }

            @Override
            public void onAnimationCancel(final Animator animation) {

            }

            @Override
            public void onAnimationRepeat(final Animator animation) {

            }
        });
        animator.setDuration(FLY_AWAY_ANIM_DURATION);
        animator.start();
    }

    private void startBaseAnimation() {
        mBaseAnimator = AnimatorInflater.loadAnimator(this, R.animator.bounce);
        mBaseAnimator.setTarget(mSpaceShipView);
        mBaseAnimator.start();
    }

    private void pauseBaseAnimation() {
        if (mBaseAnimator != null) {
            mBaseAnimator.end();
        }
    }

}


