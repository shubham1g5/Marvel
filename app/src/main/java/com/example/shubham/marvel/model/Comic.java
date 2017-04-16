package com.example.shubham.marvel.model;

import android.content.ContentValues;

import com.example.shubham.marvel.data.local.ComicsContract;

import java.util.List;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Comic {
    private final int id;
    private final String title;
    private final String description;
    private final String thumbnail;
    private final String image;
    private final int pages;
    private final double price;
    private final List<Author> authors;
}
