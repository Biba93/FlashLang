package com.github.biba.flashlang.operations.impl.common.impl;

import android.net.Uri;

import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.domain.models.collection.CollectionBuilder;
import com.github.biba.flashlang.firebase.storage.IFirebaseStorageManager;
import com.github.biba.flashlang.operations.Operations;
import com.github.biba.flashlang.utils.OperationUtils;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.logs.Log;
import com.github.biba.lib.threading.ExecutorType;
import com.github.biba.lib.threading.IThreadingManager;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.executors.IExecutor;

import java.util.ArrayList;
import java.util.List;

public class CreateCollectionOperation implements IOperation<String> {

    private static final String LOG_TAG = CreateCollectionOperation.class.getSimpleName();

    private final String mUserId;
    private final String mSourceLanguageKey;
    private final String mTargetLanguageKey;
    private final IExecutor mExecutor;

    public CreateCollectionOperation(final String pUserId, final String pSourceLanguageKey, final String pTargetLanguageKey) {
        mUserId = pUserId;
        mSourceLanguageKey = pSourceLanguageKey;
        mTargetLanguageKey = pTargetLanguageKey;
        mExecutor = IThreadingManager.Imlp.getThreadingManager().getExecutor(ExecutorType.EXECUTOR_SERVICE);
    }

    @Override
    public String perform() {
        final String id = OperationUtils.getIdForCollection();
        final CollectionBuilder collectionBuilder = new CollectionBuilder().setId(id)
                .setOwnerId(mUserId)
                .setSourceLanguage(mSourceLanguageKey)
                .setTargetLanguage(mTargetLanguageKey);

        final IFirebaseStorageManager.ILoadListener sourceLanguageLoadListener = new IFirebaseStorageManager.ILoadListener() {

            @Override
            public void onSuccess(final Uri pUri) {
                collectionBuilder.setSourceLanguageCover(pUri.toString());
                loadNext();
            }

            @Override
            public void onError(final Throwable pThrowable) {
                Log.e(LOG_TAG, "onError: ", pThrowable);
                loadNext();
            }

            private void loadNext() {
                final IFirebaseStorageManager.ILoadListener targetLanguageLoadListener = new IFirebaseStorageManager.ILoadListener() {

                    @Override
                    public void onSuccess(final Uri pUri) {
                        collectionBuilder.setTargetLanguageCover(pUri.toString());
                        putToDb(collectionBuilder.createCollection());
                    }

                    @Override
                    public void onError(final Throwable pThrowable) {
                        Log.e(LOG_TAG, "onError: ", pThrowable);
                        putToDb(collectionBuilder.createCollection());
                    }
                };
                final IOperation<Void> getTargetLanguageCoverOperation = Operations.newOperation()
                        .storage()
                        .getLanguageCoverUrl(mTargetLanguageKey, targetLanguageLoadListener);
                final ICommand<Void> getTargetLanguageCoverCommand = new Command<>(getTargetLanguageCoverOperation);
                mExecutor.execute(getTargetLanguageCoverCommand);
            }
        };

        final IOperation<Void> getSourceLanguageCoverOperation = Operations.newOperation()
                .storage()
                .getLanguageCoverUrl(mSourceLanguageKey, sourceLanguageLoadListener);

        final ICommand<Void> getSourceLanguageCoverCommand = new Command<>(getSourceLanguageCoverOperation);

        mExecutor.execute(getSourceLanguageCoverCommand);

        return id;
    }

    private void putToDb(final Collection mCollection) {
        final IOperation<Boolean> uploadToLocalDb = Operations.newOperation()
                .info()
                .local()
                .collection()
                .upload(mCollection);
        final IOperation<Void> uploadToFirebase = Operations.newOperation()
                .info()
                .firebase()
                .collection()
                .upload(mCollection);
        final List<ICommand> commands = new ArrayList<>();
        commands.add(new Command<>(uploadToLocalDb));
        commands.add(new Command<>(uploadToFirebase));
        IThreadingManager.Imlp.getThreadingManager().getExecutor(ExecutorType.THREAD)
                .execute(commands);
    }
}
