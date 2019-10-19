package me.submission.app.moviecatalogapi.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

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
import me.submission.app.moviecatalogapi.adapter.SearchTvshowAdapter;
import me.submission.app.moviecatalogapi.models.CataloguePojo;
import me.submission.app.moviecatalogapi.support.ItemClickSupport;
import me.submission.app.moviecatalogapi.viewmodel.SearchTvshowViewModel;

public class SearchTvshowFragment extends Fragment {

    public static String type = "tv show";

    private SearchTvshowAdapter adapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SearchTvshowViewModel mViewModel;
    private TextView notification;

    public static SearchTvshowFragment newInstance() {
        return new SearchTvshowFragment();
    }

    private Observer<ArrayList<CataloguePojo>> getTvshow = new Observer<ArrayList<CataloguePojo>>() {
        @Override
        public void onChanged(ArrayList<CataloguePojo> cataloguePojos) {
            if(cataloguePojos!=null){
                adapter.setTvshow(cataloguePojos);
                loading(false);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        progressBar = view.findViewById(R.id.progress_bar_search);
        loading(false);

        notification = view.findViewById(R.id.notification_search);
        showNotification(true);

        recyclerView = view.findViewById(R.id.rv_search_listview);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.search_bar_menu, menu);

        SearchManager searchManager = (SearchManager)getActivity().getSystemService(
                Context.SEARCH_SERVICE);
        if (searchManager!=null){
            SearchView searchView = (SearchView)(menu.findItem(R.id.menu_search_bar))
                    .getActionView();
            searchView.setVisibility(androidx.appcompat.widget.SearchView.VISIBLE);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    callSearch(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    callInstantSearch(s);
                    return false;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void callSearch(String s) {
        showNotification(false);
        loading(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mViewModel = ViewModelProviders.of(this).get(SearchTvshowViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getSearchTitle().observe(this, getTvshow);
        mViewModel.setSearchTitle(s);
        mViewModel.setSearchList();

        adapter = new SearchTvshowAdapter(getContext());
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
    }

    private void callInstantSearch(String s) {
        showNotification(false);
        loading(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mViewModel = ViewModelProviders.of(this).get(SearchTvshowViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getSearchTitle().observe(this, getTvshow);
        mViewModel.setSearchTitle(s);
        mViewModel.setSearchList();

        adapter = new SearchTvshowAdapter(getContext());
        adapter.notifyDataSetChanged();

        addItemClickSupport();

        recyclerView.setAdapter(adapter);
    }

    private void addItemClickSupport() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
            new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    CataloguePojo pojo = SearchTvshowAdapter.getTvshow().get(position);
                    String objectDesc = pojo.getDescTV();
                    String objectPhoto = pojo.getPhotoTv();
                    String objectTitle = pojo.getTitleTv();
                    int id = pojo.getId();

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

    private void loading(boolean b) {
        if(b){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showNotification(boolean b){
        if (b) {
            notification.setVisibility(View.VISIBLE);
        }else {
            notification.setVisibility(View.GONE);
        }
    }
}
