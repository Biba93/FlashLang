package com.github.biba.lib.threading.publisher;

import com.github.biba.lib.contracts.ICallback;

public interface IResultPublisher {

    <Result> void publishResult(final Result pResult, final ICallback<Result> pCallback);

    void publishError(final Throwable pThrowable, final ICallback pCallback);

}
