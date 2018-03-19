package com.github.biba.lib.threading.executors;

import java.util.concurrent.ExecutorService;

interface IExecutorServiceFactory {

    ExecutorService getExecutorService(IExecutorServiceConfig pConfig);

}
