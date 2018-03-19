package com.github.biba.flashlang.ui.presenter;

import com.github.biba.flashlang.UserManager;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.operations.Operations;
import com.github.biba.flashlang.ui.contract.MainContract;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.logs.Log;

public class MainPresenter implements MainContract.Presenter {

    private static final String LOG_TAG = MainPresenter.class.getSimpleName();

    private MainContract.View mView;

    @Override
    public void attachView(final MainContract.View pView) {
        mView = pView;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadCurrentUser() {
        final User currentUser = UserManager.getCurrentUser();
        if (currentUser == null) {
            final IOperation<User> loadUserOperation = Operations.newOperation()
                    .common()
                    .load()
                    .currentUser();
            try {
                final User user = loadUserOperation.perform();
                UserManager.setCurrentUser(user);
            } catch (final Exception pE) {
                Log.e(LOG_TAG, "currentUser: ", pE);
            }
        }
    }

}
