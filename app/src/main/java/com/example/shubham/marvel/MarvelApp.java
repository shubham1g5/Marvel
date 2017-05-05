package com.example.shubham.marvel;

import android.app.Application;

import com.example.shubham.marvel.data.ComicsRepositoryModule;

// TODO: 22/4/17 Test Cases For Repositories and Presenters
public class MarvelApp extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .comicsRepositoryModule(new ComicsRepositoryModule())
                .marvelAppModule(new MarvelAppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
