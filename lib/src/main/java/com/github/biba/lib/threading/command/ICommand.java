package com.github.biba.lib.threading.command;

import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;

public interface ICommand<T> {

    IOperation<T> getOperation();

    ICallback<T> getCallback();

}
