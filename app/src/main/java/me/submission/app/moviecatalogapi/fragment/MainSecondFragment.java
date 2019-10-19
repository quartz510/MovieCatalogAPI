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
import me.submission.app.moviecatalogapi.adapter.MainTvshowAdapter;
import me.submission.app.moviecatalogapi.models.CataloguePojo;
import me.submission.app.moviecatalogapi.support.ItemClickSupport;
import me.submission.app.moviecatalogapi.viewmodel.MainSecondViewModel;

public class MainSecondFragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private MainSecondViewModel mViewModel;
    private MainTvshowAdapter tvAdapter;

    public static String type = "tv show";

    public static MainSecondFragment newInstance() {
        return new MainSecondFragment();
    }

    private Observer<ArrayList<CataloguePojo>> getTvShow = new Observer<ArrayList<CataloguePojo>>() {
        @Override
        public void onChanged(ArrayList<CataloguePojo> cataloguePojos) {
            if (cataloguePojos!=null){
                tvAdapter.setTvshow(cataloguePojos);
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        loading(true);

        recyclerView = view.findViewById(R.id.rv_listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mViewModel = ViewModelProviders.of(this).get(MainSecondViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getTvShow().observe(this, getTvShow);
        mViewModel.setTvshowList();

        tvAdapter = new MainTvshowAdapter(getContext());
        tvAdapter.notifyDataSetChanged();

        addItemClickSupport();

        recyclerView.setAdapter(tvAdapter);
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

    private void loading(boolean b){
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
                String objectDesc = MainTvshowAdapter.getTvshow().get(position).getDescTV();
                String objectPhoto = MainTvshowAdapter.getTvshow().get(position).getPhotoTv();
                String objectTitle = MainTvshowAdapter.getTvshow().get(position).getTitleTv();
                int id = MainTvshowAdapter.getTvshow().get(position).getId();

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
