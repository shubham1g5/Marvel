package com.example.shubham.marvel.data.local;

import android.provider.BaseColumns;

public class ComicsContract {

    // Prevent direct instantiation
    private ComicsContract() {
    }

    public static abstract class ComicsEntry implements BaseColumns {
        public static final String TABLE_NAME = "comic";
        public static final String COL_TITLE = "title";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_PAGES = "pages";
        public static final String COL_PRICE = "price";
        public static final String COL_IMAGE = "image";
        public static final String COL_THUMBNAIL = "thumnail";
    }

    public static abstract class AuthorsEntry implements BaseColumns {
        public static final String TABLE_NAME = "author";
        public static final String COL_NAME = "name";
        public static final String COL_ROLE = "role";
    }

    public static abstract class ComicHasAuthorsEntry implements BaseColumns {
        public static final String TABLE_NAME = "comic_has_author";
        public static final String COL_COMIC_ID = "comic_id";
        public static final String COL_AUTHOR_ID = "author_id";
    }
}
