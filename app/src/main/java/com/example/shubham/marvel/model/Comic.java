package com.example.shubham.marvel.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Comic implements Parcelable {
    private final int id;
    private final String title;
    private final String description;
    private final String thumbnail;
    private final String image;
    private final int pages;
    private final double price;

    @Nullable
    private List<Author> authors;

    protected Comic(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        thumbnail = in.readString();
        image = in.readString();
        pages = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    private Comic(int id, String title, String description, String thumbnail, String image, int pages, double price, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.image = image;
        this.pages = pages;
        this.price = price;
        this.authors = authors;
    }

    public static ComicBuilder builder() {
        return new ComicBuilder();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeString(image);
        dest.writeInt(pages);
        dest.writeDouble(price);
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public String getImage() {
        return this.image;
    }

    public int getPages() {
        return this.pages;
    }

    public double getPrice() {
        return this.price;
    }

    public List<Author> getAuthors() {
        return this.authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public static class ComicBuilder {
        private int id;
        private String title;
        private String description;
        private String thumbnail;
        private String image;
        private int pages;
        private double price;
        private List<Author> authors;

        ComicBuilder() {
        }

        public Comic.ComicBuilder id(int id) {
            this.id = id;
            return this;
        }

        public Comic.ComicBuilder title(String title) {
            this.title = title;
            return this;
        }

        public Comic.ComicBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Comic.ComicBuilder thumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Comic.ComicBuilder image(String image) {
            this.image = image;
            return this;
        }

        public Comic.ComicBuilder pages(int pages) {
            this.pages = pages;
            return this;
        }

        public Comic.ComicBuilder price(double price) {
            this.price = price;
            return this;
        }

        public ComicBuilder authors(List<Author> items) {
            this.authors = items;
            return this;
        }

        public Comic build() {
            return new Comic(id, title, description, thumbnail, image, pages, price, authors);
        }
    }
}
