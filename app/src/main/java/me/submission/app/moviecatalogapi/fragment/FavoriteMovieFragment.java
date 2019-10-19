package me.submission.app.moviecatalogapi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.submission.app.moviecatalogapi.adapter.FavoriteMovieAdapter;
import me.submission.app.moviecatalogapi.database.FavoriteHelper;
import me.submission.app.moviecatalogapi.R;

public class FavoriteMovieFragment extends Fragment {
    private FavoriteHelper favoriteHelper;
    private FavoriteMovieAdapter movieAdapter;
    private RecyclerView recyclerView;

    public static FavoriteMovieFragment newInstance() {
        return new FavoriteMovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_fav_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        movieAdapter = new FavoriteMovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();

        favoriteHelper = new FavoriteHelper(getContext());

        favoriteHelper.open();
        movieAdapter.setListMovie(favoriteHelper.getCataloguePojoMv());
        favoriteHelper.close();

        recyclerView.setAdapter(movieAdapter);
    }
}
