package com.example.shubham.marvel.common;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class EventBus {
    private final Relay<Object> mBus;

    @Inject
    public EventBus() {
        mBus = PublishRelay.create().toSerialized();
    }

    public void send(Object o) {
        mBus.accept(o);
    }

    public Flowable<Object> asFlowable() {
        return mBus.toFlowable(BackpressureStrategy.LATEST);
    }

    public Observable<Object> asObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }
}
