package com.github.biba.flashlang.operations.impl.common.impl.fetch;

import com.github.biba.flashlang.domain.models.achievement.Achievement;
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

public class FetchUserAchievementsOperation implements IOperation<Void> {

    private final String mUserId;
    private final IExecutor mExecutor;

    public FetchUserAchievementsOperation(final String pUserId) {
        mUserId = pUserId;
        mExecutor = IThreadingManager.Imlp.getThreadingManager()
                .getExecutor(ExecutorType.THREAD);
    }

    @Override
    public Void perform() throws Exception {
        final ICallback<List<Achievement>> callback = new ICallback<List<Achievement>>() {

            @Override
            public void onSuccess(final List<Achievement> pAchievements) {
                uploadToDb(pAchievements);
            }

            @Override
            public void onError(final Throwable pThrowable) {
            }
        };
        final IOperation<Void> loadFromFirebase = Operations.newOperation()
                .info()
                .firebase()
                .achievement()
                .loadList(callback, new Achievement.ByOwnerIdSelector(mUserId));
        final ICommand command = new Command<>(loadFromFirebase);
        mExecutor.execute(command);
        return null;
    }

    private void uploadToDb(final Collection<Achievement> pAchievements) {
        final Achievement[] achievements = new Achievement[pAchievements.size()];
        pAchievements.toArray(achievements);
        final IOperation<Boolean> uploadToLocalDb = Operations.newOperation()
                .info()
                .local()
                .achievement()
                .uploadAll(achievements);
        final ICommand command = new Command<>(uploadToLocalDb);
        mExecutor.execute(command);

    }
}
