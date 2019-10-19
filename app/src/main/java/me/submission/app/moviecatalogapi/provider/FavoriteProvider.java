package me.submission.app.moviecatalogapi.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.submission.app.moviecatalogapi.database.FavoriteHelper;

import static me.submission.app.moviecatalogapi.database.DatabaseContract.FAVORITE_MOVIE;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FAVORITE_TV;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteMvColumns.CONTENT_URI_MOVIE;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteTvColumns.CONTENT_URI_TV;

public class FavoriteProvider extends ContentProvider {

    public static final String AUTHORITY = "me.submission.app.moviecatalogapi";

    private static final int MOVIE = 1;
    private static final int TV = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private FavoriteHelper favoriteHelper;

    static {
        // content://me.submission.app.moviecatalogapi/favorite_movie
        uriMatcher.addURI(AUTHORITY, FAVORITE_MOVIE, MOVIE);

        // content://me.submission.app.moviecatalogapi/favorite_tv
        uriMatcher.addURI(AUTHORITY, FAVORITE_TV, TV);
    }

    @Override
    public boolean onCreate() {
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        favoriteHelper.open();
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case MOVIE:
                cursor = favoriteHelper.queryProviderMovie();
                break;
            case TV:
                cursor = favoriteHelper.queryProviderTv();
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        favoriteHelper.open();
        long added;
        Uri uri1 = null;
        switch (uriMatcher.match(uri)){
            case MOVIE:
                added = favoriteHelper.insertProviderMovie(contentValues);
                if (added>0){
                    uri1 = ContentUris.withAppendedId(CONTENT_URI_MOVIE, added);
                    getContext().getContentResolver().notifyChange(uri1, null);
                }
                break;

            case TV:
                added = favoriteHelper.insertProviderTv(contentValues);
                if (added>0){
                    uri1 = ContentUris.withAppendedId(CONTENT_URI_TV, added);
                    getContext().getContentResolver().notifyChange(uri1, null);
                }
                break;

            default:
                try {
                    throw new SQLException("Unexpected value: " + uriMatcher.match(uri));
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        favoriteHelper.open();
        int deleted;
        switch (uriMatcher.match(uri)){
            case MOVIE:
                deleted = favoriteHelper.deleteProviderMovie(uri.getLastPathSegment());
                break;
            case TV:
                deleted = favoriteHelper.deleteProviderTv(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
                      @Nullable String[] strings) {
        favoriteHelper.open();
        int updated;
        switch (uriMatcher.match(uri)){
            case MOVIE:
                updated = favoriteHelper.updateProviderMovie(
                        uri.getLastPathSegment(), contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            case TV:
                updated = favoriteHelper.updateProviderTv(uri.getLastPathSegment(), contentValues);
                getContext().getContentResolver().notifyChange(uri, null);
                break;
            default:
                updated = 0;
                break;
        }
        return updated;
    }
}
