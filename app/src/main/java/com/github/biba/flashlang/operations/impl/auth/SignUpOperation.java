package com.github.biba.flashlang.operations.impl.auth;

import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.firebase.auth.IFirebaseUserManager;
import com.github.biba.flashlang.firebase.auth.UserFactory;
import com.github.biba.flashlang.firebase.auth.request.AuthRequest;
import com.github.biba.flashlang.firebase.auth.request.EmailAuthInfo;
import com.github.biba.flashlang.firebase.auth.request.IAuthRequest;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;
import com.google.firebase.auth.FirebaseUser;

public class SignUpOperation implements IOperation<Void> {

    private final String mEmail;
    private final String mPassword;
    private final ICallback<User> mCallback;

    public SignUpOperation(final String pEmail, final String pPassword, final ICallback<User> pCallback) {
        mEmail = pEmail;
        mPassword = pPassword;
        mCallback = pCallback;
    }

    @Override
    public Void perform() {
        final IFirebaseUserManager userManager = IFirebaseUserManager.Impl.Companion.getInstance();
        final EmailAuthInfo authInfo = new EmailAuthInfo(mEmail, mPassword);
        final IAuthRequest request = new AuthRequest(authInfo);
        userManager.signUp(request, new IFirebaseUserManager.IAuthCallback() {

            @Override
            public void onSuccess(final FirebaseUser pUser) {
                final UserFactory factory = new UserFactory();
                final User user = factory.createUser(pUser);
                mCallback.onSuccess(user);
            }

            @Override
            public void onError(final Throwable pThrowable) {
                mCallback.onError(pThrowable);
            }
        });
        return null;
    }
}
