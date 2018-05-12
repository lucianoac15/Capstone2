package br.com.lucianoac.receita.screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import br.com.lucianoac.receita.R;
import br.com.lucianoac.receita.dto.RecipeObject;
import br.com.lucianoac.receita.util.OnItemClickListener;


public class RecipesSearchAdapter extends ArrayRecyclerViewAdapter<RecipeObject, RecipeGridItemViewHolder> {

    private static final String POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_IMAGE_SIZE = "w780";

    private Context context;
    private OnItemClickListener onItemClickListener;

    public RecipesSearchAdapter(Context context, @Nullable List<RecipeObject> items) {
        super(items);
        this.context = context;
    }

    @Override
    public RecipeGridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_recipe, parent, false);
        return new RecipeGridItemViewHolder(itemView, onItemClickListener);
    }

    @Override
    @SuppressLint("PrivateResource")
    public void onBindViewHolder(RecipeGridItemViewHolder holder, int position) {

        RecipeObject movie = getItems().get(position);
        holder.moviePoster.setContentDescription(movie.getTitle());
        Glide.with(context)
                .load(POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE + movie.getPosterPath())
                .placeholder(new ColorDrawable(context.getResources().getColor(R.color.accent_material_light)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .crossFade()
                .into(holder.moviePoster);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
