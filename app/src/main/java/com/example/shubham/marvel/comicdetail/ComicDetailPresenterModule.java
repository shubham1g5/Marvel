package com.example.shubham.marvel.comicdetail;

import com.example.shubham.marvel.model.Comic;

import dagger.Module;
import dagger.Provides;

@Module
class ComicDetailPresenterModule {
    private final Comic mComic;

    public ComicDetailPresenterModule(Comic comic) {
       mComic = comic;
    }

    @Provides
    Comic providesComic(){
        return mComic;
    }
}
