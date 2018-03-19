package com.github.biba.lib.threading.publisher;

import android.os.Handler;
import android.os.Looper;

import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.threading.IExecutedCallback;

public class Publisher implements IPublisher {

    private final Handler mHandler;

    public Publisher() {
        this(new Handler(Looper.getMainLooper()));
    }

    public Publisher(final Handler pHandler) {
        mHandler = pHandler;
    }

    @Override
    public void publishExecuted(final IExecutedCallback pCallback) {
        post(new Runnable() {

            @Override
            public void run() {
                if (pCallback != null) {
                    pCallback.onFinished();
                }
            }
        });
    }

    @Override
    public <Result> void publishResult(final Result pResult, final ICallback<Result> pCallback) {
        post(new Runnable() {

            @Override
            public void run() {
                if (pCallback != null) {
                    pCallback.onSuccess(pResult);
                }
            }
        });

    }

    @Override
    public void publishError(final Throwable pThrowable, final ICallback pCallback) {

        post(new Runnable() {

            @Override
            public void run() {
                if (pCallback != null) {
                    pCallback.onError(pThrowable);
                }
            }
        });

    }

    private void post(final Runnable pRunnable) {
        mHandler.post(pRunnable);
    }
}
