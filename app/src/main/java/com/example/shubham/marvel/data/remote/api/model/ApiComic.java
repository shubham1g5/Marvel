package com.example.shubham.marvel.data.remote.api.model;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiComic {
    public final int id;
    public final String title;
    public final String description;
    public final int pageCount;
    public final Thumbnail thumbnail;
    public final List<Price> prices;
    public final Creator creators;
}
