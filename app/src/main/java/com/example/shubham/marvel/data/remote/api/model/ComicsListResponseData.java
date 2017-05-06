package com.example.shubham.marvel.data.remote.api.model;

import java.util.List;

public class ComicsListResponseData {
    public final List<ApiComic> results;

    public ComicsListResponseData(List<ApiComic> results) {
        this.results = results;
    }
}
