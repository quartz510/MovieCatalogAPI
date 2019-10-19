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

import me.submission.app.moviecatalogapi.models.CataloguePojo;
import me.submission.app.moviecatalogapi.R;

public class FavoriteTvshowAdapter extends RecyclerView.Adapter<FavoriteTvshowAdapter.ViewHolder> {
    private static ArrayList<CataloguePojo> tvshow = new ArrayList<>();
    private Context context;

    public static ArrayList<CataloguePojo> getTvshow() {
        return tvshow;
    }

    public void setTvshow(ArrayList<CataloguePojo> tvshowList) {
        tvshow.clear();
        tvshow.addAll(tvshowList);
        notifyDataSetChanged();
    }

    public FavoriteTvshowAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteTvshowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvshowAdapter.ViewHolder holder, int position) {
        holder.tvDesc.setText(getTvshow().get(position).getDescTV());
        holder.tvTitle.setText(getTvshow().get(position).getTitleTv());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+getTvshow().get(position).getPhotoTv())
                .into(holder.tvPoster);
    }

    @Override
    public int getItemCount() {
        return getTvshow().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tvPoster;
        TextView tvDesc, tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.main_rv_desc);
            tvPoster = itemView.findViewById(R.id.main_rv_poster);
            tvTitle = itemView.findViewById(R.id.main_rv_title);
        }
    }
}
