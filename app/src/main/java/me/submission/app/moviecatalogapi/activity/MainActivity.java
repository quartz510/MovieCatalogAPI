package me.submission.app.moviecatalogapi.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Locale;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.adapter.MainPagerAdapter;
import me.submission.app.moviecatalogapi.service.DailyReceiver;
import me.submission.app.moviecatalogapi.service.ReleaseReceiver;

import static me.submission.app.moviecatalogapi.database.DatabaseContract.FavoriteMvColumns.CONTENT_URI_MOVIE;
import static me.submission.app.moviecatalogapi.service.DailyReceiver.DAILY_RECEIVER;
import static me.submission.app.moviecatalogapi.service.DailyReceiver.DAILY_REQUEST_CODE;
import static me.submission.app.moviecatalogapi.service.DailyReceiver.RELEASE_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    public static HandlerThread handlerThread;
    public static String Language;

    private DataObserver dataObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPagerAdapter sectionsPagerAdapter = new MainPagerAdapter(
                this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        dataObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(
                CONTENT_URI_MOVIE, true, dataObserver);

        String language = Locale.getDefault().getISO3Language();
        switch (language) {
            case "ind":
                Language = "id-ID";
                break;
            default:
                Language = "en-US";
                break;
        }
    }

    public static void setAlarmRelease(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, ReleaseReceiver.class);
        intent.putExtra(DAILY_RECEIVER, RELEASE_REQUEST_CODE);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101,
                intent,0);
        long dateTime = calendar.getTimeInMillis();
        if (dateTime<=System.currentTimeMillis()){
            dateTime = dateTime + 24 * 3600 * 1000;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP, dateTime, AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dateTime, AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }

    public static void setAlarmDaily(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, DailyReceiver.class);
        intent.putExtra(DAILY_RECEIVER, DAILY_REQUEST_CODE);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        long dateTime = calendar.getTimeInMillis();
        if (dateTime<=System.currentTimeMillis()){
            dateTime = dateTime + 24 * 3600 * 1000;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP, dateTime, AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dateTime, AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }

    public static void cancelAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = null;
        int request = 0;
        if (type.equals("release")){
            request = 101;
            intent = new Intent(context, ReleaseReceiver.class);
        }
        if (type.equals("daily")){
            request = 100;
            intent = new Intent(context, DailyReceiver.class);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request, intent, 0);
        if (alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_language:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                break;
            case R.id.menu_about:
                Intent mIntent = new Intent(this, AboutActivity.class);
                startActivity(mIntent);
                break;
            case R.id.menu_favorite:
                Intent fIntent = new Intent(this, FavoriteActivity.class);
                startActivity(fIntent);
                break;
            case R.id.menu_settings:
                Intent sIntent = new Intent(this, SettingsActivity.class);
                startActivity(sIntent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public static class DataObserver extends ContentObserver {
        final Context context;
        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
        @Override
        public void onChange(boolean selfChange){
            super.onChange(selfChange);
        }
    }
}