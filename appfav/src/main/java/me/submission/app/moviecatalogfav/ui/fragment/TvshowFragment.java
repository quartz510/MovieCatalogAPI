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
import me.submission.app.moviecatalogfav.models.Tvshow;
import me.submission.app.moviecatalogfav.ui.adapter.MainTvshowAdapter;
import me.submission.app.moviecatalogfav.ui.viewmodel.MovieVm;
import me.submission.app.moviecatalogfav.ui.viewmodel.TvshowVm;

/**
 * A placeholder fragment containing a simple view.
 */
public class TvshowFragment extends Fragment {

    private MovieVm viewModel;
    private MainTvshowAdapter adapter;
    private RecyclerView recyclerView;

    public static TvshowFragment newInstance() {
        return new TvshowFragment();
    }

    private Observer<ArrayList<Tvshow>> getData = new Observer<ArrayList<Tvshow>>() {
        @Override
        public void onChanged(ArrayList<Tvshow> tvshows) {
            if(tvshows!=null){
                adapter.setTv(tvshows);
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

        TvshowVm viewModel = ViewModelProviders.of(this).get(TvshowVm.class);
        viewModel.getContext(getContext());
        viewModel.getLiveData().observe(this, getData);
        viewModel.setIndex();

        adapter = new MainTvshowAdapter(getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}