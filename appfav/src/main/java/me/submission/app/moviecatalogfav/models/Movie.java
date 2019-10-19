package me.submission.app.moviecatalogfav.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static me.submission.app.moviecatalogfav.database.DbContract.FavoriteMvColumns.MOVIE_DESC;
import static me.submission.app.moviecatalogfav.database.DbContract.FavoriteMvColumns.MOVIE_PHOTO;
import static me.submission.app.moviecatalogfav.database.DbContract.FavoriteMvColumns.MOVIE_TITLE;
import static me.submission.app.moviecatalogfav.database.DbContract.getColumnInt;
import static me.submission.app.moviecatalogfav.database.DbContract.getColumnString;

public class Movie implements Parcelable {
    private int id;
    private String type;
    private String desc;
    private String photo;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Movie(int id, String desc, String photo, String title) {
        this.id = id;
        this.desc = desc;
        this.photo = photo;
        this.title = title;
    }

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.desc = getColumnString(cursor, MOVIE_DESC);
        this.photo = getColumnString(cursor, MOVIE_PHOTO);
        this.title = getColumnString(cursor, MOVIE_TITLE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeString(this.desc);
        dest.writeString(this.photo);
        dest.writeString(this.title);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.desc = in.readString();
        this.photo = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
