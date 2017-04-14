package com.example.shubham.marvel.data;

import com.example.shubham.marvel.model.Comic;

import java.util.List;

import io.reactivex.Observable;

public interface ComicsDataSource {

    Observable<List<Comic>> getComics();

    Observable<Comic> getComic(int comicId);
}
