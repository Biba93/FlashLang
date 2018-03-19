package com.github.biba.flashlang.ui.fragment.main.collection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.domain.models.card.ICard;
import com.github.biba.flashlang.domain.models.collection.ICollection;
import com.github.biba.flashlang.ui.ViewConstants;
import com.github.biba.lib.logs.Log;

public class BaseCollectionFragment extends Fragment {

    private static final String LOG_TAG = BaseCollectionFragment.class.getSimpleName();

    FragmentManager mFragmentManager;
    private SourceLanguagesCollectionFragment mFragment;
    private int mCollectionContainer;
    private TargetLanguagesCollectionFragment mTargetLanguagesFragment;
    private CardCollectionFragment mCardCollectionFragment;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (mFragmentManager == null) {
            mFragmentManager = getChildFragmentManager();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, final Bundle pSavedInstanceState) {
        final ViewGroup rootView = (ViewGroup) pInflater.inflate(R.layout.fragment_collection_base, pContainer, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View pView, @Nullable final Bundle pSavedInstanceState) {
        super.onViewCreated(pView, pSavedInstanceState);
        mCollectionContainer = R.id.collection_container;
        if (pSavedInstanceState == null) {
            loadSourceLanguagesFragment();
        }
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume() called");
        super.onResume();
    }

    private void loadSourceLanguagesFragment() {
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mFragment == null) {
            mFragment = (SourceLanguagesCollectionFragment) mFragmentManager.findFragmentByTag(ViewConstants.Collection.SOURCE_COLLECTION_FRAGMENT_TAG);
            if (mFragment == null) {
                mFragment = new SourceLanguagesCollectionFragment();
                transaction.add(mFragment, ViewConstants.Collection.SOURCE_COLLECTION_FRAGMENT_TAG);
            }
        }
        mFragment.setChildFragmentListener(new IChildFragmentListener<ICollection>() {

            @Override
            public void onItemClick(final ICollection pElement) {
                Log.d(LOG_TAG, "onItemClick() called with: pElement = [" + pElement + "]");
                loadTargetLanguagesFragment(pElement);
            }

            @Override
            public void onBackClick() {

            }
        });

        //transaction.commitNowAllowingStateLoss();
        transaction.replace(mCollectionContainer, mFragment).commit();

    }

    private void loadTargetLanguagesFragment(final ICollection pSourceCollection) {
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mTargetLanguagesFragment == null) {
            mTargetLanguagesFragment = (TargetLanguagesCollectionFragment) mFragmentManager.findFragmentByTag(ViewConstants.Collection.TARGET_COLLECTION_FRAGMENT_TAG);
            if (mTargetLanguagesFragment == null) {
                mTargetLanguagesFragment = new TargetLanguagesCollectionFragment();
                transaction.add(mTargetLanguagesFragment, ViewConstants.Collection.TARGET_COLLECTION_FRAGMENT_TAG);
            }
        }
        final Bundle args = new Bundle();
        args.putString(ViewConstants.Collection.ARGS_SOURCE_LANGUAGE_KEY, pSourceCollection.getSourceLanguage());
        args.putString(ViewConstants.Collection.ARGS_SOURCE_COVER_KEY, pSourceCollection.getSourceLanguageCover());
        mTargetLanguagesFragment.setArguments(args);
        mTargetLanguagesFragment.setChildFragmentListener(new IChildFragmentListener<ICollection>() {

            @Override
            public void onItemClick(final ICollection pElement) {
                loadCardsCollectionFragment(pElement);
            }

            @Override
            public void onBackClick() {
                loadSourceLanguagesFragment();
            }
        });
        //transaction.commitNowAllowingStateLoss();
        transaction.replace(mCollectionContainer, mTargetLanguagesFragment).commit();
    }

    private void loadCardsCollectionFragment(final ICollection pCollection) {
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mCardCollectionFragment == null) {
            mCardCollectionFragment = (CardCollectionFragment) mFragmentManager.findFragmentByTag(ViewConstants.Collection.CARDS_COLLECTION_FRAGMENT_TAG);
            if (mCardCollectionFragment == null) {
                mCardCollectionFragment = new CardCollectionFragment();
                transaction.add(mCardCollectionFragment, ViewConstants.Collection.CARDS_COLLECTION_FRAGMENT_TAG);
            }
        }
        final Bundle args = new Bundle();
        args.putString(ViewConstants.Collection.ARGS_SOURCE_LANGUAGE_KEY, pCollection.getSourceLanguage());
        args.putString(ViewConstants.Collection.ARGS_SOURCE_COVER_KEY, pCollection.getSourceLanguageCover());
        args.putString(ViewConstants.Collection.ARGS_TARGET_LANGUAGE_KEY, pCollection.getTargetLanguage());
        args.putString(ViewConstants.Collection.ARGS_TARGET_COVER_KEY, pCollection.getTargetLanguageCover());
        mCardCollectionFragment.setArguments(args);
        mCardCollectionFragment.setChildFragmentListener(new IChildFragmentListener<ICard>() {

            @Override
            public void onItemClick(final ICard pElement) {
                Log.d(LOG_TAG, "onItemClick() called with: pElement = [" + pElement + "]");
                //todo
            }

            @Override
            public void onBackClick() {
                loadTargetLanguagesFragment(pCollection);
            }
        });
        //transaction.commitNowAllowingStateLoss();
        transaction.replace(mCollectionContainer, mCardCollectionFragment).commit();

    }

}
