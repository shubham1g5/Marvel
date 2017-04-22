package com.example.shubham.marvel.data;

import android.support.annotation.Nullable;

import com.example.shubham.marvel.model.Author;
import com.example.shubham.marvel.model.Comic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class ComicsRepository implements ComicsDataSource {

    private static final int CACHE_CAPACITY = 100;

    private final ComicsDataSource mLocalComicsDataSource;
    private final ComicsDataSource mRemoteComicsDataSource;

    @Nullable
    Map<Integer, Comic> mCachedComics;

    boolean mCacheIsDirty = false;

    @Inject
    ComicsRepository(@Local ComicsDataSource localComicsDataSource, @Remote ComicsDataSource remoteComicsDataSource) {
        mLocalComicsDataSource = localComicsDataSource;
        mRemoteComicsDataSource = remoteComicsDataSource;
    }

    @Override
    public Observable<List<Comic>> getComics(int capacity) {

        // Respond immediately with cache if available and not dirty
        if (mCachedComics != null && !mCacheIsDirty) {
            return Observable.just(new ArrayList<>(mCachedComics.values()));
        } else if (mCachedComics == null) {
            mCachedComics = new LinkedHashMap<>(CACHE_CAPACITY);
        }

        Observable<List<Comic>> remoteComics = getAndSaveRemoteComics(capacity);

        if (mCacheIsDirty) {
            return remoteComics;
        } else {
            // Query the local storage if available. If not, query the network.
            Observable<List<Comic>> localComics = getAndCacheLocalComics(capacity);
            return Observable.concat(localComics, remoteComics)
                    .filter(comics -> !comics.isEmpty())
                    .first(new ArrayList<>())
                    .toObservable();
        }
    }

    private Observable<List<Comic>> getAndCacheLocalComics(int capacity) {
        return mLocalComicsDataSource.getComics(capacity)
                .flatMap(comics -> Observable.fromIterable(comics)
                        .doOnNext(comic -> mCachedComics.put(comic.getId(), comic))
                        .toList().toObservable());
    }

    private Observable<List<Comic>> getAndSaveRemoteComics(int capacity) {
        return mRemoteComicsDataSource.getComics(capacity)
                .doOnNext(comics -> mLocalComicsDataSource.removeAll())
                .flatMap(comics -> Observable.fromIterable(comics))
                .doOnNext(comic -> mLocalComicsDataSource.saveComic(comic))
                .doOnNext(comic -> mCachedComics.put(comic.getId(), comic))
                .toList().toObservable()
                .doOnComplete(() -> mCacheIsDirty = false);
    }

    @Override
    public void saveComic(Comic comic) {
    }

    @Override
    public void removeAll() {
    }

    @Override
    public Observable<List<Author>> getAuthors(int comicId) {
        return mLocalComicsDataSource.getAuthors(comicId);
    }

    public void refreshComics(boolean refresh) {
        mCacheIsDirty = true;
    }
}
