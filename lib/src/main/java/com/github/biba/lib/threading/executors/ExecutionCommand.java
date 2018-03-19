package com.github.biba.lib.threading.executors;

import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.publisher.IPublisher;

class ExecutionCommand<Result> implements Runnable {

    private final IPublisher mPublisher;
    private final ICommand<Result> mCommand;

    ExecutionCommand(final IPublisher pPublisher, final ICommand<Result> pCommand) {
        mPublisher = pPublisher;
        mCommand = pCommand;
    }

    @Override
    public void run() {
        try {
            final Result result = mCommand.getOperation().perform();
            if (mPublisher != null) {
                mPublisher.publishResult(result, mCommand.getCallback());
            }
        } catch (final Exception pE) {
            if (mPublisher != null) {
                mPublisher.publishError(pE, mCommand.getCallback());
            }
        }
    }
}
