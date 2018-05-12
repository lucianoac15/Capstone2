package br.com.lucianoac.receita.screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import br.com.lucianoac.receita.R;
import br.com.lucianoac.receita.dto.VideoObject;
import br.com.lucianoac.receita.util.OnItemClickListener;


public class RecipeVideosAdapter extends RecyclerView.Adapter<RecipeVideoViewHolder> {

    private static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/mqdefault.jpg";
    private final Context context;

    @Nullable
    private ArrayList<VideoObject> movieVideos;
    @Nullable
    private OnItemClickListener onItemClickListener;

    public RecipeVideosAdapter(Context context) {
        this.context = context;
        movieVideos = new ArrayList<>();
    }

    public void setMovieVideos(@Nullable ArrayList<VideoObject> movieVideos) {
        this.movieVideos = movieVideos;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Nullable
    public ArrayList<VideoObject> getMovieVideos() {
        return movieVideos;
    }

    @Override
    public RecipeVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe_video, parent, false);
        return new RecipeVideoViewHolder(itemView, onItemClickListener);
    }

    @Override
    @SuppressLint("PrivateResource")
    public void onBindViewHolder(RecipeVideoViewHolder holder, int position) {
        if (movieVideos == null) {
            return;
        }
        VideoObject video = movieVideos.get(position);
        if (video.isYoutubeVideo()) {
            Glide.with(context)
                    .load(String.format(YOUTUBE_THUMBNAIL, video.getKey()))
                    .placeholder(new ColorDrawable(context.getResources().getColor(R.color.accent_material_light)))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .crossFade()
                    .into(holder.movieVideoThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        if (movieVideos == null) {
            return 0;
        }
        return movieVideos.size();
    }

    public VideoObject getItem(int position) {
        if (movieVideos == null || position < 0 || position > movieVideos.size()) {
            return null;
        }
        return movieVideos.get(position);
    }
}
