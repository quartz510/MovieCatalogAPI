package me.submission.app.moviecatalogapi.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import me.submission.app.moviecatalogapi.R;

import static me.submission.app.moviecatalogapi.activity.MainActivity.cancelAlarm;
import static me.submission.app.moviecatalogapi.activity.MainActivity.setAlarmDaily;
import static me.submission.app.moviecatalogapi.activity.MainActivity.setAlarmRelease;

public class SettingsActivity extends AppCompatActivity {

    SettingsFragment fragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_settings, fragment)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem item){
        finish();
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat
            implements Preference.OnPreferenceChangeListener{

        private SwitchPreferenceCompat reminderDaily;
        private SwitchPreferenceCompat reminderRelease;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.root_preferences);

            String REMINDER_DAILY_KEY = getResources().getString(R.string.key_reminder_daily);
            String REMINDER_RELEASE_KEY = getResources().getString(R.string.key_reminder_release);

            reminderDaily = (SwitchPreferenceCompat) findPreference(REMINDER_DAILY_KEY);
            reminderDaily.setOnPreferenceChangeListener(this);
            reminderRelease = (SwitchPreferenceCompat) findPreference(REMINDER_RELEASE_KEY);
            reminderRelease.setOnPreferenceChangeListener(this);
       }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean b = (boolean) newValue;

            if (key.equals(getString(R.string.key_reminder_daily))) {
                if (b) {
                    setAlarmDaily(getActivity());
                } else {
                    cancelAlarm(getActivity().getApplicationContext(), "daily");
                }
            }
            if (key.equals(getString(R.string.key_reminder_release))) {
                if (b) {
                    setAlarmRelease(getActivity());
                } else {
                    cancelAlarm(getActivity().getApplicationContext(), "release");
                }
            }
            return true;
        }
    }
}