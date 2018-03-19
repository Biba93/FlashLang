package com.github.biba.flashlang.ui.presenter;

import android.database.Cursor;

import com.github.biba.flashlang.UserManager;
import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.operations.Operations;
import com.github.biba.flashlang.ui.contract.SourceLanguagesCollectionContract;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.threading.ExecutorType;
import com.github.biba.lib.threading.IThreadingManager;
import com.github.biba.lib.threading.command.Command;

public class SourceLanguagesCollectionPresenter implements SourceLanguagesCollectionContract.Presenter {

    private SourceLanguagesCollectionContract.View mView;

    @Override
    public void attachView(final SourceLanguagesCollectionContract.View pView) {
        mView = pView;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void load() {
        final String currentUserId = UserManager.getCurrentUser().getId();
        final IOperation<Cursor> loadSourceLanguagesCursor = Operations.newOperation()
                .info()
                .local()
                .collection()
                .loadCursor(Collection.DbKeys.SOURCE_LANGUAGE, new Collection.ByOwnerIdSelector(currentUserId));

        final ICallback<Cursor> callback = new ICallback<Cursor>() {

            @Override
            public void onSuccess(final Cursor pCursor) {
                publishResult(pCursor);
            }

            @Override
            public void onError(final Throwable pThrowable) {
                publishError(pThrowable);
            }
        };
        final Command<Cursor> command = new Command<>(loadSourceLanguagesCursor);
        command.setCallback(callback);

        IThreadingManager.Imlp.getThreadingManager().getExecutor(ExecutorType.ASYNC_TASK)
                .execute(command);
    }

    private void publishResult(final Cursor pCursor) {
        if (mView != null) {
            mView.onLoaded(pCursor);
        }
    }

    private void publishError(final Throwable pThrowable) {
        if (mView != null) {
            mView.onError(pThrowable.getMessage());
        }
    }

}


