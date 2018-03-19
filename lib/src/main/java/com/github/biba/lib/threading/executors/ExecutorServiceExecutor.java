package com.github.biba.lib.threading.executors;

import com.github.biba.lib.Constants;
import com.github.biba.lib.logs.Log;
import com.github.biba.lib.threading.IExecutedCallback;
import com.github.biba.lib.threading.command.ICommand;
import com.github.biba.lib.threading.publisher.IPublisher;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class ExecutorServiceExecutor implements IExecutor {

    private static final String LOG_TAG = ExecutorServiceExecutor.class.getSimpleName();

    private final Config mConfig;
    private final IPublisher mPublisher;

    public ExecutorServiceExecutor(final IPublisher pPublisher, final Config pConfig) {
        mPublisher = pPublisher;
        mConfig = pConfig;
    }

    @Override
    public <Result> void execute(final ICommand<Result> pCommand) {
        final Runnable command = new ExecutionCommand<>(mPublisher, pCommand);
        executeCommand(command);
    }

    @Override
    public void execute(final List<? extends ICommand> pCommands) {
        execute(pCommands, null);
    }

    @Override
    public void execute(final List<? extends ICommand> pCommands, final IExecutedCallback pExecutedCallback) {
        //noinspection unchecked
        final Runnable pool = new ExecutionPool(mPublisher, (List<ICommand>) pCommands, pExecutedCallback);
        executeCommand(pool);
    }

    private void executeCommand(final Runnable pCommand) {
        final ExecutorService executorService = getExecutorService();
        if (executorService != null) {
            executorService.execute(pCommand);
        }
    }

    private ExecutorService getExecutorService() {
        final IExecutorServiceFactory executorServiceFactory = new ExecutorServiceFactory();
        try {
            return executorServiceFactory.getExecutorService(mConfig);
        } catch (final Exception pE) {
            Log.e(LOG_TAG, "getExecutorService: ", pE);
            return null;
        }
    }

    public static class Config implements IExecutorServiceConfig {

        ExecutorServiceType mType = Constants.Executor.DEFAULT_EXECUTOR_SERVICE;
        int mMaxNumOfThreads = Constants.Executor.DEFAULT_NUM_OF_THREADS;

        public static Config getDefaultConfig() {
            return new Config();
        }

        public Config setExecutorType(final ExecutorServiceType pType) {
            mType = pType;
            return this;
        }

        public Config setMaxNumOfThreads(final int pNumOfThreads) {
            mMaxNumOfThreads = pNumOfThreads;
            return this;
        }

        @Override
        public ExecutorServiceType getType() {
            return mType;
        }

        @Override
        public int getNumOfThreads() {
            return mMaxNumOfThreads;
        }
    }

}
