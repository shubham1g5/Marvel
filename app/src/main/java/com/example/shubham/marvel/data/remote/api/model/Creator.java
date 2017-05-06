package com.example.shubham.marvel.data.remote.api.model;

import com.example.shubham.marvel.model.Author;

import java.util.List;

public class Creator {
    public final List<Author> items;

    public Creator(List<Author> items) {
        this.items = items;
    }
}
