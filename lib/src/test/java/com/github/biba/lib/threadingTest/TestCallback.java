package com.github.biba.lib.threadingTest;

import com.github.biba.lib.contracts.ICallback;

public class TestCallback implements ICallback<String> {

    private static int count;
    private String mMessage;

    @Override
    public void onSuccess(final String pS) {
        mMessage = pS;
        count++;
    }

    @Override
    public void onError(final Throwable pThrowable) {
        mMessage = pThrowable.getMessage();
        count++;
    }

    public String getMessage() {
        return mMessage;
    }

    public int getCount() {
        return count;
    }
}
