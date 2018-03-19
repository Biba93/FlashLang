package com.github.biba.flashlang.ui.contract;

import com.github.biba.flashlang.ui.presenter.BasePresenter;

public interface AuthContract {

    interface View {

        void onAuthSuccess();

        void onAuthError(String pErrorMessage);

        interface IAuthInfoView {

            String getEmail();

            String getPassword();
        }
    }

    interface Presenter extends BasePresenter<View> {

        ISignInPresenter getSignInPresenter();

        ISignUpPresenter getSignUpPresenter();

        interface ISignInPresenter extends BasePresenter<View.IAuthInfoView> {

            void signIn();
        }

        interface ISignUpPresenter extends BasePresenter<View.IAuthInfoView> {

            void signUp();
        }
    }

}
