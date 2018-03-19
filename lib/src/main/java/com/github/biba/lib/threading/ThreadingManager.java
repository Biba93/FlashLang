package com.github.biba.lib.threading;

import com.github.biba.lib.threading.executors.IExecutor;
import com.github.biba.lib.threading.publisher.Publisher;

class ThreadingManager implements IThreadingManager {

    private final Config mConfig;
    private final IExecutorFactory mExecutorFactory;

    public ThreadingManager(final Config pConfig) {
        mExecutorFactory = new ExecutorFactory();
        mConfig = pConfig;
    }

    @Override
    public IExecutor getExecutor(final ExecutorType pType) {
        switch (pType) {
            case ASYNC_TASK:
                return createAsyncTask();
            case EXECUTOR_SERVICE:
                return createExecutorService();
            case THREAD:
                return createThread();
            default:
                return null;
        }
    }

    private IExecutor createAsyncTask() {
        final IExecutor executor = mExecutorFactory.createAsyncTaskExecutor(mConfig.getAsyncTaskConfig());
        return executor;
    }

    private IExecutor createExecutorService() {
        final IExecutor executor = mExecutorFactory.createExecutorServiceExecutor(new Publisher(mConfig.getHandler()),
                mConfig.getExecutorServiceConfig());
        return executor;
    }

    private IExecutor createThread() {
        final IExecutor executor = mExecutorFactory.createThreadExecutor(new Publisher(mConfig.getHandler()));
        return executor;
    }

}
