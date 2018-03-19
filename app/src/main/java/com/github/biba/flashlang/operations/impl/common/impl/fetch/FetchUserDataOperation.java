package com.github.biba.flashlang.operations.impl.common.impl.fetch;

import com.github.biba.flashlang.operations.Operations;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.threading.ExecutorType;
import com.github.biba.lib.threading.IThreadingManager;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;

import java.util.ArrayList;
import java.util.List;

public class FetchUserDataOperation implements IOperation<Void> {

    private final String mUserId;

    public FetchUserDataOperation(final String pUserId) {
        mUserId = pUserId;
    }

    @Override
    public Void perform() throws Exception {
        final IOperation<Void> fetchAchivements = Operations.newOperation().common()
                .fetch()
                .fetchUserAchievemets(mUserId);
        final IOperation<Void> fetchCards = Operations.newOperation().common()
                .fetch()
                .fetchUserCards(mUserId);
        final IOperation<Void> fetchCollections = Operations.newOperation().common()
                .fetch()
                .fetchUserCollections(mUserId);
        final IOperation<Void> fetchUserInfo = Operations.newOperation().common()
                .fetch()
                .fetchUserInfo(mUserId);

        final List<ICommand> commands = new ArrayList<>();
        commands.add(new Command<>(fetchAchivements));
        commands.add(new Command<>(fetchCards));
        commands.add(new Command<>(fetchCollections));
        commands.add(new Command<>(fetchUserInfo));

        IThreadingManager.Imlp.getThreadingManager().getExecutor(ExecutorType.EXECUTOR_SERVICE)
                .execute(commands);
        return null;
    }
}
