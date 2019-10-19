package me.submission.app.moviecatalogapi.service;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import me.submission.app.moviecatalogapi.BuildConfig;
import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.activity.DetailActivity;
import me.submission.app.moviecatalogapi.models.CataloguePojo;

import static me.submission.app.moviecatalogapi.activity.DetailActivity.DETAIL_ACTIVITY;
import static me.submission.app.moviecatalogapi.activity.MainActivity.Language;

public class ReleaseReceiver extends BroadcastReceiver {

    private static final String API_KEY = BuildConfig.API_KEY;
    public ArrayList<CataloguePojo> movies = new ArrayList<>();

    @Override
    public void onReceive(final Context context, Intent intent) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = Calendar.getInstance().getTime();
        final String dateNow = format.format(date);
        Log.d("release", ""+dateNow);
        final PendingResult pendingResult = goAsync();
        Thread thread = new Thread(){
            public void run(){
                SyncHttpClient client = new SyncHttpClient();
                client.get("https://api.themoviedb.org/3/discover/movie?api_key="
                        + API_KEY
                        + "&primary_release_date.gte="
                        + dateNow
                        + "&primary_release_date.lte="
                        + dateNow
                        + "&language="
                        + Language
                        , new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                        try {
                            ArrayList<CataloguePojo> list = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++){
                                int id = jsonArray.getJSONObject(i).getInt("id");
                                String release = jsonArray.getJSONObject(i).getString("release_date");
                                String title = jsonArray.getJSONObject(i).getString("title");

                                CataloguePojo pojo = new CataloguePojo();
                                pojo.setId(id);
                                pojo.setReleaseDateMovie(release);
                                pojo.setTitleMovie(title);
                                list.add(pojo);
                            }
                            movies = list;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.d("ReleaseReceiver", "total list = " + movies.size());
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString,
                                          Throwable throwable){
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("ReleaseReceiver", "fetch failed");
                    }
                });
                ArrayList<CataloguePojo> pojos = movies;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = Calendar.getInstance().getTime();
                String dateNow = format.format(date);
                for (CataloguePojo pojo : pojos){
                    if (pojo.getReleaseDateMovie().equals(dateNow)) {
                        showNotificationRelease(context, pojo.getId(), pojo.getTitleMovie());
                        Log.d("ReleaseReceiver", "release found today");
                    }
                }
                pendingResult.finish();
            }
        };
        thread.start();
    }

    private void showNotificationRelease(Context context, int id, String titleMovie) {
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        Intent intent = new Intent(context, DetailActivity.class);
        CataloguePojo pojo = new CataloguePojo();
        pojo.setId(id);
        pojo.setTitleMovie(titleMovie);
        pojo.setType("movie");
        intent.putExtra(DETAIL_ACTIVITY, pojo);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 101, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                "101").setContentIntent(pendingIntent)
                .setContentTitle(context.getString(R.string.release_notification_title))
                .setContentText(titleMovie + " " + context.getString(
                        R.string.release_notification_desc))
                .setSound(ringtone)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        if (managerCompat != null) {
            managerCompat.notify(id, builder.build());
        }
    }
}
