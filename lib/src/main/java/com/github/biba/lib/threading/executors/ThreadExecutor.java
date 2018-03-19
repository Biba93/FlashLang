package com.github.biba.lib.threading.executors;

import com.github.biba.lib.threading.IExecutedCallback;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.publisher.IPublisher;

import java.util.List;

public class ThreadExecutor implements IExecutor {

    private final IPublisher mPublisher;

    public ThreadExecutor(final IPublisher pPublisher) {
        mPublisher = pPublisher;
    }

    @Override
    public <Result> void execute(final ICommand<Result> pCommand) {
        final Runnable command = new ExecutionCommand<>(mPublisher, pCommand);
        runCommand(command);
    }

    @Override
    public void execute(final List<? extends ICommand> pCommands) {
        execute(pCommands, null);
    }

    @Override
    public void execute(final List<? extends ICommand> pCommands, final IExecutedCallback pExecutedCallback) {
        final Runnable pool = new ExecutionPool(mPublisher, (List<ICommand>) pCommands, pExecutedCallback);
        runCommand(pool);
    }

    private void runCommand(final Runnable pRunnable) {
        final Thread thread = new Thread(pRunnable);
        thread.start();
    }
}
