package com.github.biba.lib.contracts;

public interface ICallback<Result> {

    void onSuccess(Result pResult);

    void onError(Throwable pThrowable);

}
