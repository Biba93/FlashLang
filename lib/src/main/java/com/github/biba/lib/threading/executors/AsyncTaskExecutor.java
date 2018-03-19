package com.github.biba.lib.threading.executors;

import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.github.biba.lib.Constants;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;
import com.github.biba.lib.logs.Log;
import com.github.biba.lib.threading.IExecutedCallback;
import com.github.biba.lib.threading.command.ICommand;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class AsyncTaskExecutor implements IExecutor {

    private static final String LOG_TAG = AsyncTaskExecutor.class.getSimpleName();

    private final Config mConfig;

    public AsyncTaskExecutor(final Config pConfig) {
        mConfig = pConfig;
    }

    @Override
    public <Result> void execute(final ICommand<Result> pCommand) {
        final ExecutedAsyncTask<Result> task = new ExecutedAsyncTask<>(pCommand);
        final ExecutorService executorService = getExecutorService();
        if (executorService != null) {
            task.executeOnExecutor(executorService);
        } else {
            task.execute();
        }
    }

    @Override
    public void execute(final List<? extends ICommand> pCommands) {
        execute(pCommands, null);
    }

    @Override
    public void execute(final List<? extends ICommand> pCommands, final IExecutedCallback pExecutedCallback) {
        //noinspection unchecked
        final ExecutedAsyncTasks tasks = new ExecutedAsyncTasks((List<ICommand>) pCommands, pExecutedCallback);
        final ExecutorService executorService = getExecutorService();
        if (executorService != null) {
            tasks.executeOnExecutor(executorService);
        } else {
            tasks.execute();
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

        ExecutorServiceType mType;
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

    private static class ExecutedAsyncTask<Result> extends AsyncTask<Void, Void, Object> {

        final ICommand<Result> mCommand;

        ExecutedAsyncTask(final ICommand<Result> pCommand) {
            mCommand = pCommand;
        }

        @Override
        protected Object doInBackground(final Void... pVoid) {
            try {
                if (mCommand != null) {
                    final IOperation<Result> operation = mCommand.getOperation();
                    return operation.perform();
                } else {
                    return null;
                }
            } catch (final Exception pE) {
                return pE;
            }
        }

        @Override
        protected void onPostExecute(final Object pO) {
            if (mCommand != null) {
                final ICallback<Result> callback = mCommand.getCallback();
                if (callback != null) {
                    if (pO == null) {
                        callback.onError(new Exception("Can't get result"));
                    } else if (pO instanceof Throwable) {
                        callback.onError((Throwable) pO);
                    } else {
                        try {
                            //noinspection unchecked
                            final Result result = (Result) pO;
                            callback.onSuccess(result);
                        } catch (final Exception pE) {
                            Log.e(LOG_TAG, "onPostExecute: ", pE);
                            callback.onError(new Exception("Can't get result"));
                        }
                    }
                }
            }
        }
    }

    private static class ExecutedAsyncTasks extends AsyncTask<Void, Pair<Object, ICallback<Object>>, Void> {

        private final List<ICommand> mCommands;
        private final IExecutedCallback mCallback;

        public ExecutedAsyncTasks(final List<ICommand> pCommands, final IExecutedCallback pCallback) {
            mCommands = pCommands;
            mCallback = pCallback;
        }

        @Override
        protected Void doInBackground(final Void... pVoids) {
            for (final ICommand command :
                    mCommands) {
                try {
                    if (command != null) {
                        final Object result = command.getOperation().perform();
                        //noinspection unchecked
                        onProgressUpdate(new Pair<Object, ICallback<Object>>(result, command.getCallback()));
                    }
                } catch (final Exception ignored) {
                    Log.e(LOG_TAG, "doInBackground: ", ignored);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(final Pair<Object, ICallback<Object>>... values) {
            if (values != null && values.length > 0) {
                final Object result = values[0].first;
                final ICallback<Object> callback = values[0].second;
                if (callback != null) {
                    if (result == null) {
                        callback.onError(new Exception("Can't get result"));
                        return;
                    }

                    if (result instanceof Throwable) {
                        callback.onError((Throwable) result);
                        return;
                    }
                    callback.onSuccess(result);
                }

            }
        }

        @Override
        protected void onPostExecute(final Void pO) {
            if (mCallback != null) {
                mCallback.onFinished();
            }
        }
    }

}
