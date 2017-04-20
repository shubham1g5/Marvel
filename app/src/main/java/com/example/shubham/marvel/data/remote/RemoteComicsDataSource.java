package com.example.shubham.marvel.data.remote;

import com.example.shubham.marvel.data.ComicsDataSource;
import com.example.shubham.marvel.model.Comic;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class RemoteComicsDataSource implements ComicsDataSource {

    private final MarvelService mMarvelService;
    private final ComicMapper mComicMapper;

    @Inject
    public RemoteComicsDataSource(MarvelService marvelService, ComicMapper comicMapper) {
        mMarvelService = marvelService;
        mComicMapper = comicMapper;
    }

    @Override
    public Observable<List<Comic>> getComics(int capacity) {
        return mMarvelService.getComics(capacity)
                .map(mComicMapper::map);
    }

    @Override
    public Observable<Comic> getComic(int comicId) {
        return null;
    }

    @Override
    public void saveComic(Comic comic) {
    }

    @Override
    public void removeAll() {
    }
}