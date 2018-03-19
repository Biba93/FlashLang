package com.github.biba.lib.threading;

import com.github.biba.lib.threading.executors.AsyncTaskExecutor;
import com.github.biba.lib.threading.executors.ExecutorServiceExecutor;
import com.github.biba.lib.threading.executors.IExecutor;
import com.github.biba.lib.threading.publisher.IPublisher;

interface IExecutorFactory {

    IExecutor createAsyncTaskExecutor(AsyncTaskExecutor.Config pConfig);

    IExecutor createExecutorServiceExecutor(IPublisher pPublisher, ExecutorServiceExecutor.Config pConfig);

    IExecutor createThreadExecutor(IPublisher pPublisher);
}
