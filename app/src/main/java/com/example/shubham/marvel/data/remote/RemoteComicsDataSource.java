package com.example.shubham.marvel.data.remote;

import com.example.shubham.marvel.data.ComicsDataSource;
import com.example.shubham.marvel.model.Comic;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class RemoteComicsDataSource implements ComicsDataSource {

    private final MarvelService mMarvelService;
    private final ComicMapper mComicMapper;

    @Inject
    RemoteComicsDataSource(MarvelService marvelService, ComicMapper comicMapper) {
        mMarvelService = marvelService;
        mComicMapper = comicMapper;
    }

    @Override
    public Observable<List<Comic>> getComics() {
        return null;
    }

    @Override
    public Observable<Comic> getComic(int comicId) {
        return null;
    }
}
