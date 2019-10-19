package me.submission.app.moviecatalogfav.ui.viewmodel;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import me.submission.app.moviecatalogfav.models.Movie;

public class MovieVm extends ViewModel {

    private final MutableLiveData<ArrayList<Movie>> listMutableLiveData = new MutableLiveData<>();
    private Context context;

    public void getContext(Context context) {
        this.context = context;
    }

    public void setIndex() {
        Uri uri = Uri.parse("content://me.submission.app.moviecatalogapi/favorite_movie");
        ContentProviderClient client = context.getContentResolver()
                .acquireContentProviderClient(uri);

        try{
            ArrayList<Movie>arrayList =  new ArrayList<>();
            Cursor cursor = client.query(uri,null, null, null,
                    null);
            cursor.moveToFirst();

            Movie data;
            if (cursor.getCount()>0){
                do {
                    data = new Movie();
                    data.setId(Integer.valueOf(cursor.getString(0)));
                    data.setDesc(cursor.getString(1));
                    data.setPhoto(cursor.getString(2));
                    data.setTitle(cursor.getString(3));
                    arrayList.add(data);
                    cursor.moveToNext();
                }while (!cursor.isAfterLast());
            }
            cursor.close();
            listMutableLiveData.postValue(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<ArrayList<Movie>> getLiveData() {
        return listMutableLiveData;
    }
}