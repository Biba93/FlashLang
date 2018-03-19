package com.github.biba.flashlang.ui.fragment.auth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import com.github.biba.flashlang.R;
import com.github.biba.flashlang.ui.contract.AuthContract;

public class BaseAuthFragment extends Fragment implements AuthContract.View.IAuthInfoView {

    private View mView;

    public BaseAuthFragment() {
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
    }

    @Override
    public String getEmail() {
        final EditText emailView = mView.findViewById(R.id.editText_email);
        return emailView.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        final EditText passwordView = mView.findViewById(R.id.editText_password);
        return passwordView.getText().toString().trim();
    }

}
