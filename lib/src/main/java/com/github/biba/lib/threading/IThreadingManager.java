package com.github.biba.lib.threading;

import android.os.Handler;
import android.os.Looper;

import com.github.biba.lib.threading.executors.AsyncTaskExecutor;
import com.github.biba.lib.threading.executors.ExecutorServiceExecutor;
import com.github.biba.lib.threading.executors.IExecutor;

public interface IThreadingManager {

    IExecutor getExecutor(ExecutorType pType);

    final class Imlp {

        private static ThreadingManager.Config mConfig;

        public static void setConfig(final ThreadingManager.Config pConfig) {
            mConfig = pConfig;
        }

        public static IThreadingManager getThreadingManager() {
            if (mConfig == null) {
                return new ThreadingManager(Config.getDefaultConfig());
            } else {
                return new ThreadingManager(mConfig);
            }
        }

    }

    class Config {

        private Handler mHandler = new Handler(Looper.getMainLooper());
        private AsyncTaskExecutor.Config mAsyncTaskConfig = AsyncTaskExecutor.Config.getDefaultConfig();
        private ExecutorServiceExecutor.Config mExecutorServiceConfig = ExecutorServiceExecutor.Config.getDefaultConfig();

        public static Config getDefaultConfig() {
            return new Config();
        }

        public Config setHandler(final Handler pHandler) {
            mHandler = pHandler;
            return this;
        }

        public Config setAsyncTaskConfig(final AsyncTaskExecutor.Config pConfig) {
            mAsyncTaskConfig = pConfig;
            return this;
        }

        public Config setExecutorServiceConfig(final ExecutorServiceExecutor.Config pConfig) {
            mExecutorServiceConfig = pConfig;
            return this;
        }

        public Handler getHandler() {
            return mHandler;
        }

        public AsyncTaskExecutor.Config getAsyncTaskConfig() {
            return mAsyncTaskConfig;
        }

        public ExecutorServiceExecutor.Config getExecutorServiceConfig() {
            return mExecutorServiceConfig;
        }
    }
}
