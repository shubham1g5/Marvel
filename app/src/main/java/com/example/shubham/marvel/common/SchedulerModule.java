package com.example.shubham.marvel.common;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulerModule {
    @Provides
    @UI
    Scheduler providesUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @IO
    Scheduler providesIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    rx.Scheduler providesRxIoScheduler() {
        return rx.schedulers.Schedulers.io();
    }
}
