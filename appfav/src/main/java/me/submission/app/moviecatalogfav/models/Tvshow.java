package me.submission.app.moviecatalogfav.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static me.submission.app.moviecatalogfav.database.DbContract.FavoriteTvColumns.TV_DESC;
import static me.submission.app.moviecatalogfav.database.DbContract.FavoriteTvColumns.TV_PHOTO;
import static me.submission.app.moviecatalogfav.database.DbContract.FavoriteTvColumns.TV_TITLE;
import static me.submission.app.moviecatalogfav.database.DbContract.getColumnInt;
import static me.submission.app.moviecatalogfav.database.DbContract.getColumnString;

public class Tvshow implements Parcelable {
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

    public Tvshow() {
    }

    public Tvshow(int id, String desc, String photo, String title) {
        this.id = id;
        this.desc = desc;
        this.photo = photo;
        this.title = title;
    }

    public Tvshow(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.desc = getColumnString(cursor, TV_DESC);
        this.photo = getColumnString(cursor, TV_PHOTO);
        this.title = getColumnString(cursor, TV_TITLE);
    }

    protected Tvshow(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.desc = in.readString();
        this.photo = in.readString();
        this.title = in.readString();
    }

    public static final Creator<Tvshow> CREATOR = new Creator<Tvshow>() {
        @Override
        public Tvshow createFromParcel(Parcel source) {
            return new Tvshow(source);
        }

        @Override
        public Tvshow[] newArray(int size) {
            return new Tvshow[size];
        }
    };
}
