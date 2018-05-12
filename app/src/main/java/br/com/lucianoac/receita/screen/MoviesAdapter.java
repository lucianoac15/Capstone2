package br.com.lucianoac.receita.screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import br.com.lucianoac.receita.R;
import br.com.lucianoac.receita.dto.MovieObject;
import br.com.lucianoac.receita.util.CursorRecyclerViewAdapter;
import br.com.lucianoac.receita.util.OnItemClickListener;


public class MoviesAdapter extends CursorRecyclerViewAdapter<MovieGridItemViewHolder> {

    private static final String POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_IMAGE_SIZE = "w780";
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public MoviesAdapter(Context context, Cursor cursor) {
        super(cursor);
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    @SuppressLint("PrivateResource")
    public void onBindViewHolder(MovieGridItemViewHolder viewHolder, Cursor cursor) {
        if (cursor != null) {
            MovieObject movie = MovieObject.fromCursor(cursor);
            viewHolder.moviePoster.setContentDescription(movie.getTitle());
            Glide.with(context)
                    .load(POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE + movie.getPosterPath())
                    .placeholder(new ColorDrawable(context.getResources().getColor(R.color.accent_material_light)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .crossFade()
                    .into(viewHolder.moviePoster);
        }

    }

    @Override
    public MovieGridItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_movie, parent, false);
        return new MovieGridItemViewHolder(itemView, onItemClickListener);
    }

    @Nullable
    public MovieObject getItem(int position) {
        Cursor cursor = getCursor();
        if (cursor == null) {
            return null;
        }
        if (position < 0 || position > cursor.getCount()) {
            return null;
        }
        cursor.moveToFirst();
        for (int i = 0; i < position; i++) {
            cursor.moveToNext();
        }
        return MovieObject.fromCursor(cursor);
    }

}
