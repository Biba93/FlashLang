package com.github.biba.flashlang.ui.contract;

import android.database.Cursor;

import com.github.biba.flashlang.ui.presenter.BasePresenter;

public interface SourceLanguagesCollectionContract {

    interface View {

        void onLoaded(Cursor pCursor);

        void onError(String pErrorMessage);
    }

    interface Presenter extends BasePresenter<SourceLanguagesCollectionContract.View> {

        void load();
    }

}
