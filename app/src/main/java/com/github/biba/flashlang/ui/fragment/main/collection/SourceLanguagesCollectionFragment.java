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
import android.widget.Toast;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.collection.ICollection;
import com.github.biba.flashlang.ui.adapter.LanguageItemType;
import com.github.biba.flashlang.ui.adapter.LanguagesRecyclerViewCursorAdapter;
import com.github.biba.flashlang.ui.contract.SourceLanguagesCollectionContract;
import com.github.biba.flashlang.ui.domain.IRecycleClickCallback;
import com.github.biba.flashlang.ui.presenter.SourceLanguagesCollectionPresenter;
import com.github.biba.flashlang.utils.ResourceUtils;
import com.github.biba.flashlang.utils.SystemConfigUtils;
import com.github.biba.lib.logs.Log;

public class SourceLanguagesCollectionFragment extends Fragment implements SourceLanguagesCollectionContract.View {

    private static final String LOG_TAG = SourceLanguagesCollectionFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SourceLanguagesCollectionContract.Presenter mPresenter;
    private View mView;
    private RecyclerView mRecyclerView;
    private LanguagesRecyclerViewCursorAdapter mAdapter;
    private IChildFragmentListener<ICollection> mListener;

    public SourceLanguagesCollectionFragment() {
    }

    public void setChildFragmentListener(final IChildFragmentListener<ICollection> pListener) {
        mListener = pListener;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (mPresenter == null) {
            Log.d(LOG_TAG, "onCreate: presenter is null");
            mPresenter = new SourceLanguagesCollectionPresenter();
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
    public void setUserVisibleHint(final boolean pIsVisibleToUser) {
        super.setUserVisibleHint(pIsVisibleToUser);
        if (pIsVisibleToUser) {
            mPresenter.load();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
        final ViewGroup rootView = (ViewGroup) pInflater.inflate(R.layout.fragment_source_languages, pContainer, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View pView, @Nullable final Bundle pSavedInstanceState) {
        super.onViewCreated(pView, pSavedInstanceState);
        mView = pView;
        init();
    }

    private void init() {
        mRecyclerView = mView.findViewById(R.id.card_collection_recycler_view);
        mSwipeRefreshLayout = mView.findViewById(R.id.swipe_refresh_layout);
        initSwipeRefresh();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new LanguagesRecyclerViewCursorAdapter(new IRecycleClickCallback<ICollection>() {

            @Override
            public void onClick(final ICollection pElement) {
                mListener.onItemClick(pElement);
            }
        });
        mAdapter.setLanguageItemType(LanguageItemType.SOURCE);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(getOrientation());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.load();
    }

    private void initSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mPresenter.load();
            }
        });

    }

    @Override
    public void onLoaded(final Cursor pCursor) {
        Log.d(LOG_TAG, "onLoaded() called with: pCursor = [" + pCursor + "]");
        mAdapter.setCursor(pCursor);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(final String pErrorMessage) {
        mSwipeRefreshLayout.setRefreshing(false);
        showError(ResourceUtils.getString(R.string.cant_load_data));
        Log.d(LOG_TAG, "onError() called with: pErrorMessage = [" + pErrorMessage + "]");
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
    public void onDestroy() {
        mAdapter.release();
        super.onDestroy();
    }

    private void showError(final CharSequence pMessage) {
        Toast.makeText(this.getContext(), pMessage, Toast.LENGTH_SHORT)
                .show();
    }

}
