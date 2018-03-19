package com.github.biba.flashlang.ui.fragment.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.ui.contract.AuthContract;

public class SignInFragment extends BaseAuthFragment implements View.OnClickListener {

    private AuthContract.Presenter.ISignInPresenter mPresenter;

    public SignInFragment() {
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        mPresenter.detachView();
        super.onStop();
    }

    public void attachPresenter(final AuthContract.Presenter.ISignInPresenter pPresenter) {
        mPresenter = pPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater pInflater, @Nullable final ViewGroup pContainer, @Nullable final Bundle pSavedInstanceState) {
        final ViewGroup rootView = (ViewGroup) pInflater.inflate(R.layout.fragment_signin, pContainer, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View pView, @Nullable final Bundle pSavedInstanceState) {
        super.onViewCreated(pView, pSavedInstanceState);
        pView.findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onClick(final View pView) {
        final int id = pView.getId();
        switch (id) {
            case R.id.sign_in_button:
                mPresenter.signIn();
        }
    }
}
