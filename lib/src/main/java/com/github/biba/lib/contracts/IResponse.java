package com.github.biba.lib.contracts;

public interface IResponse<T> {

    T getResult();

    Throwable getError();
}
