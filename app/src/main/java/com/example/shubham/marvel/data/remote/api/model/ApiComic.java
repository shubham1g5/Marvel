package com.example.shubham.marvel.data.remote.api.model;

import java.util.List;

public class ApiComic {
    public final int id;
    public final String title;
    public final String description;
    public final int pageCount;
    public final Thumbnail thumbnail;
    public final List<Price> prices;
    public final Creator creators;

    public ApiComic(int id, String title, String description, int pageCount, Thumbnail thumbnail, List<Price> prices, Creator creators) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.prices = prices;
        this.creators = creators;
    }
}
