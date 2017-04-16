package com.example.shubham.marvel.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ComicsDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Comics.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";

    private static final String COMMA_SEP = ",";
    private static final String NOT_NULL = "NOT NULL";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String FOREIGN_KEY = " FOREIGN KEY (";
    private static final String REFERENCES = ") REFERENCES ";


    private static final String SQL_CREATE_COMIC_ENTRY =
            "CREATE TABLE " + ComicsContract.ComicsEntry.TABLE_NAME + " (" +
                    ComicsContract.ComicsEntry._ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                    ComicsContract.ComicsEntry.COL_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ComicsContract.ComicsEntry.COL_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    ComicsContract.ComicsEntry.COL_PAGES + TEXT_TYPE + COMMA_SEP +
                    ComicsContract.ComicsEntry.COL_PAGES + TEXT_TYPE + COMMA_SEP +
                    ComicsContract.ComicsEntry.COL_PRICE + REAL_TYPE + COMMA_SEP +
                    ComicsContract.ComicsEntry.COL_THUMBNAIL + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_AUTHOR_ENTRY =
            "CREATE TABLE " + ComicsContract.AuthorsEntry.TABLE_NAME + " (" +
                    ComicsContract.AuthorsEntry._ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                    ComicsContract.AuthorsEntry.COL_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ComicsContract.AuthorsEntry.COL_ROLE + TEXT_TYPE + NOT_NULL +
                    " )";

    private static final String SQL_CREATE_COMIC_HAS_AUTHOR_ENTRY =
            "CREATE TABLE " + ComicsContract.ComicHasAuthorsEntry.TABLE_NAME + " (" +
                    ComicsContract.ComicHasAuthorsEntry._ID + TEXT_TYPE + PRIMARY_KEY + COMMA_SEP +
                    ComicsContract.ComicHasAuthorsEntry.COL_COMIC_ID + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    ComicsContract.ComicHasAuthorsEntry.COL_AUTHOR_ID + TEXT_TYPE + NOT_NULL +

                    FOREIGN_KEY + ComicsContract.ComicHasAuthorsEntry.COL_COMIC_ID + REFERENCES +
                    ComicsContract.ComicsEntry.TABLE_NAME + " (" + ComicsContract.ComicsEntry._ID + ")" + COMMA_SEP +

                    FOREIGN_KEY + ComicsContract.ComicHasAuthorsEntry.COL_AUTHOR_ID + REFERENCES +
                    ComicsContract.AuthorsEntry.TABLE_NAME + " (" + ComicsContract.AuthorsEntry._ID + ")" +
                    " )";

    private static final String[] createStmts = new String[]{
            SQL_CREATE_COMIC_ENTRY,
            SQL_CREATE_AUTHOR_ENTRY,
            SQL_CREATE_COMIC_HAS_AUTHOR_ENTRY
    };


    public ComicsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        for (String createStmt : createStmts) {
            db.execSQL(createStmt);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data
        // Simply discard the data and start over

        String[] tables = new String[]{
                ComicsContract.ComicsEntry.TABLE_NAME,
                ComicsContract.AuthorsEntry.TABLE_NAME,
                ComicsContract.ComicHasAuthorsEntry.TABLE_NAME
        };

        for (String table : tables) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }
        onCreate(db);
    }
}
