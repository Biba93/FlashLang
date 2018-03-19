package com.github.biba.flashlang.ui.presenter;

public interface BasePresenter<T> {

    void attachView(T pView);

    void detachView();

}
