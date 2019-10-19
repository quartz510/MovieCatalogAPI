package me.submission.app.moviecatalogapi.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.fragment.MainFirstFragment;
import me.submission.app.moviecatalogapi.fragment.MainSecondFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public MainPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a MainFirstFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return MainFirstFragment.newInstance();
            case 1:
                return MainSecondFragment.newInstance();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}