package me.submission.app.moviecatalogapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.submission.app.moviecatalogapi.BuildConfig;
import me.submission.app.moviecatalogapi.models.CataloguePojo;

import static me.submission.app.moviecatalogapi.activity.MainActivity.Language;

public class SearchMovieViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private static final String API_KEY = BuildConfig.API_KEY;
    private final MutableLiveData<ArrayList<CataloguePojo>> listMutableLiveData = new MutableLiveData<>();

    private String titleMovie;

    public LiveData<ArrayList<CataloguePojo>> getSearchTitle(){
        return listMutableLiveData;
    }

    public void setSearchTitle(String s) {
        this.titleMovie = s;
    }

    public void setSearchList() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<CataloguePojo> listMovie = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language="
                + Language + "&query=" + titleMovie;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject response = new JSONObject(result);
                    JSONArray dataResult = response.getJSONArray("results");

                    for(int i = 0; i < dataResult.length(); i++) {
                        JSONObject jsonObject =  dataResult.getJSONObject(i);
                        CataloguePojo movies = new CataloguePojo();
                        movies.setId(jsonObject.getInt("id"));
                        movies.setDescMovie(jsonObject.getString("overview"));
                        movies.setPhotoMovie(jsonObject.getString("poster_path"));
                        movies.setTitleMovie(jsonObject.getString("title"));
                        listMovie.add(movies);
                    }
                    listMutableLiveData.postValue(listMovie);


                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

    }
}
