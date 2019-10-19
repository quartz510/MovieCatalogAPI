package me.submission.app.moviecatalogapi.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    private static final String AUTHORITY = "me.submission.app.moviecatalogapi";
    private static final String SCHEME = "content";

    public static String FAVORITE_MOVIE = "favorite_movie";
    public static final class FavoriteMvColumns implements BaseColumns{
        public static String MOVIE_DESC = "favorite_mv_desc";
        public static String MOVIE_PHOTO = "favorite_mv_photo";
        public static String MOVIE_TITLE = "favorite_mv_title";

        public static final Uri CONTENT_URI_MOVIE = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(FAVORITE_MOVIE)
                .build();
    }

    public static String FAVORITE_TV = "favorite_tv";
    public static final class FavoriteTvColumns implements BaseColumns{
        public static String TV_DESC = "favorite_tv_desc";
        public static String TV_PHOTO = "favorite_tv_photo";
        public static String TV_TITLE = "favorite_tv_title";

        public static final Uri CONTENT_URI_TV = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(FAVORITE_TV)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
