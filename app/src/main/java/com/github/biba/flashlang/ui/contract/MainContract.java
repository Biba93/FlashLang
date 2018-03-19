package com.github.biba.flashlang.ui.contract;

import com.github.biba.flashlang.ui.presenter.BasePresenter;

public interface MainContract {

    interface View {

    }

    interface Presenter extends BasePresenter<View> {

        void loadCurrentUser();
    }

}
