package me.submission.app.moviecatalogapi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.activity.DetailActivity;
import me.submission.app.moviecatalogapi.activity.SearchActivity;
import me.submission.app.moviecatalogapi.adapter.MainMovieAdapter;
import me.submission.app.moviecatalogapi.models.CataloguePojo;
import me.submission.app.moviecatalogapi.support.ItemClickSupport;
import me.submission.app.moviecatalogapi.viewmodel.MainFirstViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFirstFragment extends Fragment {

    public static String type = "movie";

    private MainMovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public static MainFirstFragment newInstance() {
        return new MainFirstFragment();
    }

    private Observer<ArrayList<CataloguePojo>> getMovie = new Observer<ArrayList<CataloguePojo>>() {
        @Override
        public void onChanged(ArrayList<CataloguePojo> cataloguePojos) {
            if(cataloguePojos!=null){
                movieAdapter.setListMovie(cataloguePojos);
                loading(false);
            }
        }
    };

    private Observer<ArrayList<CataloguePojo>> getSearchMovie = new Observer<ArrayList<CataloguePojo>>() {
        @Override
        public void onChanged(ArrayList<CataloguePojo> cataloguePojos) {
            if(cataloguePojos!=null){
                movieAdapter.setListMovie(cataloguePojos);
                loading(false);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        loading(true);

        recyclerView = view.findViewById(R.id.rv_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MainFirstViewModel firstFragmentVM = ViewModelProviders.of(this).get(MainFirstViewModel.class);
        firstFragmentVM.getMovie().observe(this, getMovie);
        firstFragmentVM.setMovieList();

        addItemClickSupport();

        movieAdapter = new MainMovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.search_item_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menu_search_item:
                CataloguePojo pojo = new CataloguePojo();
                pojo.setType(type);
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(SearchActivity.SEARCH_ACTIVITY, pojo);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void loading(boolean b) {
        if(b){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void addItemClickSupport() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String objectDesc = MainMovieAdapter.getMovie().get(position).getDescMovie();
                String objectPhoto = MainMovieAdapter.getMovie().get(position).getPhotoMovie();
                String objectTitle = MainMovieAdapter.getMovie().get(position).getTitleMovie();
                int id = MainMovieAdapter.getMovie().get(position).getId();

                CataloguePojo parcelable = new CataloguePojo();
                parcelable.setDescMovie(objectDesc);
                parcelable.setPhotoMovie(objectPhoto);
                parcelable.setTitleMovie(objectTitle);
                parcelable.setId(id);
                parcelable.setType(type);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.DETAIL_ACTIVITY, parcelable);
                startActivity(intent);
            }
        });
    }
}