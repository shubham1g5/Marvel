package com.example.shubham.marvel;

import android.app.Application;

import com.example.shubham.marvel.data.ComicsRepositoryComponent;
import com.example.shubham.marvel.data.ComicsRepositoryModule;
import com.example.shubham.marvel.data.DaggerComicsRepositoryComponent;

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
