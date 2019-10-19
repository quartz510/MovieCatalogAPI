package me.submission.app.moviecatalogapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import me.submission.app.moviecatalogapi.R;
import me.submission.app.moviecatalogapi.models.CataloguePojo;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder> {
    private static ArrayList<CataloguePojo> movie = new ArrayList<>();
    private Context context;

    public static ArrayList<CataloguePojo> getMovie() {
        return movie;
    }

    public void setListMovie(ArrayList<CataloguePojo> listMovie) {
        movie.clear();
        movie.addAll(listMovie);
        notifyDataSetChanged();
    }

    public SearchMovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mvDesc.setText(getMovie().get(position).getDescMovie());
        holder.mvTitle.setText(getMovie().get(position).getTitleMovie());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+getMovie().get(position).getPhotoMovie())
                .into(holder.mvPoster);
    }

    @Override
    public int getItemCount() {
        return getMovie().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mvPoster;
        TextView mvDesc, mvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mvDesc = itemView.findViewById(R.id.main_rv_desc);
            mvPoster = itemView.findViewById(R.id.main_rv_poster);
            mvTitle = itemView.findViewById(R.id.main_rv_title);
        }
    }
}
