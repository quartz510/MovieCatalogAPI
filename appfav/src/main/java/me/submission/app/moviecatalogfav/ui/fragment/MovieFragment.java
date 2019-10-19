package me.submission.app.moviecatalogfav.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.submission.app.moviecatalogfav.R;
import me.submission.app.moviecatalogfav.models.Movie;
import me.submission.app.moviecatalogfav.ui.adapter.MainMovieAdapter;
import me.submission.app.moviecatalogfav.ui.viewmodel.MovieVm;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment {

    private MovieVm viewModel;
    private MainMovieAdapter adapter;
    private RecyclerView recyclerView;

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }

    private Observer<ArrayList<Movie>> getData = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if(movies!=null){
                adapter.setMovies(movies);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MovieVm viewModel = ViewModelProviders.of(this).get(MovieVm.class);
        viewModel.getContext(getContext());
        viewModel.getLiveData().observe(this, getData);
        viewModel.setIndex();

        adapter = new MainMovieAdapter(getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}