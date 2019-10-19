package me.submission.app.moviecatalogfav.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "favoritedb.db";
    private static final int DATABASE_VERSION = 13;

    private static final String SQL_CREATE_FAVORITE_MOVIE = String.format("CREATE TABLE %s" +
            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            DbContract.FAVORITE_MOVIE,
            DbContract.FavoriteMvColumns._ID,
            DbContract.FavoriteMvColumns.MOVIE_DESC,
            DbContract.FavoriteMvColumns.MOVIE_PHOTO,
            DbContract.FavoriteMvColumns.MOVIE_TITLE
    );

    private static final String SQL_CREATE_FAVORITE_TV = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DbContract.FAVORITE_TV,
            DbContract.FavoriteTvColumns._ID,
            DbContract.FavoriteTvColumns.TV_DESC,
            DbContract.FavoriteTvColumns.TV_PHOTO,
            DbContract.FavoriteTvColumns.TV_TITLE
    );

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(SQL_CREATE_FAVORITE_MOVIE);
        database.execSQL(SQL_CREATE_FAVORITE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " + DbContract.FAVORITE_MOVIE);
        database.execSQL("DROP TABLE IF EXISTS " + DbContract.FAVORITE_TV);
        onCreate(database);
    }
}
