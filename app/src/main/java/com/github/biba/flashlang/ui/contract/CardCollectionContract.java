package com.github.biba.flashlang.ui.contract;

import android.database.Cursor;

import com.github.biba.flashlang.ui.presenter.BasePresenter;

public interface CardCollectionContract {

    interface View {

        String getSourceLanguageKey();

        String getTargetLanguageKey();

        void onLoaded(Cursor pCursor);

        void onError(String pErrorMessage);
    }

    interface Presenter extends BasePresenter<CardCollectionContract.View> {

        void load();

    }

}
