package me.submission.app.moviecatalogapi.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.submission.app.moviecatalogapi.adapter.FavoriteTvshowAdapter;
import me.submission.app.moviecatalogapi.database.FavoriteHelper;
import me.submission.app.moviecatalogapi.R;

public class FavoriteTvshowFragment extends Fragment {

    public FavoriteTvshowFragment() {
    }

    public static FavoriteTvshowFragment newInstance() {
        return new FavoriteTvshowFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_fav_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FavoriteTvshowAdapter tvAdapter = new FavoriteTvshowAdapter(getContext());
        tvAdapter.notifyDataSetChanged();

        FavoriteHelper favoriteHelper = new FavoriteHelper(getContext());

        favoriteHelper.open();
        tvAdapter.setTvshow(favoriteHelper.getCataloguePojoTv());
        favoriteHelper.close();

        recyclerView.setAdapter(tvAdapter);
    }
}
