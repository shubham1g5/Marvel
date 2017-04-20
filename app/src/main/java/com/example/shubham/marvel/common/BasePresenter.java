package com.example.shubham.marvel.common;

import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BasePresenter<V extends BasePresenterView> {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private V view;

    @CallSuper
    public void register(V view) {
        if (this.view != null) {
            throw new IllegalStateException("View " + this.view + " is already attached. Cannot attach " + view);
        }
        this.view = view;
        onRegister(view);
    }

    protected abstract void onRegister(V view);

    @CallSuper
    public void unregister() {
        if (view == null) {
            throw new IllegalStateException("View is already detached");
        }
        view = null;
        compositeDisposable.clear();
    }

    @CallSuper
    protected final void addToUnsubscribe(final Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}
