package com.example.shubham.marvel.data.local;

import android.database.Cursor;

import com.example.shubham.marvel.data.ComicsDataSource;
import com.example.shubham.marvel.data.remote.ComicMapper;
import com.example.shubham.marvel.model.Comic;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;

@Singleton
public class LocalComicsDataSource implements ComicsDataSource {

    private final BriteDatabase mDb;
    private final ComicMapper mComicMapper;

    @Inject
    public LocalComicsDataSource(BriteDatabase db, ComicMapper comicMapper) {
        mDb = db;
        mComicMapper = comicMapper;
    }

    @Override
    public Observable<List<Comic>> getComics(int capacity) {
        return RxJavaInterop.toV2Observable(mDb.createQuery(ComicsContract.ComicsEntry.TABLE_NAME,
                "select * from " + ComicsContract.ComicsEntry.TABLE_NAME + " order by " + ComicsContract.ComicsEntry._ID + " desc limit " + capacity))
                .map(query -> {
                    Cursor cursor = query.run();
                    try {
                         return mComicMapper.map(cursor);
                    } finally {
                        cursor.close();
                    }
                });
    }

    @Override
    public Observable<Comic> getComic(int comicId) {
        return null;
    }

    @Override
    public void saveComic(Comic comic) {
        mDb.insert(ComicsContract.ComicsEntry.TABLE_NAME, mComicMapper.toContentValues(comic));
    }

    @Override
    public void removeAll() {
        mDb.delete(ComicsContract.ComicsEntry.TABLE_NAME, null, null);
        mDb.delete(ComicsContract.AuthorsEntry.TABLE_NAME, null, null);
        mDb.delete(ComicsContract.ComicHasAuthorsEntry.TABLE_NAME, null, null);
    }
}
