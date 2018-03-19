package com.github.biba.flashlang.ui.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.card.ICard;
import com.github.biba.flashlang.ui.ViewConstants;
import com.github.biba.flashlang.ui.adapter.CardsRecyclerViewAdapter;
import com.github.biba.flashlang.ui.contract.GameContract;
import com.github.biba.flashlang.ui.domain.RecyclerClickListener;
import com.github.biba.flashlang.ui.presenter.GamePresenter;
import com.github.biba.lib.logs.Log;

import java.util.List;

public class GameActivity extends AppCompatActivity implements GameContract.View, RecyclerClickListener {

    private static final String LOG_TAG = GameActivity.class.getSimpleName();

    public static final String VISIBLE = "VISIBLE";
    public final String INVISIBLE = "invisible";
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private GameContract.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private CardsRecyclerViewAdapter mAdapter;
    private String mSourceLanguageKey;
    private String mTargetLanguageKey;

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        if (pSavedInstanceState != null) {
            final Bundle bundle = pSavedInstanceState.getBundle(ViewConstants.CardGame.ARGS);
            if (bundle != null) {
                extractDataFromArgs(bundle);
            }
        } else {
            mSourceLanguageKey = getIntent().getStringExtra(ViewConstants.CardGame.ARGS_SOURCE_LANGUAGE_KEY);
            mTargetLanguageKey = getIntent().getStringExtra(ViewConstants.CardGame.ARGS_TARGET_LANGUAGE_KEY);
        }

        setContentView(R.layout.activity_card_game);
        attachPresenter();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.load();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mPresenter;
    }

    private void attachPresenter() {
        mPresenter = (GameContract.Presenter) getLastCustomNonConfigurationInstance();
        if (mPresenter == null) {
            mPresenter = new GamePresenter();
        }
        mPresenter.attachView(this);
    }

    private void extractDataFromArgs(final Bundle pBundle) {
        if (pBundle != null) {
            mSourceLanguageKey = pBundle.getString(ViewConstants.CardGame.ARGS_SOURCE_LANGUAGE_KEY);
            mTargetLanguageKey = pBundle.getString(ViewConstants.CardGame.ARGS_TARGET_LANGUAGE_KEY);
        }
    }

    private void init() {
        mRecyclerView = findViewById(R.id.cards_recycler_view);
        initRecyclerView();
        loadAnimations();
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in);
    }

    private void initRecyclerView() {
        mAdapter = new CardsRecyclerViewAdapter();
        mAdapter.setRecyclerClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnFlingListener(null);
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
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
    public void onClick(final int pPosition) {
        final RecyclerView.ViewHolder viewHolderForAdapterPosition = mRecyclerView.findViewHolderForAdapterPosition(pPosition);
        animateView(viewHolderForAdapterPosition);

    }

    @Override
    public void onLoaded(final List<? extends ICard> pCards) {
        //noinspection unchecked
        mAdapter.setData((List<ICard>) pCards);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(final String pErrorMessage) {
        Log.d(LOG_TAG, "onError: " + pErrorMessage);

    }

    public void animateView(final RecyclerView.ViewHolder pViewHolder) {
        if (pViewHolder != null) {
            final View top = pViewHolder.itemView.findViewById(R.id.card_top);
            final View back = pViewHolder.itemView.findViewById(R.id.card_back);
            animateFlip(top, back);

        }
    }

    public void animateFlip(final View pTop, final View pBack) {
        if (pBack.getTag().toString().equals(INVISIBLE)) {
            mSetRightOut.setTarget(pTop);
            mSetLeftIn.setTarget(pBack);
            mSetRightOut.start();
            mSetLeftIn.start();
            pBack.setTag(VISIBLE);
        } else {
            mSetRightOut.setTarget(pBack);
            mSetLeftIn.setTarget(pTop);
            mSetRightOut.start();
            mSetLeftIn.start();
            pBack.setTag(INVISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    protected void onSaveInstanceState(final Bundle pOutState) {
        super.onSaveInstanceState(pOutState);
        final Bundle args = new Bundle();
        args.putString(ViewConstants.CardGame.ARGS_SOURCE_LANGUAGE_KEY, mSourceLanguageKey);
        args.putString(ViewConstants.CardGame.ARGS_TARGET_LANGUAGE_KEY, mTargetLanguageKey);
        pOutState.putBundle(ViewConstants.CardGame.ARGS, args);
    }
}
