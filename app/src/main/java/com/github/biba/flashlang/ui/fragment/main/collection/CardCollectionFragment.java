package com.github.biba.flashlang.ui.fragment.main.collection;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.card.ICard;
import com.github.biba.flashlang.ui.ViewConstants;
import com.github.biba.flashlang.ui.activity.GameActivity;
import com.github.biba.flashlang.ui.adapter.CardRowRecyclerViewCursorAdapter;
import com.github.biba.flashlang.ui.contract.CardCollectionContract;
import com.github.biba.flashlang.ui.domain.IRecycleClickCallback;
import com.github.biba.flashlang.ui.presenter.CardCollectionPresenter;
import com.github.biba.flashlang.utils.DrawableUtils;
import com.github.biba.flashlang.utils.ResourceUtils;
import com.github.biba.flashlang.utils.SystemConfigUtils;
import com.github.biba.lib.imageloader.ILouvre;
import com.github.biba.lib.logs.Log;

public class CardCollectionFragment extends Fragment implements CardCollectionContract.View, View.OnClickListener {

    private static final String LOG_TAG = CardCollectionFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private CardCollectionContract.Presenter mPresenter;
    private IChildFragmentListener<ICard> mListener;
    private View mView;
    private String mSourceLanguageKey;
    private String mSourceLanguageImage;
    private String mTargetLanguageKey;
    private String mTargetLanguageImage;
    private Bundle mArgs;

    private ImageView mSourceLanguageImageView;
    private ImageView mTargetLanguageImageView;
    private View mPlayTextView;
    private CardRowRecyclerViewCursorAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public CardCollectionFragment() {
    }

    public void setChildFragmentListener(final IChildFragmentListener<ICard> pListener) {
        mListener = pListener;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (mPresenter == null) {
            mPresenter = new CardCollectionPresenter();
        }
        if (mArgs == null && savedInstanceState != null) {
            savedInstanceState.getBundle(ViewConstants.Collection.ARGS);
        } else {
            mArgs = getArguments();
        }
        if (mArgs != null) {
            mSourceLanguageKey = mArgs.getString(ViewConstants.Collection.ARGS_SOURCE_LANGUAGE_KEY);
            mTargetLanguageKey = mArgs.getString(ViewConstants.Collection.ARGS_TARGET_LANGUAGE_KEY);
            mSourceLanguageImage = mArgs.getString(ViewConstants.Collection.ARGS_SOURCE_COVER_KEY);
            mTargetLanguageImage = mArgs.getString(ViewConstants.Collection.ARGS_TARGET_COVER_KEY);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
        initRecyclerView();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    public void setUserVisibleHint(final boolean pIsVisibleToUser) {
        super.setUserVisibleHint(pIsVisibleToUser);
        if (pIsVisibleToUser) {
            mPresenter.load();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
        final ViewGroup rootView = (ViewGroup) pInflater.inflate(R.layout.fragment_card_collection, pContainer, false);
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
            case R.id.target_language_image_view:
                mListener.onBackClick();
                break;
            case R.id.play_text_view:
                startGameActivity();
        }

    }

    private void init() {
        mSwipeRefreshLayout = mView.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = mView.findViewById(R.id.card_collection_recycler_view);
        mSourceLanguageImageView = mView.findViewById(R.id.source_language_image_view);
        mSourceLanguageImageView.setOnClickListener(this);
        mTargetLanguageImageView = mView.findViewById(R.id.target_language_image_view);
        mTargetLanguageImageView.setOnClickListener(this);
        mPlayTextView = mView.findViewById(R.id.play_text_view);
        mPlayTextView.setOnClickListener(this);
        loadImages();
        initAnimation();
        initSwipeRefresh();
    }

    private void loadImages() {
        final Bitmap mErrorImage = DrawableUtils.bitmapFromDrawable(R.drawable.moon);
        final ILouvre louvre = ILouvre.Impl.getInstance();
        try {
            louvre.newRequest().from(mSourceLanguageImage)
                    .to(mSourceLanguageImageView)
                    .setErrorImage(mErrorImage)
                    .load();
            louvre.newRequest().from(mTargetLanguageImage)
                    .to(mTargetLanguageImageView)
                    .setErrorImage(mErrorImage)
                    .load();
        } catch (final Exception pE) {
            Log.e(LOG_TAG, "loadImages: ", pE);
        }

    }

    private void initAnimation() {
        final Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.pulse);
        mPlayTextView.startAnimation(animation);
    }

    private void initSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mPresenter.load();
            }
        });
    }

    private void initRecyclerView() {
        mAdapter = new CardRowRecyclerViewCursorAdapter(new IRecycleClickCallback<ICard>() {

            @Override
            public void onClick(final ICard pElement) {
                mListener.onItemClick(pElement);
            }
        });
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(getOrientation());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this.getContext(), R.anim.layout_fall_down);
        mRecyclerView.setLayoutAnimation(animation);
        mPresenter.load();

    }

    @Override
    public String getSourceLanguageKey() {
        return mSourceLanguageKey;
    }

    @Override
    public String getTargetLanguageKey() {
        return mTargetLanguageKey;
    }

    @Override
    public void onLoaded(final Cursor pCursor) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setCursor(pCursor);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(final String pErrorMessage) {
        mSwipeRefreshLayout.setRefreshing(false);
        showError(ResourceUtils.getString(R.string.cant_load_data));
        Log.e(LOG_TAG, "onError: " + pErrorMessage);
    }

    private void startGameActivity() {
        final Intent intent = new Intent(this.getContext(), GameActivity.class);
        intent.putExtra(ViewConstants.CardGame.ARGS_SOURCE_LANGUAGE_KEY, mSourceLanguageKey);
        intent.putExtra(ViewConstants.CardGame.ARGS_TARGET_LANGUAGE_KEY, mTargetLanguageKey);

        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(final Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        pOutState.putBundle(ViewConstants.Collection.ARGS, mArgs);
    }

    public int getOrientation() {
        final int orientation = SystemConfigUtils.gerOrientation();
        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                return LinearLayoutManager.VERTICAL;
            case Configuration.ORIENTATION_LANDSCAPE:
                return LinearLayoutManager.HORIZONTAL;
            default:
                return LinearLayoutManager.VERTICAL;
        }

    }

    private void showError(final CharSequence pMessage) {
        Toast.makeText(this.getContext(), pMessage, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDestroy() {
        mAdapter.release();
        super.onDestroy();
    }

}
