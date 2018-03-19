package com.github.biba.lib.threading.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ExecutorServiceFactory implements IExecutorServiceFactory {

    @Override
    public ExecutorService getExecutorService(final IExecutorServiceConfig pConfig) {
        if (pConfig == null) {
            return null;
        }
        final ExecutorServiceType type = pConfig.getType();
        if (type == null) {
            return null;
        }
        switch (type) {
            case SINGLE_THREAD:
                return createSingleThreadExecutor();
            case FIXED_THREAD:
                final int numOfThreads = pConfig.getNumOfThreads();
                return createFixedThreadPool(numOfThreads);
            default:
                return null;

        }
    }

    private ExecutorService createSingleThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    private ExecutorService createFixedThreadPool(final int pNumOfThreads) throws IllegalStateException {
        if (pNumOfThreads <= 0) {
            throw new IllegalStateException("Wrong num of threads");
        }
        return Executors.newFixedThreadPool(pNumOfThreads);
    }
}
