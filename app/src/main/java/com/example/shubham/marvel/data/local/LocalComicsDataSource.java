package com.example.shubham.marvel.data.local;

import com.example.shubham.marvel.data.ComicsDataSource;
import com.example.shubham.marvel.model.Comic;

import java.util.List;

import io.reactivex.Observable;

public class LocalComicsDataSource implements ComicsDataSource {
    @Override
    public Observable<List<Comic>> getComics() {
        return null;
    }

    @Override
    public Observable<Comic> getComic(int comicId) {
        return null;
    }
}
