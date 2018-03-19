package com.github.biba.flashlang.ui.contract;

import android.database.Cursor;

import com.github.biba.flashlang.ui.presenter.BasePresenter;

public interface TargetLanguagesCollectionContract {

    interface View {

        String getSourceLanguageKey();

        void onLoaded(Cursor pCursor);

        void onError(String pErrorMessage);

    }

    interface Presenter extends BasePresenter<TargetLanguagesCollectionContract.View> {

        void load();
    }

}
