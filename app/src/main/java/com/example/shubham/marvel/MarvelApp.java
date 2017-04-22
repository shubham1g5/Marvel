package com.example.shubham.marvel;

import android.app.Application;

import com.example.shubham.marvel.data.ComicsRepositoryComponent;
import com.example.shubham.marvel.data.ComicsRepositoryModule;
import com.example.shubham.marvel.data.DaggerComicsRepositoryComponent;

// TODO: 22/4/17 Details UI
// TODO: 22/4/17 Set Budget Header/ Action Item
// TODO: 22/4/17 Total pages header
// TODO: 22/4/17 Test Cases For Repositories and Presenters
public class MarvelApp extends Application {

    private ComicsRepositoryComponent comicsRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        comicsRepositoryComponent = DaggerComicsRepositoryComponent.builder()
                .comicsRepositoryModule(new ComicsRepositoryModule())
                .marvelAppModule(new MarvelAppModule(this))
                .build();
    }

    public ComicsRepositoryComponent getComicsRepositoryComponent() {
        return comicsRepositoryComponent;
    }
}
