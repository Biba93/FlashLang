package com.github.biba.lib.threading.command;

import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;

public class Command<T> implements ICommand<T> {

    private final IOperation<T> mOperation;
    private ICallback<T> mCallback;

    public Command(final IOperation<T> pOperation) {
        mOperation = pOperation;
    }

    @Override
    public IOperation<T> getOperation() {
        return mOperation;
    }

    @Override
    public ICallback<T> getCallback() {
        return mCallback;
    }

    public void setCallback(final ICallback<T> pCallback) {
        mCallback = pCallback;
    }
}
