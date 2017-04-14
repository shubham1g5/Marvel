package com.example.shubham.marvel.data;

import com.example.shubham.marvel.data.local.LocalComicsDataSource;
import com.example.shubham.marvel.data.remote.RemoteComicsDataSource;
import com.example.shubham.marvel.model.Comic;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ComicsRepository implements ComicsDataSource{

    private final LocalComicsDataSource mLocalComicsDataSource;
    private final RemoteComicsDataSource mRemoteComicsDataSource;

    @Inject
    ComicsRepository(LocalComicsDataSource localComicsDataSource, RemoteComicsDataSource remoteComicsDataSource) {
        mLocalComicsDataSource = localComicsDataSource;
        mRemoteComicsDataSource = remoteComicsDataSource;
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
