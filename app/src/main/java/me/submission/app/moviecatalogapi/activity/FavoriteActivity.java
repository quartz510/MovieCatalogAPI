package me.submission.app.moviecatalogapi.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.adapter.FavoritePagerAdapter;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        FavoritePagerAdapter favoritePagerAdapter = new FavoritePagerAdapter(getSupportFragmentManager(), this);

        ViewPager viewPager = findViewById(R.id.view_pager_favorite);
        viewPager.setAdapter(favoritePagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_favorite);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar_favorite);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(R.string.title_activity_favorite);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
