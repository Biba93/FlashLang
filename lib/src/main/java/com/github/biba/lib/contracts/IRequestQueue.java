package com.github.biba.lib.contracts;

public interface IRequestQueue<T extends IRequest> {

    T takeFirst() throws InterruptedException;

    void addFirst(final T pRequest);

}
