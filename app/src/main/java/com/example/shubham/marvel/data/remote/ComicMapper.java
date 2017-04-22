package com.example.shubham.marvel.data.remote;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.shubham.marvel.data.local.ComicsContract;
import com.example.shubham.marvel.data.remote.api.model.ApiComic;
import com.example.shubham.marvel.data.remote.api.model.ComicsListResponse;
import com.example.shubham.marvel.data.remote.api.model.Price;
import com.example.shubham.marvel.data.remote.api.model.Thumbnail;
import com.example.shubham.marvel.model.Author;
import com.example.shubham.marvel.model.Comic;

import java.util.ArrayList;
import java.util.List;

public class ComicMapper {
    private static final String SIZE_PORTRAIT_SMALL = "portrait_small";
    private static final String SIZE_LANDSCAPE_XLARGE = "landscape_xlarge";
    private static final String PRICE_TYPE_PRINT = "printPrice";

    public List<Comic> map(ComicsListResponse comicsListResponse) {
        List<Comic> comics = new ArrayList<>();
        comicsListResponse.data.results.forEach(apiComic -> comics.add(map(apiComic)));
        return comics;
    }

    public List<Comic> map(Cursor cursor) {
        List<Comic> comics = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                comics.add(fromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return comics;
    }

    public ContentValues getContentValuesFromComic(Comic comic) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ComicsContract.ComicsEntry._ID, comic.getId());
        contentValues.put(ComicsContract.ComicsEntry.COL_TITLE, comic.getTitle());
        contentValues.put(ComicsContract.ComicsEntry.COL_THUMBNAIL, comic.getThumbnail());
        contentValues.put(ComicsContract.ComicsEntry.COL_PAGES, comic.getPages());
        contentValues.put(ComicsContract.ComicsEntry.COL_PRICE, comic.getPrice());
        contentValues.put(ComicsContract.ComicsEntry.COL_IMAGE, comic.getImage());
        contentValues.put(ComicsContract.ComicsEntry.COL_DESCRIPTION, comic.getDescription());
        return contentValues;
    }

    private Comic fromCursor(Cursor cursor) {
        return Comic.builder()
                .id(cursor.getInt(cursor.getColumnIndex(ComicsContract.ComicsEntry._ID)))
                .title(cursor.getString(cursor.getColumnIndex(ComicsContract.ComicsEntry.COL_TITLE)))
                .description(cursor.getString(cursor.getColumnIndex(ComicsContract.ComicsEntry.COL_DESCRIPTION)))
                .image(cursor.getString(cursor.getColumnIndex(ComicsContract.ComicsEntry.COL_IMAGE)))
                .thumbnail(cursor.getString(cursor.getColumnIndex(ComicsContract.ComicsEntry.COL_THUMBNAIL)))
                .pages(cursor.getInt(cursor.getColumnIndex(ComicsContract.ComicsEntry.COL_PAGES)))
                .price(cursor.getDouble(cursor.getColumnIndex(ComicsContract.ComicsEntry.COL_PRICE)))
                .build();
    }

    private Comic map(ApiComic apiComic){
        return Comic.builder()
                .id(apiComic.id)
                .title(apiComic.title)
                .description(apiComic.description)
                .thumbnail(getThumbnailUrl(apiComic.thumbnail, SIZE_PORTRAIT_SMALL))
                .image(getThumbnailUrl(apiComic.thumbnail,SIZE_LANDSCAPE_XLARGE))
                .authors(apiComic.creators.items)
                .price(getPrice(apiComic.prices, PRICE_TYPE_PRINT))
                .build();
    }

    private double getPrice(List<Price> prices, String priceType) {
        for (Price price : prices) {
            if(price.type.compareTo(priceType)==0){
                return price.price;
            }
        }
        return 0;
    }

    private String getThumbnailUrl(Thumbnail thumbnail, String size) {
        return thumbnail.path + "/" + size + "." + thumbnail.extension;
    }

    public List<Author> mapAuthors(Cursor cursor) {
        List<Author> authors = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                authors.add(getAuthorFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return authors;
    }

    private Author getAuthorFromCursor(Cursor cursor) {
        return new Author(cursor.getString(cursor.getColumnIndex(ComicsContract.AuthorsEntry.COL_NAME)),
                cursor.getString(cursor.getColumnIndex(ComicsContract.AuthorsEntry.COL_ROLE)));
    }

    public ContentValues getContentValuesFromAuthor(Author author, int comicId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ComicsContract.AuthorsEntry.COL_NAME, author.getName());
        contentValues.put(ComicsContract.AuthorsEntry.COL_ROLE, author.getRole());
        contentValues.put(ComicsContract.AuthorsEntry.COL_COMIC_ID, comicId);
        return contentValues;
    }
}
