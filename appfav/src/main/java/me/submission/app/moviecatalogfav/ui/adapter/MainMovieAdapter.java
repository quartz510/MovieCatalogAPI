package me.submission.app.moviecatalogfav.ui.adapter;

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

import me.submission.app.moviecatalogfav.R;
import me.submission.app.moviecatalogfav.models.Movie;

public class MainMovieAdapter extends RecyclerView.Adapter<MainMovieAdapter.ViewHolder> {

    private static ArrayList<Movie> movies = new ArrayList<>();
    private Context context;

    public static ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        MainMovieAdapter.movies.clear();
        MainMovieAdapter.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public MainMovieAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MainMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMovieAdapter.ViewHolder holder, int position) {
        holder.desc.setText(getMovies().get(position).getDesc());
        holder.title.setText(getMovies().get(position).getTitle());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+getMovies().get(position).getPhoto())
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return getMovies().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView desc, title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.main_rv_desc);
            poster = itemView.findViewById(R.id.main_rv_poster);
            title = itemView.findViewById(R.id.main_rv_title);
        }
    }
}
