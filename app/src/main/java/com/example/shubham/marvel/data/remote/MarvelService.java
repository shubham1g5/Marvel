package com.example.shubham.marvel.data.remote;

import com.example.shubham.marvel.data.remote.api.model.ComicsListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelService {

    @GET("comics?format=comic&formatType=comic&noVariants=true&orderBy=focDate")
    Observable<ComicsListResponse> getComics(@Query("limit") int capacity);
}
