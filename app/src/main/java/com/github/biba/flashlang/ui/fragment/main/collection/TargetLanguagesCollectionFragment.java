package com.github.biba.flashlang.ui.fragment.main.collection;

import android.content.res.Configuration;
import android.database.Cursor;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.collection.ICollection;
import com.github.biba.flashlang.ui.ViewConstants;
import com.github.biba.flashlang.ui.adapter.LanguageItemType;
import com.github.biba.flashlang.ui.adapter.LanguagesRecyclerViewCursorAdapter;
import com.github.biba.flashlang.ui.contract.TargetLanguagesCollectionContract;
import com.github.biba.flashlang.ui.domain.IRecycleClickCallback;
import com.github.biba.flashlang.ui.presenter.TargetLanguagesCollectionPresenter;
import com.github.biba.flashlang.utils.DrawableUtils;
import com.github.biba.flashlang.utils.ResourceUtils;
import com.github.biba.flashlang.utils.SystemConfigUtils;
import com.github.biba.lib.imageloader.ILouvre;
import com.github.biba.lib.logs.Log;

public class TargetLanguagesCollectionFragment extends Fragment implements TargetLanguagesCollectionContract.View, View.OnClickListener {

    private static final String LOG_TAG = TargetLanguagesCollectionFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TargetLanguagesCollectionContract.Presenter mPresenter;
    private View mView;
    private ImageView mSourceLanguageImageView;
    private String mSourceLanguageImage;
    private RecyclerView mRecyclerView;
    private LanguagesRecyclerViewCursorAdapter mAdapter;
    private IChildFragmentListener<ICollection> mListener;
    private String mSourceLanguageKey;
    private Bundle mArgs;

    public TargetLanguagesCollectionFragment() {
    }

    public void setChildFragmentListener(final IChildFragmentListener<ICollection> pListener) {
        mListener = pListener;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (mPresenter == null) {
            mPresenter = new TargetLanguagesCollectionPresenter();
        }
        if (mArgs == null && savedInstanceState != null) {
            savedInstanceState.getBundle(ViewConstants.Collection.ARGS);
        } else {
            mArgs = getArguments();
        }
        if (mArgs != null) {
            mSourceLanguageKey = mArgs.getString(ViewConstants.Collection.ARGS_SOURCE_LANGUAGE_KEY);
            mSourceLanguageImage = mArgs.getString(ViewConstants.Collection.ARGS_SOURCE_COVER_KEY);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart() called");
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
        final ViewGroup rootView = (ViewGroup) pInflater.inflate(R.layout.fragment_target_languages, pContainer, false);
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
            case R.id.source_language_image_view:
                mListener.onBackClick();
        }
    }

    private void init() {
        mSwipeRefreshLayout = mView.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = mView.findViewById(R.id.card_collection_recycler_view);
        mSourceLanguageImageView = mView.findViewById(R.id.source_language_image_view);
        mSourceLanguageImageView.setOnClickListener(this);
        loadSourceImage();
        initSwipeRefresh();
    }

    private void loadSourceImage() {
        try {
            ILouvre.Impl.getInstance()
                    .newRequest()
                    .to(mSourceLanguageImageView)
                    .from(mSourceLanguageImage)
                    .setErrorImage(DrawableUtils.bitmapFromDrawable(R.drawable.moon))
                    .load();
        } catch (final Exception ignored) {
        }
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
        mAdapter = new LanguagesRecyclerViewCursorAdapter(new IRecycleClickCallback<ICollection>() {

            @Override
            public void onClick(final ICollection pElement) {
                mListener.onItemClick(pElement);
            }
        });
        mAdapter.setLanguageItemType(LanguageItemType.TARGET);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(getOrientation());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.load();

    }

    @Override
    public String getSourceLanguageKey() {
        return mSourceLanguageKey;
    }

    @Override
    public void onLoaded(final Cursor pCursor) {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.setCursor(pCursor);
    }

    @Override
    public void onError(final String pErrorMessage) {
        mSwipeRefreshLayout.setRefreshing(false);
        showError(ResourceUtils.getString(R.string.cant_load_data));
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

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(ViewConstants.Collection.ARGS, mArgs);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAdapter.release();
    }

    @Override
    public void onDestroy() {
        mAdapter.release();
        super.onDestroy();
    }

    private void showError(final CharSequence pMessage) {
        Toast.makeText(this.getContext(), pMessage, Toast.LENGTH_SHORT)
                .show();
    }
}
