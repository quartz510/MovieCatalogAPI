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
import me.submission.app.moviecatalogfav.models.Tvshow;

public class MainTvshowAdapter extends RecyclerView.Adapter<MainTvshowAdapter.ViewHolder> {

    private static ArrayList<Tvshow> tv = new ArrayList<>();
    private Context context;

    public static ArrayList<Tvshow> getTv() {
        return tv;
    }

    public void setTv(ArrayList<Tvshow> tv) {
        MainTvshowAdapter.tv.clear();
        MainTvshowAdapter.tv.addAll(tv);
        notifyDataSetChanged();
    }

    public MainTvshowAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MainTvshowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainTvshowAdapter.ViewHolder holder, int position) {
        holder.desc.setText(getTv().get(position).getDesc());
        holder.title.setText(getTv().get(position).getTitle());

        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185"+ getTv().get(position).getPhoto())
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return getTv().size();
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
