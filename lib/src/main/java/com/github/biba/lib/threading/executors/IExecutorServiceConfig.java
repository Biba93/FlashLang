package com.github.biba.lib.threading.executors;

interface IExecutorServiceConfig {

    ExecutorServiceType getType();

    int getNumOfThreads();

}
