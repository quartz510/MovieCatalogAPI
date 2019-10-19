package me.submission.app.moviecatalogapi.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.fragment.FavoriteMovieFragment;
import me.submission.app.moviecatalogapi.fragment.FavoriteTvshowFragment;

public class FavoritePagerAdapter extends FragmentPagerAdapter {
    private static final int[] TABFav_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context fContext;

    public FavoritePagerAdapter(FragmentManager fm, Context fContext) {
        super(fm);
        this.fContext = fContext;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return FavoriteMovieFragment.newInstance();
            case 1:
                return FavoriteTvshowFragment.newInstance();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fContext.getResources().getString(TABFav_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
