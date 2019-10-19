package me.submission.app.moviecatalogapi.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteMvColumns.MOVIE_DESC;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteMvColumns.MOVIE_PHOTO;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteMvColumns.MOVIE_TITLE;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteTvColumns.TV_DESC;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteTvColumns.TV_PHOTO;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteTvColumns.TV_TITLE;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.getColumnInt;
import static me.submission.app.moviecatalogapi.database.DatabaseContract.getColumnString;

public class CataloguePojo implements Parcelable {

    private int Id;
    private String Type;
    private String PhotoMovie;
    private String PhotoTv;
    private String DescMovie;
    private String DescTV;
    private String ReleaseDateMovie;
    private String TitleMovie;
    private String TitleTv;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPhotoMovie() {
        return PhotoMovie;
    }

    public void setPhotoMovie(String photoMovie) {
        PhotoMovie = photoMovie;
    }

    public String getPhotoTv() {
        return PhotoTv;
    }

    public void setPhotoTv(String photoTv) {
        PhotoTv = photoTv;
    }

    public String getDescMovie() {
        return DescMovie;
    }

    public void setDescMovie(String descMovie) {
        DescMovie = descMovie;
    }

    public String getDescTV() {
        return DescTV;
    }

    public void setDescTV(String descTV) {
        DescTV = descTV;
    }

    public String getReleaseDateMovie() {
        return ReleaseDateMovie;
    }

    public void setReleaseDateMovie(String releaseDateMovie) {
        ReleaseDateMovie = releaseDateMovie;
    }

    public String getTitleMovie() {
        return TitleMovie;
    }

    public void setTitleMovie(String titleMovie) {
        TitleMovie = titleMovie;
    }

    public String getTitleTv() {
        return TitleTv;
    }

    public void setTitleTv(String titleTv) {
        TitleTv = titleTv;
    }

    public CataloguePojo() {
    }

    public CataloguePojo(int id, String photoMovie, String photoTv, String descMovie,
                         String descTV, String titleMovie, String titleTv) {
        Id = id;
        PhotoMovie = photoMovie;
        PhotoTv = photoTv;
        DescMovie = descMovie;
        DescTV = descTV;
        TitleMovie = titleMovie;
        TitleTv = titleTv;
    }

    public CataloguePojo(Cursor cursor) {
        Id = getColumnInt(cursor, _ID);
        PhotoMovie = getColumnString(cursor, MOVIE_PHOTO);
        PhotoTv = getColumnString(cursor, TV_PHOTO);
        DescMovie = getColumnString(cursor, MOVIE_DESC);
        DescTV = getColumnString(cursor, TV_DESC);
        TitleMovie = getColumnString(cursor, MOVIE_TITLE);
        TitleTv = getColumnString(cursor, TV_TITLE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.Type);
        dest.writeString(this.PhotoMovie);
        dest.writeString(this.PhotoTv);
        dest.writeString(this.DescMovie);
        dest.writeString(this.DescTV);
        dest.writeString(this.TitleMovie);
        dest.writeString(this.TitleTv);
    }

    protected CataloguePojo(Parcel in) {
        this.Id = in.readInt();
        this.Type = in.readString();
        this.PhotoMovie = in.readString();
        this.PhotoTv = in.readString();
        this.DescMovie = in.readString();
        this.DescTV = in.readString();
        this.TitleMovie = in.readString();
        this.TitleTv = in.readString();
    }

    public static final Creator<CataloguePojo> CREATOR = new Creator<CataloguePojo>() {
        @Override
        public CataloguePojo createFromParcel(Parcel source) {
            return new CataloguePojo(source);
        }

        @Override
        public CataloguePojo[] newArray(int size) {
            return new CataloguePojo[size];
        }
    };
}
