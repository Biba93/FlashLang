package com.github.biba.flashlang.operations.impl.common.impl.fetch;

import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.operations.Operations;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.threading.ExecutorType;
import com.github.biba.lib.threading.IThreadingManager;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.executors.IExecutor;

import java.util.List;

public class FetchUserCollectionsOperation implements IOperation<Void> {

    private final String mUserId;
    private final IExecutor mExecutor;

    public FetchUserCollectionsOperation(final String pUserId) {
        mUserId = pUserId;
        mExecutor = IThreadingManager.Imlp.getThreadingManager()
                .getExecutor(ExecutorType.THREAD);
    }

    @Override
    public Void perform() throws Exception {
        final ICallback<List<Collection>> callback = new ICallback<List<Collection>>() {

            @Override
            public void onSuccess(final List<Collection> pCards) {
                uploadToDb(pCards);
            }

            @Override
            public void onError(final Throwable pThrowable) {
            }
        };
        final IOperation<Void> loadFromFirebase = Operations.newOperation()
                .info()
                .firebase()
                .collection()
                .loadList(callback, new Collection.ByOwnerIdSelector(mUserId));
        final ICommand command = new Command<>(loadFromFirebase);
        mExecutor.execute(command);
        return null;
    }

    private void uploadToDb(final java.util.Collection<Collection> pCollections) {
        final Collection[] collections = new Collection[pCollections.size()];
        pCollections.toArray(collections);
        final IOperation<Boolean> uploadToLocalDb = Operations.newOperation()
                .info()
                .local()
                .collection()
                .uploadAll(collections);
        final ICommand command = new Command<>(uploadToLocalDb);
        mExecutor.execute(command);
    }
}
