package com.github.biba.flashlang.operations.impl.common.impl.fetch;

import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.operations.Operations;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.threading.ExecutorType;
import com.github.biba.lib.threading.IThreadingManager;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.executors.IExecutor;

import java.util.Collection;
import java.util.List;

public class FetchUserCardsOperation implements IOperation<Void> {

    private final String mUserId;
    private final IExecutor mExecutor;

    public FetchUserCardsOperation(final String pUserId) {
        mUserId = pUserId;
        mExecutor = IThreadingManager.Imlp.getThreadingManager()
                .getExecutor(ExecutorType.THREAD);
    }

    @Override
    public Void perform() throws Exception {
        final ICallback<List<Card>> callback = new ICallback<List<Card>>() {

            @Override
            public void onSuccess(final List<Card> pCards) {
                uploadToDb(pCards);
            }

            @Override
            public void onError(final Throwable pThrowable) {
            }
        };
        final IOperation<Void> loadFromFirebase = Operations.newOperation()
                .info()
                .firebase()
                .card()
                .loadList(callback, new Card.ByOwnerIdSelector(mUserId));
        final ICommand command = new Command<>(loadFromFirebase);
        mExecutor.execute(command);
        return null;
    }

    private void uploadToDb(final Collection<Card> pCards) {
        final Card[] cards = new Card[pCards.size()];
        pCards.toArray(cards);
        final IOperation<Boolean> uploadToLocalDb = Operations.newOperation()
                .info()
                .local()
                .card()
                .uploadAll(cards);
        final ICommand command = new Command<>(uploadToLocalDb);
        mExecutor.execute(command);

    }
}
