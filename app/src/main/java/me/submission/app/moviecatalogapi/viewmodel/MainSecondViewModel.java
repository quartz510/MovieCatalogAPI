package me.submission.app.moviecatalogapi.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.submission.app.moviecatalogapi.BuildConfig;
import me.submission.app.moviecatalogapi.models.CataloguePojo;

import static me.submission.app.moviecatalogapi.activity.MainActivity.Language;

public class MainSecondViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private static final String API_KEY = BuildConfig.API_KEY;
    private final MutableLiveData<ArrayList<CataloguePojo>> listMutableLiveData = new MutableLiveData<>();

    public void setTvshowList(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<CataloguePojo> list = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language="+ Language;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    JSONObject object = new JSONObject(result);
                    JSONArray dataResult = object.getJSONArray("results");

                    for(int i = 0; i < dataResult.length(); i++){
                        JSONObject jsonObject = dataResult.getJSONObject(i);
                        CataloguePojo tvShow = new CataloguePojo();
                        tvShow.setId(jsonObject.getInt("id"));
                        tvShow.setDescTV(jsonObject.getString("overview"));
                        tvShow.setPhotoTv(jsonObject.getString("poster_path"));
                        tvShow.setTitleTv(jsonObject.getString("name"));
                        list.add(tvShow);
                    }
                    listMutableLiveData.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<CataloguePojo>> getTvShow(){
        return listMutableLiveData;
    }
}
