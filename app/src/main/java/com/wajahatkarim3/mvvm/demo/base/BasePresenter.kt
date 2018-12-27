package com.wajahatkarim3.mvvm.demo.base

import androidx.annotation.CallSuper

abstract class BasePresenter<T> {

    protected var _view: T? = null

    protected constructor(view: T)
    {
        attachView(view)
    }

    @CallSuper
    fun attachView(view: T)
    {
        _view = view
    }

    @CallSuper
    fun detachView()
    {
        _view = null
    }

    fun isViewAttached() = _view != null

}