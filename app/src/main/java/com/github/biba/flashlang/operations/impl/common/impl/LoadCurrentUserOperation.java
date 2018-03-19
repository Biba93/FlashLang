package com.github.biba.flashlang.operations.impl.common.impl;

import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.firebase.auth.FirebaseUserManager;
import com.github.biba.flashlang.operations.Operations;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.threading.ExecutorType;
import com.github.biba.lib.threading.IThreadingManager;
import com.github.biba.lib.threading.command.Command;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.executors.IExecutor;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LoadCurrentUserOperation implements IOperation<User> {

    private final IExecutor mExecutor;

    public LoadCurrentUserOperation() {
        mExecutor = IThreadingManager.Imlp.getThreadingManager().getExecutor(ExecutorType.EXECUTOR_SERVICE);
    }

    @Override
    public User perform() throws Exception {
        final FirebaseUser currentUser = FirebaseUserManager.Impl.Companion.getInstance()
                .getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No current user");
        }

        final String uid = currentUser.getUid();
        final IOperation<User> loadUserFromLocalDb = Operations.newOperation()
                .info()
                .local()
                .user()
                .loadSingle(new User.ByIdSelector(uid));
        User user = loadUserFromLocalDb.perform();
        if (user == null) {
            user = createNewUser(currentUser);
            uploadUserToDb(user);
        } else {
            fetchUserData(user.getId());
        }
        return user;
    }

    private void uploadUserToDb(final User pUser) {
        final IOperation<Boolean> uploadToLocal = Operations.newOperation()
                .info()
                .local()
                .user()
                .upload(pUser);
        final IOperation<Void> uploadToFirebase = Operations.newOperation()
                .info()
                .firebase()
                .user()
                .upload(pUser);
        final List<ICommand> commands = new ArrayList<>();
        commands.add(new Command<>(uploadToLocal));
        commands.add(new Command<>(uploadToFirebase));

        mExecutor.execute(commands);
    }

    private User createNewUser(final FirebaseUser pUser) {
        final User user = new User(pUser);
        return user;
    }

    private void fetchUserData(final String pUserId) {
        final IOperation<Void> fetchAll = Operations.newOperation().common()
                .fetch()
                .fetchAll(pUserId);
        final ICommand command = new Command<>(fetchAll);
        mExecutor.execute(command);
    }
}
