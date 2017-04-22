package com.example.shubham.marvel.data;

import com.example.shubham.marvel.model.Author;
import com.example.shubham.marvel.model.Comic;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface ComicsDataSource {

    Observable<List<Comic>> getComics(int capacity);

    void saveComic(Comic comic);

    void removeAll();

    Observable<List<Author>> getAuthors(int comicId);
}
