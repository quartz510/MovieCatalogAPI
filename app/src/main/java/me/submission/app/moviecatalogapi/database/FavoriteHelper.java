package me.submission.app.moviecatalogapi.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import me.submission.app.moviecatalogapi.models.CataloguePojo;
import me.submission.app.moviecatalogapi.widget.CatalogStackWidget;

import static android.provider.BaseColumns._ID;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FAVORITE_MOVIE;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FAVORITE_TV;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteMvColumns.MOVIE_DESC;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteMvColumns.MOVIE_PHOTO;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteMvColumns.MOVIE_TITLE;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteTvColumns.TV_DESC;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteTvColumns.TV_PHOTO;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteTvColumns.TV_TITLE;

public class FavoriteHelper {
    public static final String DATABASE_FIELD_MOVIE = FAVORITE_MOVIE;
    public static final String DATABASE_FIELD_TV = FAVORITE_TV;

    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;

    public static SQLiteDatabase sqLiteDatabase;

    public FavoriteHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }return INSTANCE;
    }

    public void open() throws SQLException{
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteDatabase.close();

        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }


    ///////////    DATABASE FOR MOVIE   //////////
    public ArrayList<CataloguePojo> getCataloguePojoMv(){
        ArrayList<CataloguePojo> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_FIELD_MOVIE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        CataloguePojo cataloguePojo;
        if (cursor.getCount() > 0){
            do {
                cataloguePojo = new CataloguePojo();
                cataloguePojo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                cataloguePojo.setDescMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DESC)));
                cataloguePojo.setPhotoMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_PHOTO)));
                cataloguePojo.setTitleMovie(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));

                arrayList.add(cataloguePojo);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertCataloguePojoMv(CataloguePojo cataloguePojo){
        ContentValues args = new ContentValues();
        args.put(_ID, cataloguePojo.getId());
        args.put(MOVIE_DESC, cataloguePojo.getDescMovie());
        args.put(MOVIE_PHOTO, cataloguePojo.getPhotoMovie());
        args.put(MOVIE_TITLE, cataloguePojo.getTitleMovie());
        return sqLiteDatabase.insert(DATABASE_FIELD_MOVIE, null, args);
    }

    public int deleteCataloguePojoMv(int id){
        return sqLiteDatabase.delete(FAVORITE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public boolean checkDataMovieExistOrNot(int id){
        String query = "SELECT * FROM " + DATABASE_FIELD_MOVIE + " WHERE " + _ID + " = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public static void updateMovieWidget(Context context){
        Intent intent = new Intent(context, CatalogStackWidget.class);
        intent.setAction(CatalogStackWidget.UPDATE_WIDGET);
        context.sendBroadcast(intent);
    }

    //Provider movie
    public Cursor queryProviderMovie() {
        return sqLiteDatabase.query(DATABASE_FIELD_MOVIE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC"
                , null);
    }

    public long insertProviderMovie(ContentValues values){
        return sqLiteDatabase.insert(DATABASE_FIELD_MOVIE, null, values);
    }

    public int updateProviderMovie(String id, ContentValues values){
        return sqLiteDatabase.update(DATABASE_FIELD_MOVIE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProviderMovie(String id){
        return sqLiteDatabase.delete(DATABASE_FIELD_MOVIE, _ID + " = ?", new String[]{id});
    }

    //////////    DATABASE FOR TVSHOW   //////////
    public ArrayList<CataloguePojo> getCataloguePojoTv(){
        ArrayList<CataloguePojo> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_FIELD_TV,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        CataloguePojo cataloguePojo;
        if (cursor.getCount() > 0){
            do {
                cataloguePojo = new CataloguePojo();
                cataloguePojo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                cataloguePojo.setDescTV(cursor.getString(cursor.getColumnIndexOrThrow(TV_DESC)));
                cataloguePojo.setPhotoTv(cursor.getString(cursor.getColumnIndexOrThrow(TV_PHOTO)));
                cataloguePojo.setTitleTv(cursor.getString(cursor.getColumnIndexOrThrow(TV_TITLE)));

                arrayList.add(cataloguePojo);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertCataloguePojoTv(CataloguePojo cataloguePojo){
        ContentValues args = new ContentValues();
        args.put(_ID, cataloguePojo.getId());
        args.put(TV_DESC, cataloguePojo.getDescTV());
        args.put(TV_PHOTO, cataloguePojo.getPhotoTv());
        args.put(TV_TITLE, cataloguePojo.getTitleTv());
        return sqLiteDatabase.insert(DATABASE_FIELD_TV, null, args);
    }

    public int deleteCataloguePojoTv(int id){
        return sqLiteDatabase.delete(FAVORITE_TV, _ID + " = '" + id + "'", null);
    }

    public boolean checkDataTvshowExistOrNot(int id){
        String query = "SELECT * FROM " + DATABASE_FIELD_TV + " WHERE " + _ID + " = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //Provider tv
    public Cursor queryProviderTv() {
        return sqLiteDatabase.query(DATABASE_FIELD_TV
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC"
                , null);
    }

    public long insertProviderTv(ContentValues values){
        return sqLiteDatabase.insert(DATABASE_FIELD_TV, null, values);
    }

    public int updateProviderTv(String id, ContentValues values){
        return sqLiteDatabase.update(DATABASE_FIELD_TV, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProviderTv(String id){
        return sqLiteDatabase.delete(DATABASE_FIELD_TV, _ID + " = ?", new String[]{id});
    }

}
