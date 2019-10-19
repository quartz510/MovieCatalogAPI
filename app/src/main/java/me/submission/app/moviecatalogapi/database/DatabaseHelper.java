package me.submission.app.moviecatalogapi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "favoritedb.db";
    private static final int DATABASE_VERSION = 13;

    private static final String SQL_CREATE_FAVORITE_MOVIE = String.format("CREATE TABLE %s" +
            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DatabaseContract.FAVORITE_MOVIE,
            DatabaseContract.FavoriteMvColumns._ID,
            DatabaseContract.FavoriteMvColumns.MOVIE_DESC,
            DatabaseContract.FavoriteMvColumns.MOVIE_PHOTO,
            DatabaseContract.FavoriteMvColumns.MOVIE_TITLE
    );

    private static final String SQL_CREATE_FAVORITE_TV = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.FAVORITE_TV,
            DatabaseContract.FavoriteTvColumns._ID,
            DatabaseContract.FavoriteTvColumns.TV_DESC,
            DatabaseContract.FavoriteTvColumns.TV_PHOTO,
            DatabaseContract.FavoriteTvColumns.TV_TITLE
    );

    public  DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(SQL_CREATE_FAVORITE_MOVIE);
        database.execSQL(SQL_CREATE_FAVORITE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FAVORITE_MOVIE);
        database.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FAVORITE_TV);
        onCreate(database);
    }
}
