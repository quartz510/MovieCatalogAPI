package me.submission.app.moviecatalogapi.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.fragment.SearchMovieFragment;
import me.submission.app.moviecatalogapi.fragment.SearchTvshowFragment;
import me.submission.app.moviecatalogapi.models.CataloguePojo;

public class SearchActivity extends AppCompatActivity {

    public static final String SEARCH_ACTIVITY = "search_activity";
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        CataloguePojo extras = getIntent().getParcelableExtra(SEARCH_ACTIVITY);
        if (extras != null){
            type = extras.getType();
        }

        String MOVIE = "movie";
        String TVSHOW = "tv show";
        ActionBar actionBar = getSupportActionBar();
        if (type.equals(MOVIE)){
            if (actionBar!=null){
                actionBar.setTitle(R.string.title_activity_search_movies);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SearchMovieFragment.newInstance())
                    .commitNow();
            }
        } if (type.equals(TVSHOW)){
            if (actionBar!=null){
                actionBar.setTitle(R.string.title_activity_search_tv);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SearchTvshowFragment.newInstance())
                    .commitNow();
            }
        }
    }

    @Override
    public  boolean onOptionsItemSelected(@NonNull MenuItem item){
        finish();
        return super.onOptionsItemSelected(item);
    }
}
