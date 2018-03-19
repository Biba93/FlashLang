package com.github.biba.flashlang.ui.fragment.main;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.achievement.IAchievement;
import com.github.biba.flashlang.domain.models.user.IUser;
import com.github.biba.flashlang.ui.activity.AuthActivity;
import com.github.biba.flashlang.ui.contract.UserInfoContract;
import com.github.biba.flashlang.ui.presenter.UserInfoPresenter;
import com.github.biba.flashlang.utils.DrawableUtils;
import com.github.biba.lib.imageloader.ILouvre;
import com.github.biba.lib.logs.Log;

public class UserInfoFragment extends Fragment implements UserInfoContract.View, View.OnClickListener {

    private static final String LOG_TAG = UserInfoFragment.class.getSimpleName();

    private static final int NUMBER_ANIMATION_DURATION = 1000;
    private UserInfoContract.Presenter mPresenter;
    private View mView;
    private ImageView mUserImageView;
    private TextView mUserName;
    private TextView mWordCountTextView;
    private TextView mConnectionCountTextView;

    public UserInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (mPresenter == null) {
            mPresenter = new UserInfoPresenter();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getUser();
    }

    @Override
    public void setUserVisibleHint(final boolean pIsVisibleToUser) {
        super.setUserVisibleHint(pIsVisibleToUser);
        if (pIsVisibleToUser) {
            mPresenter.getAchievements();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
        final ViewGroup rootView = (ViewGroup) pInflater.inflate(R.layout.fragment_user_info, pContainer, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View pView, @Nullable final Bundle pSavedInstanceState) {
        super.onViewCreated(pView, pSavedInstanceState);
        mView = pView;
        init();
    }

    @Override
    public void onClick(final View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.logout_button:
                mPresenter.logout();
                finishActivity();
                break;
        }
    }

    private void init() {
        mUserImageView = mView.findViewById(R.id.user_picture_image_view);
        mUserImageView.setOnClickListener(this);
        mUserName = mView.findViewById(R.id.username_text_view);
        mConnectionCountTextView = mView.findViewById(R.id.total_connections_text_view);
        mWordCountTextView = mView.findViewById(R.id.total_words_text_view);
        mView.findViewById(R.id.logout_button).setOnClickListener(this);
    }

    @Override
    public Drawable getImage() {
        //todo
        return null;
    }

    @Override
    public void onUserLoaded(final IUser pUser) {
        loadUserPicture(pUser.getPictureUrl());
        mUserName.setText(pUser.getName());
    }

    @Override
    public void onUserLoadError(final String pErrorMessage) {
        Log.e(LOG_TAG, "onUserLoadError: " + pErrorMessage);
    }

    @Override
    public void onAchievementLoaded(final IAchievement pAchievement) {
        startCountAnimation(mConnectionCountTextView, pAchievement.getTotalConnections());
        startCountAnimation(mWordCountTextView, pAchievement.getTotalWords());
    }

    private void finishActivity() {
        final FragmentActivity activity = getActivity();
        if (activity != null) {
            final Intent intent = new Intent(this.getContext(), AuthActivity.class);
            startActivity(intent);
            activity.finish();
        }

    }

    private void loadUserPicture(final String pUrl) {

        try {
            ILouvre.Impl.getInstance().newRequest()
                    .from(pUrl)
                    .to(mUserImageView)
                    .saved(true)
                    .setErrorImage(DrawableUtils.bitmapFromDrawable(R.drawable.monkey))
                    .load();
        } catch (final Exception pE) {
            Log.e(LOG_TAG, "loadUserPicture: ", pE);
        }

    }

    private void startCountAnimation(final TextView pTargetTextView, final long finalCount) {
        final ValueAnimator animator = ValueAnimator.ofInt(0, (int) finalCount);
        animator.setDuration(NUMBER_ANIMATION_DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            public void onAnimationUpdate(final ValueAnimator animation) {
                pTargetTextView.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
}
